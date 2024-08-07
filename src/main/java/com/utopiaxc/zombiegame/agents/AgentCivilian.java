package com.utopiaxc.zombiegame.agents;

import com.utopiaxc.zombiegame.tools.Configs;
import sim.engine.SimState;
import sim.engine.Steppable;

import java.awt.*;

public class AgentCivilian implements IAgent, Steppable {
    public double mSpeed = Configs.getInstance().getSpeedCivilian();
    @Override
    public Color getAgentColor() {
        return Configs.getInstance().getColorCivilian();
    }

    @Override
    public void step(SimState simState) {

    }
}
