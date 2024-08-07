package com.utopiaxc.zombiegame.tools;

import java.awt.*;

import com.utopiaxc.zombiegame.tools.Enums.StartingPosition;

public class Configs {
    private static Configs instance;
    private double mSpeedZombie;
    private double mSpeedCivilian;
    private double mSpeedArmy;
    private int mNumZombies;
    private int mNumCivilian;
    private int mNumArmy;
    private Color mColorZombie;
    private Color mColorCivilian;
    private Color mColorArmy;
    private StartingPosition mPositionZombie;
    private StartingPosition mPositionCivilian;
    private StartingPosition mPositionArmy;

    public static synchronized Configs getInstance() {
        if (instance == null) {
            instance = new Configs();
        }
        return instance;
    }

    private Configs() {
        mSpeedZombie = 2.0;
        mSpeedArmy = 1.0;
        mSpeedCivilian = 1.4;
        mNumZombies = 5;
        mNumCivilian = 100;
        mNumArmy = 10;
        mColorZombie = Color.RED;
        mColorCivilian = Color.GREEN;
        mColorArmy = Color.ORANGE;
        mPositionZombie = StartingPosition.TOP_LEFT;
        mPositionCivilian = StartingPosition.MIDDLE_CENTER;
        mPositionArmy = StartingPosition.BOTTOM_RIGHT;
    }

    public double getSpeedZombie() {
        return mSpeedZombie;
    }

    public void setSpeedZombie(double speedZombie) {
        mSpeedZombie = speedZombie;
    }

    public double getSpeedCivilian() {
        return mSpeedCivilian;
    }

    public void setSpeedCivilian(double speedCivilian) {
        mSpeedCivilian = speedCivilian;
    }

    public double getSpeedArmy() {
        return mSpeedArmy;
    }

    public void setSpeedArmy(double speedArmy) {
        mSpeedArmy = speedArmy;
    }

    public int getNumZombies() {
        return mNumZombies;
    }

    public void setNumZombies(int numZombies) {
        mNumZombies = numZombies;
    }

    public int getNumCivilian() {
        return mNumCivilian;
    }

    public void setNumCivilian(int numCivilian) {
        mNumCivilian = numCivilian;
    }

    public int getNumArmy() {
        return mNumArmy;
    }

    public void setNumArmy(int numArmy) {
        mNumArmy = numArmy;
    }

    public Color getColorZombie() {
        return mColorZombie;
    }

    public void setColorZombie(Color colorZombie) {
        mColorZombie = colorZombie;
    }

    public Color getColorCivilian() {
        return mColorCivilian;
    }

    public void setColorCivilian(Color colorCivilian) {
        mColorCivilian = colorCivilian;
    }

    public Color getColorArmy() {
        return mColorArmy;
    }

    public void setColorArmy(Color colorArmy) {
        mColorArmy = colorArmy;
    }

    public StartingPosition getPositionZombie() {
        return mPositionZombie;
    }

    public void setPositionZombie(StartingPosition positionZombie) {
        mPositionZombie = positionZombie;
    }

    public StartingPosition getPositionCivilian() {
        return mPositionCivilian;
    }

    public void setPositionCivilian(StartingPosition positionCivilian) {
        mPositionCivilian = positionCivilian;
    }

    public StartingPosition getPositionArmy() {
        return mPositionArmy;
    }

    public void setPositionArmy(StartingPosition positionArmy) {
        mPositionArmy = positionArmy;
    }
}
