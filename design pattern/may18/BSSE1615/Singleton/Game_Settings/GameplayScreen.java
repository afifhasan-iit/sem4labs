package Singleton.Game_Settings;

public class GameplayScreen implements Runnable {

    private GameSettingsManager settings = GameSettingsManager.getInstance();

    @Override
    public void run() {
        System.out.println("[GameplayScreen] Thread started: " + Thread.currentThread().getName());
        startGame();
    }

    public void applyGraphicsSettings() {
        System.out.println("[GameplayScreen] Applying graphics settings:");
        System.out.println("  Resolution : " + settings.getResolution());
        System.out.println("  Brightness : " + settings.getBrightness());
    }

    public void displayCurrentSettings() {
        System.out.println("[GameplayScreen] Current active settings:");
        settings.displayAllSettings();
    }

    public void applyAudioSettings() {
        System.out.println("[GameplayScreen] Setting volume to " + settings.getVolumeLevel());
    }

    public void startGame() {
        System.out.println("[GameplayScreen] Starting game...");
        applyGraphicsSettings();
        applyAudioSettings();
        System.out.println("[GameplayScreen] Difficulty: " + settings.getDifficultyMode());
        System.out.println("[GameplayScreen] Language  : " + settings.getLanguage());
    }
}