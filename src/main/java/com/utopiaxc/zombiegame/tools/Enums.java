package com.utopiaxc.zombiegame.tools;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;

/**
 * <p> Enums for the project.
 *
 * @author utopiaxc
 * @since 2024-09-03 23:14:36
 */
public class Enums {
    /**
     * <p> Enum of agents starting position.
     *
     * @author utopiaxc
     * @since 2024-09-03 23:14:54
     */
    public enum StartingPosition {
        TOP_LEFT("TOP_LEFT", 0),
        TOP_CENTER("TOP_CENTER", 1),
        TOP_RIGHT("TOP_RIGHT", 2),
        MIDDLE_LEFT("MIDDLE_LEFT", 3),
        MIDDLE_CENTER("MIDDLE_CENTER", 4),
        MIDDLE_RIGHT("MIDDLE_RIGHT", 5),
        BOTTOM_LEFT("BOTTOM_LEFT", 6),
        BOTTOM_CENTER("BOTTOM_CENTER", 7),
        BOTTOM_RIGHT("BOTTOM_RIGHT", 8);
        private String mPositionName;
        private int mIndex;

        /**
         * <p> Construct method, init the enum.
         *
         * @param positionName Position Name
         * @param index        Index of selector
         * @author utopiaxc
         * @since 2024-09-03 23:15:32
         */
        StartingPosition(String positionName, int index) {
            mPositionName = positionName;
            mIndex = index;
        }

        /**
         * <p> Get the index of selector for each enum.
         *
         * @return int
         * @author utopiaxc
         * @since 2024-09-03 23:16:18
         */
        public int getIndex() {
            return mIndex;
        }

        /**
         * <p> Find the enum by the index of selector.
         *
         * @param index Index in the selector
         * @return com.utopiaxc.zombiegame.tools.Enums.StartingPosition
         * @author utopiaxc
         * @since 2024-09-03 23:16:35
         */
        public static StartingPosition getFromIndex(int index) {
            for (StartingPosition position : StartingPosition.values()) {
                if (position.mIndex == index) {
                    return position;
                }
            }
            return null;
        }

        /**
         * <p> Calculate the coordinate for each position.
         *
         * @param random To generate different agent to different position.
         * @param x      Frame length
         * @param y      Frame height
         * @return sim.util.Double2D
         * @author utopiaxc
         * @since 2024-09-03 23:17:02
         */
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
