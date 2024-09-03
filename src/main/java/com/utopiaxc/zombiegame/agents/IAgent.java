package com.utopiaxc.zombiegame.agents;

import java.awt.*;

/**
 * <p> Interface of agents.
 *
 * @author utopiaxc
 * @since 2024-09-03 23:30:35
 */
public interface IAgent {
    Color getAgentColor();

    double getSpeed();

    void setSpeed(double speed);
}
