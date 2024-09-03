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

public class AgentZombie implements IAgent, Steppable {
    private double mSpeed = Configs.getInstance().getSpeedZombie();
    private double mKillCount = 0;

    @Override
    public Color getAgentColor() {
        return Configs.getInstance().getColorZombie();
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

    @Override
    public void step(SimState simState) {
        try {
            // Get the zombie agent entry from simulator.
            ZombieGame zombieGame = (ZombieGame) simState;
            zombieGame.checkOver();

            // Change the speed of the agent by random value and the correction factor.
            mSpeed += (zombieGame.random.nextDouble() - 0.5) * Configs.getInstance().getSpeedVariationZombie();

            // Get the coordinate entity from simulator.
            Continuous2D yard = zombieGame.mYard;
            Double2D me = yard.getObjectLocation(this);
            MutableDouble2D sumForces = new MutableDouble2D();

            // Get all armies' and civilians' coordinate.
            List<Coordinate> coordinates = new ArrayList<>();
            Coordinate myCoordinate = new Coordinate(me.getX(), me.getY());
            for (AgentCivilian mCivilian : zombieGame.mCivilians) {
                Double2D civilian = yard.getObjectLocation(mCivilian);
                Coordinate coordinate = new Coordinate(civilian.getX(), civilian.getY());
                coordinate.setAgent(mCivilian);
                coordinates.add(coordinate);
            }
            for (AgentArmy mArmy : zombieGame.mArmies) {
                Double2D civilian = yard.getObjectLocation(mArmy);
                Coordinate coordinate = new Coordinate(civilian.getX(), civilian.getY());
                coordinate.setAgent(mArmy);
                coordinates.add(coordinate);
            }

            // If there is no armies and civilians, move randomly.
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

            // Traverse all humans to find the closest human.
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

            // If the zombie could catch the human, kill the human and transform the agent to the zombie.
            if (closestDistance < mSpeed) {
                zombieGame.mYard.remove(closestCoordinate.getAgent());
                try {
                    zombieGame.mCivilians.remove((AgentCivilian) closestCoordinate.getAgent());
                } catch (Exception _) {
                }
                try {
                    zombieGame.mArmies.remove((AgentArmy) closestCoordinate.getAgent());
                } catch (Exception _) {
                }
                AgentZombie zombie = new AgentZombie();
                zombieGame.mYard.setObjectLocation(zombie,
                        new Double2D(closestCoordinate.getX(), closestCoordinate.getY()));
                zombieGame.mZombies.add(zombie);
                zombieGame.schedule.scheduleRepeating(zombie);
                mKillCount++;
            }

            // Move the zombie agent.
            finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(closestCoordinate, mSpeed);
            sumForces.addIn(new Double2D(finalCoordinate.getX() - myCoordinate.getX(),
                    finalCoordinate.getY() - myCoordinate.getY()));
            sumForces.addIn(me);
            zombieGame.mYard.setObjectLocation(this, new Double2D(sumForces));
        } catch (Exception _) {
        }
    }
}
