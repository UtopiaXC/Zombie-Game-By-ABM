package com.utopiaxc.zombiegame.tools;

/**
 * <p> This class is for each agent to calculate its position.
 *
 * @author UtopiaXC
 * @since 2024-09-03 23:03:59
 */
public class Coordinate {
    private double mX;
    private double mY;
    private Object mAgent = null;

    /**
     * <p> Construct method, init the coordinate.
     *
     * @param x X-axis position
     * @param y Y-axis position
     * @author utopiaxc
     * @since 2024-09-03 23:06:57
     */
    public Coordinate(double x, double y) {
        mX = x;
        mY = y;
    }

    /**
     * <p> Setter of the agent.
     *
     * @param agent The agent of coordinate
     * @author utopiaxc
     * @since 2024-09-03 23:20:56
     */
    public void setAgent(Object agent) {
        mAgent = agent;
    }

    /**
     * <p> Getter of the agent.
     *
     * @return java.lang.Object
     * @author utopiaxc
     * @since 2024-09-03 23:22:13
     */
    public Object getAgent() {
        return mAgent;
    }

    /**
     * <p> Getter of X-axis position.
     *
     * @return double
     * @author utopiaxc
     * @since 2024-09-03 23:22:57
     */
    public double getX() {
        return mX;
    }

    /**
     * <p> Getter of Y-axis position.
     *
     * @return double
     * @author utopiaxc
     * @since 2024-09-03 23:23:20
     */
    public double getY() {
        return mY;
    }

    /**
     * <p> Setter of X-axis position.
     *
     * @param x X-axis position
     * @author utopiaxc
     * @since 2024-09-03 23:23:37
     */
    public void setX(double x) {
        mX = x;
    }

    /**
     * <p> Setter of Y-axis position.
     *
     * @param y Y-axis position
     * @author utopiaxc
     * @since 2024-09-03 23:24:04
     */
    public void setY(double y) {
        mY = y;
    }

    /**
     * <p> Method to calculate distance between two coordinates (myself and the other).
     *
     * @param coordinate The other coordinate.
     * @return double
     * @author utopiaxc
     * @since 2024-09-03 23:00:16
     */
    public double calculateDistance(Coordinate coordinate) {
        return Math.sqrt(
                Math.pow(Math.abs(coordinate.getX() - mX), 2)
                        + Math.pow(Math.abs(coordinate.getY() - mY), 2));
    }

    /**
     * <p> Calculate the final coordinate when approaching.
     *
     * @param coordinate Move goal.
     * @param distance   Move distance of the agent
     * @return com.utopiaxc.zombiegame.tools.Coordinate
     * @author utopiaxc
     * @since 2024-09-03 23:24:27
     */
    public Coordinate calculateFinalCoordinateForApproaching(Coordinate coordinate, double distance) {
        double recentDistance = this.calculateDistance(coordinate);
        double x = distance / recentDistance * (coordinate.getX() - mX) + mX;
        double y = distance / recentDistance * (coordinate.getY() - mY) + mY;
        return new Coordinate(x, y);
    }

    /**
     * <p> Calculate the final coordinate when running away.
     *
     * @param coordinate Move goal.
     * @param distance   Move distance of the agent
     * @return com.utopiaxc.zombiegame.tools.Coordinate
     * @author utopiaxc
     * @since 2024-09-03 23:27:18
     */
    public Coordinate calculateFinalCoordinateForRunningAway(Coordinate coordinate, double distance) {
        double recentDistance = this.calculateDistance(coordinate);
        double x = (mX - coordinate.getX()) / recentDistance * (recentDistance + distance) + coordinate.getX();
        double y = (mY - coordinate.getY()) / recentDistance * (recentDistance + distance) + coordinate.getY();
        return new Coordinate(x, y);
    }
}
