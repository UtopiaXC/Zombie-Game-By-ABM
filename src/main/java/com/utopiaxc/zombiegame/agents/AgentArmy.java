package com.utopiaxc.zombiegame.agents;

import com.utopiaxc.zombiegame.tools.Configs;
import sim.engine.SimState;
import sim.engine.Steppable;

import java.awt.*;

public class AgentArmy implements IAgent, Steppable {
    public double mSpeed = Configs.getInstance().getSpeedArmy();
    @Override
    public Color getAgentColor() {
        return Configs.getInstance().getColorArmy();
    }

    @Override
    public void step(SimState simState) {

    }
}
