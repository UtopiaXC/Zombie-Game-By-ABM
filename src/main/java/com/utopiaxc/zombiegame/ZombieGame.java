package com.utopiaxc.zombiegame;

import com.utopiaxc.zombiegame.agents.AgentArmy;
import com.utopiaxc.zombiegame.agents.AgentCivilian;
import com.utopiaxc.zombiegame.agents.AgentZombie;
import com.utopiaxc.zombiegame.tools.Configs;
import com.utopiaxc.zombiegame.tools.Enums.StartingPosition;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;

import java.util.ArrayList;
import java.util.List;

public class ZombieGame extends SimState {
    //2D space with the size of 100*100 without underlying grid (set the discretization to 1.0)
    public Continuous2D mYard = new Continuous2D(1.0, 100, 100);
    public int mNumZombies;
    public int mNumCivilians;
    public int mNumArmies;
    public List<AgentZombie> mZombies;
    public List<AgentCivilian> mCivilians;
    public List<AgentArmy> mArmies;

    public ZombieGame(long seed) {
        super(seed);
    }

    public void start() {
        super.start();
        mYard.clear();
        reset();
        // Create zombies in the left top
        for (int i = 0; i < mNumZombies; i++) {
            AgentZombie zombie = new AgentZombie();
            mYard.setObjectLocation(zombie, Configs.getInstance().getPositionZombie().generatePosition(
                    random,mYard.getWidth(),mYard.getHeight()));
            mZombies.add(zombie);
            schedule.scheduleRepeating(zombie);
        }

        // Create civilians in the center
        for (int i = 0; i < mNumCivilians; i++) {
            AgentCivilian civilian = new AgentCivilian();
            mYard.setObjectLocation(civilian, Configs.getInstance().getPositionCivilian().generatePosition(
                    random,mYard.getWidth(),mYard.getHeight()));
            mCivilians.add(civilian);
            schedule.scheduleRepeating(civilian);
        }

        // Create armies in the bottom right
        for (int i = 0; i < mNumArmies; i++) {
            AgentArmy army = new AgentArmy();
            mYard.setObjectLocation(army, Configs.getInstance().getPositionArmy().generatePosition(
                    random,mYard.getWidth(),mYard.getHeight()));
            mArmies.add(army);
            schedule.scheduleRepeating(army);
        }
    }

    public void reset() {
        mNumZombies = 10;
        mNumCivilians = 100;
        mNumArmies = 10;
        mZombies = new ArrayList<>();
        mCivilians = new ArrayList<>();
        mArmies = new ArrayList<>();
    }
}
