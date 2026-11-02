package Singleton.Game_Settings;

public class SettingsScreen implements Runnable {

    private GameSettingsManager settings = GameSettingsManager.getInstance();

    @Override
    public void run() {
        System.out.println("[SettingsScreen] Thread started: " + Thread.currentThread().getName());
        changeVolume(80);
        updateDifficulty("Hard");
        updateLanguage("Bengali");
        updateBrightness(60);
        updateResolution("2560x1440");
        saveAndExit();
    }

    public void changeVolume(int newVolume) {
        settings.setVolumeLevel(newVolume);
    }

    public void updateDifficulty(String difficulty) {
        settings.setDifficultyMode(difficulty);
    }

    public void updateLanguage(String language) {
        settings.changeLanguage(language);
    }

    public void updateBrightness(int brightness) {
        settings.setBrightness(brightness);
    }

    public void updateResolution(String resolution) {
        settings.setResolution(resolution);
    }

    public void saveAndExit() {
        settings.saveSettings();
        System.out.println("[SettingsScreen] Settings saved. Returning to menu.");
    }

    public void displaySettings() {
        System.out.println("[SettingsScreen] Displaying current settings:");
        settings.displayAllSettings();
    }
}