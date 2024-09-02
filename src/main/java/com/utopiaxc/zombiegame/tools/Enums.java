package com.utopiaxc.zombiegame.tools;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;

public class Enums {
    public enum StartingPosition {
        TOP_LEFT("TOP_LEFT", 0),
        TOP_CENTER("TOP_CENTER",1),
        TOP_RIGHT("TOP_RIGHT",2),
        MIDDLE_LEFT("MIDDLE_LEFT",3),
        MIDDLE_CENTER("MIDDLE_CENTER",4),
        MIDDLE_RIGHT("MIDDLE_RIGHT",5),
        BOTTOM_LEFT("BOTTOM_LEFT",6),
        BOTTOM_CENTER("BOTTOM_CENTER",7),
        BOTTOM_RIGHT("BOTTOM_RIGHT",8);
        private String mPositionName;
        private int mIndex;
        StartingPosition(String positionName, int index) {
            mPositionName = positionName;
            mIndex = index;
        }

        public int getIndex(){
            return mIndex;
        }

        public static StartingPosition getFromIndex(int index){
            for (StartingPosition position : StartingPosition.values()) {
                if (position.mIndex == index){
                    return position;
                }
            }
            return null;
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
