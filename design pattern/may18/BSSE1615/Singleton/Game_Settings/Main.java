package Singleton.Game_Settings;

public class Main {

    public static void main(String[] args) {

        System.out.println("========== GAME STARTING ==========\n");

        GameSettingsManager.getInstance().loadSettings();

        Thread settingsThread = new Thread(new SettingsScreen());
        Thread gameplayThread = new Thread(new GameplayScreen());
        Thread pauseThread    = new Thread(new PauseMenu());
        Thread mainMenuThread = new Thread(new MainMenu());

        settingsThread.start();
        gameplayThread.start();
        pauseThread.start();
        mainMenuThread.start();

        try {
            settingsThread.join();
            gameplayThread.join();
            pauseThread.join();
            mainMenuThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Singleton Verification ---");
        GameSettingsManager a = GameSettingsManager.getInstance();
        GameSettingsManager b = GameSettingsManager.getInstance();
        System.out.println("Same instance? " + (a == b));

        System.out.println("\n========== GAME SESSION ENDED ==========");
    }
}