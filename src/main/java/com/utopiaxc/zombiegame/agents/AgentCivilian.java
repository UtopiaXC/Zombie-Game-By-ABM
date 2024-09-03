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

public class AgentCivilian implements IAgent, Steppable {
    private double mSpeed = Configs.getInstance().getSpeedCivilian();

    @Override
    public Color getAgentColor() {
        return Configs.getInstance().getColorCivilian();
    }

    @Override
    public double getSpeed() {
        return mSpeed;
    }

    @Override
    public void setSpeed(double speed) {
        mSpeed=speed;
    }

    @Override
    public void step(SimState simState) {
        try {
            // Get the civilian agent entry from simulator.
            ZombieGame zombieGame = (ZombieGame) simState;
            zombieGame.checkOver();

            // Change the speed of the agent by random value and the correction factor.
            mSpeed += (zombieGame.random.nextDouble() - 0.5) * Configs.getInstance().getSpeedVariationCivilian();

            // Get the coordinate entity from simulator.
            Continuous2D yard = zombieGame.mYard;
            Double2D me = yard.getObjectLocation(this);
            MutableDouble2D sumForces = new MutableDouble2D();

            // Get all zombies' coordinate.
            List<Coordinate> coordinates = new ArrayList<>();
            Coordinate myCoordinate = new Coordinate(me.getX(), me.getY());
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

            // Move reversely along the closest zombie.
            Coordinate finalCoordinate;
            finalCoordinate = myCoordinate.calculateFinalCoordinateForRunningAway(closestCoordinate, mSpeed);
            if (finalCoordinate.getX() > yard.getWidth()
                    || finalCoordinate.getX() < 0
                    || finalCoordinate.getY() > yard.getHeight()
                    || finalCoordinate.getY() < 0) {
                Coordinate coordinate = new Coordinate(
                        zombieGame.random.nextDouble() * yard.getWidth(),
                        zombieGame.random.nextDouble() * yard.getHeight());
                finalCoordinate = myCoordinate.calculateFinalCoordinateForApproaching(coordinate, mSpeed);
            }
            sumForces.addIn(new Double2D(finalCoordinate.getX() - myCoordinate.getX(),
                    finalCoordinate.getY() - myCoordinate.getY()));
            sumForces.addIn(me);
            zombieGame.mYard.setObjectLocation(this, new Double2D(sumForces));
        } catch (Exception _) {
        }
    }
}
