package com.utopiaxc.zombiegame.agents;

import com.utopiaxc.zombiegame.ZombieGame;
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
        mSpeed=speed;
    }

    public double getKillCount() {
        return mKillCount;
    }

    @Override
    public void step(SimState simState) {
        try {
            ZombieGame zombieGame = (ZombieGame) simState;
            zombieGame.checkOver();
            mSpeed += (zombieGame.random.nextDouble() - 0.5) * Configs.getInstance().getSpeedVariationZombie();
            Continuous2D yard = zombieGame.mYard;
            Double2D me = yard.getObjectLocation(this);
            MutableDouble2D sumForces = new MutableDouble2D();
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

            finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(closestCoordinate, mSpeed);
            sumForces.addIn(new Double2D(finalCoordinate.getX() - myCoordinate.getX(),
                    finalCoordinate.getY() - myCoordinate.getY()));
            sumForces.addIn(me);
            zombieGame.mYard.setObjectLocation(this, new Double2D(sumForces));
        } catch (Exception _) {
        }
    }
}
