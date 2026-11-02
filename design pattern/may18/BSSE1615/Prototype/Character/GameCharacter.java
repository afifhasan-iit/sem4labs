package Prototype.Character;

import java.util.ArrayList;
import java.util.List;

abstract public class GameCharacter {
    private String name;
    private int health;
    private String weapon;
    private String armor;
    private List<String> skillSet;
    private String animationProfile;

    public GameCharacter(String name, int health, String weapon, String armor, List<String> skillSet, String animationProfile) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.armor = armor;
        this.skillSet = skillSet;
        this.animationProfile = animationProfile;
    }

    public GameCharacter(GameCharacter target) {
        if(target != null) {
            this.name = target.name;
            this.health = target.health;
            this.weapon = target.weapon;
            this.armor = target.armor;
            this.animationProfile = target.animationProfile;
            this.skillSet = new ArrayList<>(target.skillSet);
        }
        
    }

    public abstract GameCharacter clone();

    public void attack() {
        System.out.println(name + " attacks with " + weapon);
    }

    public void move() {
        System.out.println(name + " moves using " + animationProfile);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int moreHealth) {
        this.health = moreHealth;
    }

    public void displayCharacterInfo() {
        System.out.println("\n========================");
        System.out.println("Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Weapon: " + weapon);
        System.out.println("Armor: " + armor);
        System.out.println("Skills: " + skillSet);
        System.out.println("Animation Profile: " + animationProfile);
    }

}
