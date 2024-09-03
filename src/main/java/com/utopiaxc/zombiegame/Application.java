package com.utopiaxc.zombiegame;

import com.utopiaxc.zombiegame.game.ZombieGameWithUI;
import com.utopiaxc.zombiegame.tools.Configs;
import com.utopiaxc.zombiegame.tools.Enums;

import javax.swing.*;

/**
 * <p> This class is the entrance of the application.
 *
 * @author utopiaxc
 * @since 2024-09-03 23:07:43
 */
public class Application {
    // UI components for the config editing.
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
    private JTextField mInputArmyShootRange;
    private JTextField mInputArmyEscapeRange;
    private JTextField mInputArmyShootColdDown;
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
    private JLabel mLabelArmyShootRange;
    private JLabel mLabelArmyEscapeRange;
    private JLabel mLabelArmyShootColdDown;
    private JButton mButtonResetConfig;
    private JFrame mFrame;

    /**
     * <p> Create new Application entity.
     *
     * @param args params
     * @author utopiaxc
     * @since 2024-09-03 23:10:41
     */
    public static void main(String[] args) {
        new Application();
    }

    /**
     * <p> Constructor method, init the UI.
     *
     * @author utopiaxc
     * @since 2024-09-03 23:11:39
     */
    public Application() {
        init();
    }


    /**
     * <p> Init the default config editing UI.
     *
     * @author utopiaxc
     * @since 2024-09-03 23:12:29
     */
    private void init() {
        mFrame = new JFrame("Zombie Game Launcher");
        mFrame.setContentPane(mPanel);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.pack();
        mFrame.setVisible(true);
        setDefaultConfig();
        setButtons();
    }

    /**
     * <p> Read all configs from default and set they into UI.
     *
     * @author utopiaxc
     * @since 2024-09-03 23:13:07
     */
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
        mInputArmyShootRange.setText(String.valueOf(configs.getArmyShootRange()));
        mInputArmyEscapeRange.setText(String.valueOf(configs.getArmyEscapeRange()));
        mInputArmyShootColdDown.setText(String.valueOf(configs.getArmyShootColdDown()));
    }

    /**
     * <p> Start mason UI when click the start button
     *
     * @author utopiaxc
     * @since 2024-09-03 23:13:20
     */
    private void setButtons() {
        mButtonStartGame.addActionListener(_ -> {
            // Check the configs' format and show the warning dialog if there are any errors.
            if (setConfigsAndReturnIfFormatIsWrong()) {
                showUnableDialog();
                return;
            }
            ZombieGameWithUI.getInstance().init();
            mButtonStartGame.setEnabled(false);
            mButtonResetConfig.setEnabled(true);
        });

        // Update the configs
        mButtonResetConfig.addActionListener(_ -> {
            // Check the configs' format and show the warning dialog if there are any errors.
            if (setConfigsAndReturnIfFormatIsWrong()) {
                showUnableDialog();
                return;
            }
        });
    }

    /**
     * <p> Set configs into config instance.
     *
     * @author utopiaxc
     * @since 2024-09-03 23:13:39
     */
    private boolean setConfigsAndReturnIfFormatIsWrong() {
        if (!checkConfigs()) {
            return true;
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
            configs.setArmyShootRange(Double.parseDouble(mInputArmyShootRange.getText()));
            configs.setArmyEscapeRange(Double.parseDouble(mInputArmyEscapeRange.getText()));
            configs.setArmyShootColdDown(Integer.parseInt(mInputArmyShootColdDown.getText()));
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            return true;
        }
        return false;
    }

    /**
     * <p> Check values, not yet done.
     *
     * @author utopiaxc
     * @since 2024-09-03 23:14:01
     */
    private boolean checkConfigs() {
        return true;
    }

    /**
     * <p> Show a warning dialog when input value format is wrong.
     *
     * @author utopiaxc
     * @since 2024-09-03 23:14:12
     */
    private void showUnableDialog() {
        JOptionPane.showMessageDialog(null, "Input formatting errors exist");
    }
}
