package com.utopiaxc.zombiegame.tools;

public class Coordinate {
    private double mX;
    private double mY;
    private Object mAgent = null;

    public Coordinate(double x, double y) {
        mX = x;
        mY = y;
    }

    public void setAgent(Object agent) {
        mAgent = agent;
    }

    public Object getAgent() {
        return mAgent;
    }

    public double getX() {
        return mX;
    }

    public double getY() {
        return mY;
    }

    public void setX(double x) {
        mX = x;
    }

    public void setY(double y) {
        mY = y;
    }

    public double calculateDistance(Coordinate coordinate) {
        return Math.sqrt(
                Math.pow(Math.abs(coordinate.getX() - mX), 2)
                        + Math.pow(Math.abs(coordinate.getY() - mY), 2));
    }

    public Coordinate calculateFinalCoordinateForApproaching(Coordinate coordinate, double distance) {
        double recentDistance = this.calculateDistance(coordinate);
        double x = distance / recentDistance * (coordinate.getX() - mX) + mX;
        double y = distance / recentDistance * (coordinate.getY() - mY) + mY;
        return new Coordinate(x, y);
    }

    public Coordinate calculateFinalCoordinateForRunningAway(Coordinate coordinate, double distance) {
        double recentDistance = this.calculateDistance(coordinate);
        double x = (mX - coordinate.getX()) / recentDistance * (recentDistance + distance) + coordinate.getX();
        double y = (mY - coordinate.getY()) / recentDistance * (recentDistance + distance) + coordinate.getY();
        return new Coordinate(x, y);
    }
}
