package Singleton.Game_Settings;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GameSettingsManager {
    
    private int volumeLevel;
    private String difficultyMode;
    private String language;
    private int brightness;
    private String resolution;

    private static GameSettingsManager instance;

    private GameSettingsManager() {
        this.volumeLevel = 50;
        this.difficultyMode = "Normal";
        this.language = "English";
        this.brightness = 70;
        this.resolution = "1920x1080";
    }

    public static GameSettingsManager getInstance() {
        if (instance == null) {
            synchronized (GameSettingsManager.class) {
                if (instance == null) {
                    instance = new GameSettingsManager();
                }
            }
        }
        return instance;
    }

    public int getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(int volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    public void saveSettings() {
        
        try(PrintWriter printer = new PrintWriter(new FileWriter("GameSettings.txt"))) {
            printer.println("Volume: " + volumeLevel);
            printer.println("Difficulty: " + difficultyMode);
            printer.println("Language: " + language);
            printer.println("Brightness: " + brightness);
            printer.println("Resolution: " + resolution);
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        System.out.println("Loading settings from file...");
        // Placeholder
    }

    public String getDifficultyMode() {
        return difficultyMode;
    }

    public void setDifficultyMode(String difficultyMode) {
        this.difficultyMode = difficultyMode;
    }

    public String getLanguage() {
        return language;
    }

    public void changeLanguage(String language) {
        this.language = language;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void displayAllSettings() {
        System.out.println("Current Game Settings:");
        System.out.println("  Volume    : " + volumeLevel);
        System.out.println("  Difficulty: " + difficultyMode);
        System.out.println("  Language  : " + language);
        System.out.println("  Brightness: " + brightness);
        System.out.println("  Resolution: " + resolution);
    }
}
