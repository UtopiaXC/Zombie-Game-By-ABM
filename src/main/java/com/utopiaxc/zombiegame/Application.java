package com.utopiaxc.zombiegame;

import com.utopiaxc.zombiegame.game.ZombieGameWithUI;
import com.utopiaxc.zombiegame.tools.Configs;
import com.utopiaxc.zombiegame.tools.Enums;

import javax.swing.*;

public class Application {
    private JTextField mInputZombieSpeed;
    private JButton mButtonStartGame;
    private JTextField mInputZombieSpeedCorrectionFactor;
    private JTextField mInputCivilianSpeed;
    private JTextField mInputCivilianSpeedConnectionFactor;
    private JTextField mInputArmySpeed;
    private JPanel mPanel;
    private JLabel mLabelZombieSpeed;
    private JLabel mLabelArmySpeed;
    private JLabel mLabelCivilianSpeed;
    private JLabel mLabelZombieSpeedCorrectionFactor;
    private JTextField mInputArmySpeedCorrectionFactor;
    private JTextField mInputZombieAmount;
    private JTextField mInputCivilianAmount;
    private JTextField mInputArmyAmount;
    private JTextField mInputArmyShotRange;
    private JTextField mInputArmyEscapeRange;
    private JTextField mInputArmyShotColdDown;
    private JComboBox mSelectorZombieGenerationPosition;
    private JComboBox mSelectorCivilianGenerationPosition;
    private JComboBox mSelectorArmyGenerationPosition;
    private JLabel mLabelZombieConfigs;
    private JLabel mLabelZombieAmount;
    private JLabel mLabelZombieGenerationPosition;
    private JLabel mLabelCivilianConfigs;
    private JLabel mLabelCivilianAmount;
    private JLabel mLabelCivilianSpeedConnectionFactor;
    private JLabel mLabelCivilianGenerationPosition;
    private JLabel mLabelArmyConfigs;
    private JLabel mLabelArmyAmount;
    private JLabel mLabelArmySpeedCorrectionFactor;
    private JLabel mLabelArmyGenerationPosition;
    private JLabel mLabelArmyShotRange;
    private JLabel mLabelArmyEscapeRange;
    private JLabel mLabelArmyShotColdDown;
    private JButton mButtonResetConfig;
    private JFrame mFrame;

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        init();
    }

    private void init() {
        mFrame = new JFrame("Zombie Game Launcher");
        mFrame.setContentPane(mPanel);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.pack();
        mFrame.setVisible(true);
        setDefaultConfig();
        setButtons();
    }

    private void setDefaultConfig() {
        Configs configs = Configs.getInstance();
        mInputZombieAmount.setText(String.valueOf(configs.getNumZombies()));
        mInputZombieSpeed.setText(String.valueOf(configs.getSpeedZombie()));
        mInputZombieSpeedCorrectionFactor.setText(String.valueOf(configs.getSpeedVariationZombie()));
        mSelectorZombieGenerationPosition.setSelectedIndex(configs.getPositionZombie().getIndex());
        mInputCivilianAmount.setText(String.valueOf(configs.getNumCivilian()));
        mInputCivilianSpeed.setText(String.valueOf(configs.getSpeedCivilian()));
        mInputCivilianSpeedConnectionFactor.setText(String.valueOf(configs.getSpeedVariationCivilian()));
        mSelectorCivilianGenerationPosition.setSelectedIndex(configs.getPositionCivilian().getIndex());
        mInputArmyAmount.setText(String.valueOf(configs.getNumArmy()));
        mInputArmySpeed.setText(String.valueOf(configs.getSpeedArmy()));
        mInputArmySpeedCorrectionFactor.setText(String.valueOf(configs.getSpeedVariationArmy()));
        mSelectorArmyGenerationPosition.setSelectedIndex(configs.getPositionArmy().getIndex());
        mInputArmyShotRange.setText(String.valueOf(configs.getArmyShotRange()));
        mInputArmyEscapeRange.setText(String.valueOf(configs.getArmyEscapeRange()));
        mInputArmyShotColdDown.setText(String.valueOf(configs.getArmyShotColdDown()));
    }

    private void setButtons() {
        mButtonStartGame.addActionListener(_ -> {
            if (!setConfigs()){
                showUnableDialog();
                return;
            }
            ZombieGameWithUI.getInstance().init();
            mButtonStartGame.setEnabled(false);
            mButtonResetConfig.setEnabled(true);
        });
        mButtonResetConfig.addActionListener(_->{
            if (!setConfigs()){
                showUnableDialog();
                return;
            }
        });
    }

    public boolean setConfigs(){
        if (!checkConfigs()) {
            return false;
        }
        try {
            Configs configs = Configs.getInstance();
            configs.setNumZombies(Integer.parseInt(mInputZombieAmount.getText()));
            configs.setSpeedZombie(Double.parseDouble(mInputZombieSpeed.getText()));
            configs.setSpeedVariationZombie(Double.parseDouble(mInputZombieSpeedCorrectionFactor.getText()));
            configs.setPositionZombie(Enums.StartingPosition.getFromIndex(
                    mSelectorZombieGenerationPosition.getSelectedIndex()));
            configs.setNumCivilian(Integer.parseInt(mInputCivilianAmount.getText()));
            configs.setSpeedCivilian(Double.parseDouble(mInputCivilianSpeed.getText()));
            configs.setSpeedVariationCivilian(Double.parseDouble(mInputCivilianSpeedConnectionFactor.getText()));
            configs.setPositionCivilian(Enums.StartingPosition.getFromIndex(
                    mSelectorCivilianGenerationPosition.getSelectedIndex()));
            configs.setNumArmy(Integer.parseInt(mInputArmyAmount.getText()));
            configs.setSpeedArmy(Double.parseDouble(mInputArmySpeed.getText()));
            configs.setSpeedVariationArmy(Double.parseDouble(mInputArmySpeedCorrectionFactor.getText()));
            configs.setPositionArmy(Enums.StartingPosition.getFromIndex(
                    mSelectorArmyGenerationPosition.getSelectedIndex()));
            configs.setArmyShotRange(Double.parseDouble(mInputArmyShotRange.getText()));
            configs.setArmyEscapeRange(Double.parseDouble(mInputArmyEscapeRange.getText()));
            configs.setArmyShotColdDown(Integer.parseInt(mInputArmyShotColdDown.getText()));
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            return false;
        }
        return true;
    }

    private boolean checkConfigs() {
        return true;
    }

    private void showUnableDialog(){
        JOptionPane.showMessageDialog(null,"Input formatting errors exist");
    }
}
