package com.utopiaxc.zombiegame.tools;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;

public class Enums {
    public enum StartingPosition {
        TOP_LEFT("TOP_LEFT"),
        TOP_CENTER("TOP_CENTER"),
        TOP_RIGHT("TOP_RIGHT"),
        MIDDLE_LEFT("MIDDLE_LEFT"),
        MIDDLE_CENTER("MIDDLE_CENTER"),
        MIDDLE_RIGHT("MIDDLE_RIGHT"),
        BOTTOM_LEFT("BOTTOM_LEFT"),
        BOTTOM_CENTER("BOTTOM_CENTER"),
        BOTTOM_RIGHT("BOTTOM_RIGHT");

        StartingPosition(String positionName) {
        }

        public Double2D generatePosition(MersenneTwisterFast random, double x, double y) {
            switch (this) {
                case TOP_LEFT -> {
                    return new Double2D(
                            random.nextDouble() * 10,
                            random.nextDouble() * 10);
                }
                case TOP_CENTER -> {
                    return new Double2D(
                            (x / 2) + ((random.nextDouble() - 0.5) * 10),
                            random.nextDouble() * 10);
                }
                case TOP_RIGHT -> {
                    return new Double2D(
                            x - random.nextDouble() * 10,
                            random.nextDouble() * 10);
                }
                case MIDDLE_LEFT -> {
                    return new Double2D(
                            random.nextDouble() * 10,
                            (y / 2) + ((random.nextDouble() - 0.5) * 10));
                }
                case MIDDLE_CENTER -> {
                    return new Double2D(
                            (x / 2) + ((random.nextDouble() - 0.5) * 10),
                            (y / 2) + ((random.nextDouble() - 0.5) * 10));
                }
                case MIDDLE_RIGHT -> {
                    return new Double2D(
                            x - random.nextDouble() * 10,
                            (y / 2) + ((random.nextDouble() - 0.5) * 10));
                }
                case BOTTOM_LEFT -> {
                    return new Double2D(
                            random.nextDouble() * 10,
                            y - random.nextDouble() * 10);
                }
                case BOTTOM_CENTER -> {
                    return new Double2D(
                            (x / 2) + ((random.nextDouble() - 0.5) * 10),
                            y - random.nextDouble() * 10);
                }
                case BOTTOM_RIGHT -> {
                    return new Double2D(
                            x - random.nextDouble() * 10,
                            y - random.nextDouble() * 10);
                }
                default -> {
                    return new Double2D(0, 0);
                }
            }
        }
    }
}
