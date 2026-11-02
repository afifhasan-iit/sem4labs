package Singleton.Game_Settings;

public class MainMenu implements Runnable {

    private GameSettingsManager settings = GameSettingsManager.getInstance();

    @Override
    public void run() {
        System.out.println("[MainMenu] Thread started: " + Thread.currentThread().getName());
        display();
    }

    public void display() {
        System.out.println("[MainMenu] Welcome! Language: " + settings.getLanguage());
        System.out.println("[MainMenu] Volume: " + settings.getVolumeLevel());
    }

    public void openSettings() {
        System.out.println("[MainMenu] Opening Settings Screen...");
        SettingsScreen settingsScreen = new SettingsScreen();
        settingsScreen.displaySettings();
    }

    public void startGame() {
        System.out.println("[MainMenu] Launching Gameplay Screen...");
        GameplayScreen gameplayScreen = new GameplayScreen();
        gameplayScreen.startGame();
    }
}