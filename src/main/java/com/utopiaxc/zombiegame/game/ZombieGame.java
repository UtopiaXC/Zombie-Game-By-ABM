package com.utopiaxc.zombiegame.game;

import com.utopiaxc.zombiegame.agents.AgentArmy;
import com.utopiaxc.zombiegame.agents.AgentCivilian;
import com.utopiaxc.zombiegame.agents.AgentZombie;
import com.utopiaxc.zombiegame.tools.Configs;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;

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
        // Create zombies and add them into schedule task
        for (int i = 0; i < mNumZombies; i++) {
            AgentZombie zombie = new AgentZombie();
            mYard.setObjectLocation(zombie, Configs.getInstance().getPositionZombie().generatePosition(
                    random, mYard.getWidth(), mYard.getHeight()));
            mZombies.add(zombie);
            schedule.scheduleRepeating(zombie); 
        }

        // Create civilians and add them into schedule task
        for (int i = 0; i < mNumCivilians; i++) {
            AgentCivilian civilian = new AgentCivilian();
            mYard.setObjectLocation(civilian, Configs.getInstance().getPositionCivilian().generatePosition(
                    random, mYard.getWidth(), mYard.getHeight()));
            mCivilians.add(civilian);
            schedule.scheduleRepeating(civilian);
        }

        // Create armies and add them into schedule task
        for (int i = 0; i < mNumArmies; i++) {
            AgentArmy army = new AgentArmy();
            mYard.setObjectLocation(army, Configs.getInstance().getPositionArmy().generatePosition(
                    random, mYard.getWidth(), mYard.getHeight()));
            mArmies.add(army);
            schedule.scheduleRepeating(army);
        }
    }

    public void reset() {
        mNumZombies = Configs.getInstance().getNumZombies();
        mNumCivilians = Configs.getInstance().getNumCivilian();
        mNumArmies = Configs.getInstance().getNumArmy();
        mZombies = new ArrayList<>();
        mCivilians = new ArrayList<>();
        mArmies = new ArrayList<>();
    }

    public void checkOver() {
        if (mZombies.isEmpty() || (mCivilians.isEmpty() && mArmies.isEmpty())){
            this.finish();
        }
    }
}
