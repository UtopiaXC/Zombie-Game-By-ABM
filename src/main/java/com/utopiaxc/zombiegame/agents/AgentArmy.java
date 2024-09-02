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
    private int mLastShotBeforeSteps = Integer.MAX_VALUE;
    private double mKillCount = 0;
    private double mShotRange = Configs.getInstance().getArmyShotRange();
    private double mEscapeRange = Configs.getInstance().getArmyEscapeRange();
    private int mArmyShotColdDown = Configs.getInstance().getArmyShotColdDown();


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

    public int getArmyShotColdDown() {
        return mArmyShotColdDown;
    }

    public void setArmyShotColdDown(int armyShotColdDown) {
        mArmyShotColdDown = armyShotColdDown;
    }

    public double getEscapeRange() {
        return mEscapeRange;
    }

    public void setEscapeRange(double escapeRange) {
        mEscapeRange = escapeRange;
    }

    public double getShotRange() {
        return mShotRange;
    }

    public void setShotRange(double shotRange) {
        mShotRange = shotRange;
    }

    @Override
    public void step(SimState simState) {
        try {
            ZombieGame zombieGame = (ZombieGame) simState;
            zombieGame.checkOver();
            mSpeed += (zombieGame.random.nextDouble() - 0.5) * Configs.getInstance().getSpeedVariationArmy();
            Continuous2D yard = zombieGame.mYard;
            Double2D me = yard.getObjectLocation(this);
            MutableDouble2D sumForces = new MutableDouble2D();
            List<Coordinate> coordinates = new ArrayList<>();
            Coordinate myCoordinate = new Coordinate(me.getX(), me.getY());
            for (AgentZombie mZombie : zombieGame.mZombies) {
                Double2D zombie = yard.getObjectLocation(mZombie);
                Coordinate coordinate = new Coordinate(zombie.getX(), zombie.getY());
                coordinate.setAgent(mZombie);
                coordinates.add(coordinate);
            }
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
            List<Coordinate> humans = new ArrayList<>();
            boolean isAnyoneOnTheShotWay = false;
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
            for (Coordinate human : humans) {
                if (((human.getX() - myCoordinate.getX()) * (closestCoordinate.getY() - myCoordinate.getY())
                        == (closestCoordinate.getX() - myCoordinate.getX()) * (human.getY() - myCoordinate.getY()))
                        && Math.min(myCoordinate.getX(), closestCoordinate.getX()) < human.getX()
                        && human.getX() < Math.max(myCoordinate.getX(), closestCoordinate.getX())
                        && Math.min(myCoordinate.getY(), closestCoordinate.getY()) < human.getY()
                        && human.getY() < Math.max(myCoordinate.getY(), closestCoordinate.getY())) {
                    isAnyoneOnTheShotWay = true;
                    break;
                }
            }
            if (closestDistance <= mShotRange
                    && mLastShotBeforeSteps > mArmyShotColdDown
                    && !isAnyoneOnTheShotWay) {
                zombieGame.mYard.remove(closestCoordinate.getAgent());
                zombieGame.mZombies.remove((AgentZombie) closestCoordinate.getAgent());
                mKillCount++;
                mLastShotBeforeSteps = 0;
                finalCoordinate = myCoordinate;
            } else if (closestDistance <= mShotRange
                    && closestDistance > mEscapeRange
                    && mLastShotBeforeSteps <= mArmyShotColdDown) {
                finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(closestCoordinate, mSpeed);
            } else if (closestDistance <= mEscapeRange) {
                finalCoordinate = myCoordinate.calculateFinalCoordinateForRunningAway(closestCoordinate, mSpeed);
            } else if (closestDistance > mShotRange) {
                finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(closestCoordinate, mSpeed);
            } else {
                finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(closestCoordinate, mSpeed);
            }
            if (finalCoordinate.getX() > yard.getWidth()
                    || finalCoordinate.getX() < 0
                    || finalCoordinate.getY() > yard.getHeight()
                    || finalCoordinate.getY() < 0) {
                Coordinate coordinate = new Coordinate(
                        zombieGame.random.nextDouble() * yard.getWidth(),
                        zombieGame.random.nextDouble() * yard.getHeight());
                finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(coordinate, mSpeed);
            }
            if (mLastShotBeforeSteps != Integer.MAX_VALUE) {
                mLastShotBeforeSteps++;
            }
            sumForces.addIn(new Double2D(finalCoordinate.getX() - myCoordinate.getX(),
                    finalCoordinate.getY() - myCoordinate.getY()));
            sumForces.addIn(me);
            zombieGame.mYard.setObjectLocation(this, new Double2D(sumForces));

        } catch (Exception _) {
        }
    }
}
