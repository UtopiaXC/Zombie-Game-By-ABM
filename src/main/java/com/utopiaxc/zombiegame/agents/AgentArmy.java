package com.utopiaxc.zombiegame.agents;

import com.utopiaxc.zombiegame.game.ZombieGame;
import com.utopiaxc.zombiegame.tools.Configs;
import com.utopiaxc.zombiegame.tools.Coordinate;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AgentArmy implements IAgent, Steppable {
    private double mSpeed = Configs.getInstance().getSpeedArmy();
    private int mLastShootBeforeSteps = Integer.MAX_VALUE;
    private double mKillCount = 0;
    private double mShootRange = Configs.getInstance().getArmyShootRange();
    private double mEscapeRange = Configs.getInstance().getArmyEscapeRange();
    private int mArmyShootColdDown = Configs.getInstance().getArmyShootColdDown();


    @Override
    public Color getAgentColor() {
        return Configs.getInstance().getColorArmy();
    }

    @Override
    public double getSpeed() {
        return mSpeed;
    }

    @Override
    public void setSpeed(double speed) {
        mSpeed = speed;
    }

    public double getKillCount() {
        return mKillCount;
    }

    public int getArmyShootColdDown() {
        return mArmyShootColdDown;
    }

    public void setArmyShootColdDown(int armyShootColdDown) {
        mArmyShootColdDown = armyShootColdDown;
    }

    public double getEscapeRange() {
        return mEscapeRange;
    }

    public void setEscapeRange(double escapeRange) {
        mEscapeRange = escapeRange;
    }

    public double getShootRange() {
        return mShootRange;
    }

    public void setShootRange(double shootRange) {
        mShootRange = shootRange;
    }

    @Override
    public void step(SimState simState) {
        try {
            // Get the zombie agent entry from simulator.
            ZombieGame zombieGame = (ZombieGame) simState;
            zombieGame.checkOver();

            // Change the speed of the agent by random value and the correction factor.
            mSpeed += (zombieGame.random.nextDouble() - 0.5) * Configs.getInstance().getSpeedVariationArmy();

            // Get the coordinate entity from simulator.
            Continuous2D yard = zombieGame.mYard;
            Double2D me = yard.getObjectLocation(this);
            MutableDouble2D sumForces = new MutableDouble2D();

            // Create the coordinate class for the agent.
            Coordinate myCoordinate = new Coordinate(me.getX(), me.getY());

            // Get all zombies' coordinate.
            List<Coordinate> coordinates = new ArrayList<>();
            for (AgentZombie mZombie : zombieGame.mZombies) {
                Double2D zombie = yard.getObjectLocation(mZombie);
                Coordinate coordinate = new Coordinate(zombie.getX(), zombie.getY());
                coordinate.setAgent(mZombie);
                coordinates.add(coordinate);
            }

            // If there is no zombies, move randomly.
            if (coordinates.isEmpty()) {
                Coordinate coordinate = new Coordinate(
                        zombieGame.random.nextDouble() * yard.getWidth(),
                        zombieGame.random.nextDouble() * yard.getHeight());
                Coordinate finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(coordinate, mSpeed);
                sumForces.addIn(new Double2D(finalCoordinate.getX() - myCoordinate.getX(),
                        finalCoordinate.getY() - myCoordinate.getY()));
                sumForces.addIn(me);
                yard.setObjectLocation(this, new Double2D(sumForces));
                return;
            }

            // Traverse all zombies to find the closest zombie.
            Coordinate closestCoordinate = coordinates.getFirst();
            double closestDistance = closestCoordinate.calculateDistance(myCoordinate);
            for (Coordinate coordinate : coordinates) {
                double distance = coordinate.calculateDistance(myCoordinate);
                if (distance < closestDistance) {
                    closestCoordinate = coordinate;
                    closestDistance = distance;
                }
            }
            Coordinate finalCoordinate;

            // Find if any army or civilian on the shoot way between this agent and closest zombie.
            List<Coordinate> humans = new ArrayList<>();
            boolean isAnyoneOnTheShootWay = false;
            for (AgentCivilian mCivilian : zombieGame.mCivilians) {
                Double2D civilian = yard.getObjectLocation(mCivilian);
                Coordinate coordinate = new Coordinate(civilian.getX(), civilian.getY());
                coordinate.setAgent(civilian);
                humans.add(coordinate);
            }
            for (AgentArmy mArmy : zombieGame.mArmies) {
                Double2D army = yard.getObjectLocation(mArmy);
                if (myCoordinate.getY() == army.getY() && myCoordinate.getX() == army.getX()) {
                    continue;
                }
                Coordinate coordinate = new Coordinate(army.getX(), army.getY());
                coordinate.setAgent(army);
                humans.add(coordinate);
            }

            // Use the area of a triangle to find if three points are collinear.
            // Army(x,y), Agent(i,j), Zombie(p,q), S = 0.5 * |(p-x)*(j-y)-(q-y)(i-x)|, when S = 0, points are collinear.
            for (Coordinate human : humans) {
                if ((closestCoordinate.getX() - myCoordinate.getX()) * (human.getY() - myCoordinate.getY()) ==
                        (closestCoordinate.getY() - myCoordinate.getY()) * (human.getX() - myCoordinate.getX())) {
                    isAnyoneOnTheShootWay = true;
                    break;
                }
            }

            // If the closest zombie is in the range of shooting and the army is not get too close, kill the zombie.
            // Note that if there is any army or civilian on the shooting way, stop shooting.
            // Note that the gun has clod down.
            if (closestDistance <= mShootRange
                    && mLastShootBeforeSteps > mArmyShootColdDown
                    && !isAnyoneOnTheShootWay) {
                zombieGame.mYard.remove(closestCoordinate.getAgent());
                zombieGame.mZombies.remove((AgentZombie) closestCoordinate.getAgent());
                mKillCount++;
                mLastShootBeforeSteps = 0;
                finalCoordinate = myCoordinate;
            }
            // If the gun is in the clod down period, and the army is not get too close, approach to zombie.
            else if (closestDistance <= mShootRange
                    && closestDistance > mEscapeRange
                    && mLastShootBeforeSteps <= mArmyShootColdDown) {
                finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(closestCoordinate, mSpeed);
            }
            // If the army is getting too close, run away.
            else if (closestDistance <= mEscapeRange) {
                finalCoordinate = myCoordinate.calculateFinalCoordinateForRunningAway(closestCoordinate, mSpeed);
            }
            // If the army is too far away the zombie to shoot, get close.
            else if (closestDistance > mShootRange) {
                finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(closestCoordinate, mSpeed);
            }
            // Any other conditions, get close to the zombie.
            else {
                finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(closestCoordinate, mSpeed);
            }

            // Move the agent to the next coordinate.
            if (finalCoordinate.getX() > yard.getWidth()
                    || finalCoordinate.getX() < 0
                    || finalCoordinate.getY() > yard.getHeight()
                    || finalCoordinate.getY() < 0) {
                Coordinate coordinate = new Coordinate(
                        zombieGame.random.nextDouble() * yard.getWidth(),
                        zombieGame.random.nextDouble() * yard.getHeight());
                finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(coordinate, mSpeed);
            }
            if (mLastShootBeforeSteps != Integer.MAX_VALUE) {
                mLastShootBeforeSteps++;
            }
            sumForces.addIn(new Double2D(finalCoordinate.getX() - myCoordinate.getX(),
                    finalCoordinate.getY() - myCoordinate.getY()));
            sumForces.addIn(me);
            zombieGame.mYard.setObjectLocation(this, new Double2D(sumForces));

        } catch (Exception _) {
        }
    }
}
