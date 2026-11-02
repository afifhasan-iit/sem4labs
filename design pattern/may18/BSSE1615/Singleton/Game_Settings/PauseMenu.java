package Singleton.Game_Settings;

public class PauseMenu implements Runnable {

    private GameSettingsManager settings = GameSettingsManager.getInstance();

    @Override
    public void run() {
        System.out.println("[PauseMenu] Thread started: " + Thread.currentThread().getName());
        display();
        quickMute();
        resumeGame();
    }

    public void display() {
        System.out.println("[PauseMenu] Game paused.");
        System.out.println("[PauseMenu] Volume    : " + settings.getVolumeLevel());
        System.out.println("[PauseMenu] Difficulty: " + settings.getDifficultyMode());
    }

    public void quickMute() {
        settings.setVolumeLevel(0);
        System.out.println("[PauseMenu] Game muted.");
    }

    public void resumeGame() {
        System.out.println("[PauseMenu] Resuming game with current settings...");
        GameplayScreen gameplayScreen = new GameplayScreen();
        gameplayScreen.applyGraphicsSettings();
        gameplayScreen.applyAudioSettings();
    }

    public void openSettings() {
        System.out.println("[PauseMenu] Opening Settings Screen from pause...");
        SettingsScreen settingsScreen = new SettingsScreen();
        settingsScreen.displaySettings();
    }
}