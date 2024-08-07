package com.utopiaxc.zombiegame;

import com.utopiaxc.zombiegame.agents.IAgent;
import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;

import javax.swing.*;
import java.awt.*;

public class ZombieGameWithUI extends GUIState {

    public Display2D mDisplay;
    public JFrame mDisplayFrame;
    ContinuousPortrayal2D mYardPortrayal = new ContinuousPortrayal2D();

    public static void main(String[] args) {
        ZombieGameWithUI zombieGameWithUI = new ZombieGameWithUI();
        Console c = new Console(zombieGameWithUI);
        c.setVisible(true);
    }

    public ZombieGameWithUI() {
        //
        super(new ZombieGame(System.currentTimeMillis()));
    }

    // When the play button is pressed, this method will be called.
    public void start() {
        super.start();
        setupPortrayals();
    }

    // When the simulation is loaded from the checkpoint, this method will be called
    public void load(SimState state) {
        super.load(state);
        setupPortrayals();
    }

    // Set up the game, should be called during loading the simulation and starting the simulation
    public void setupPortrayals() {
        ZombieGame zombieGame = (ZombieGame)state;
        mYardPortrayal.setField(zombieGame.mYard);
        mYardPortrayal.setPortrayalForAll(new OvalPortrayal2D(){
            public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
            {
                // Set the point color by interface
                IAgent agent = (IAgent)object;
                paint = agent.getAgentColor();
                super.draw(object, graphics, info);
            }
        });
        mDisplay.reset();
        mDisplay.setBackdrop(Color.white);
        mDisplay.repaint();
    }

    // This method is used to create a display window and frames for the agent
    public void init(Controller c) {
        super.init(c);
        // Create a display windows with width of 600 and height of 600, and make the simulation based on this class
        mDisplay = new Display2D(600, 600, this);
        // Set the window is not be clipped
        mDisplay.setClipping(false);
        // Create a frame on the display
        mDisplayFrame = mDisplay.createFrame();
        // Set the show title of the display
        mDisplayFrame.setTitle("Zombie Game");
        // Register the frame onto the controller so that the controller will attach the frame to the display
        c.registerFrame(mDisplayFrame);
        // Show the frame
        mDisplayFrame.setVisible(true);
        // Attach the yard portrayal to the display
        mDisplay.attach(mYardPortrayal,"Yard");
    }

    // This method is used to destroy the window and the frame of the game
    public void quit() {
        super.quit();
        // Destroy the frame on the display
        if (mDisplayFrame != null) {
            mDisplayFrame.dispose();
        }
        mDisplayFrame = null;
        // Make the display to be null
        mDisplay = null;
    }
}
