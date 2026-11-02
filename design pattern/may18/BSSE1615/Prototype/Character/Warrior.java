package Prototype.Character;

public class Warrior extends GameCharacter {

    public Warrior(String name, int health, String weapon, String armor, java.util.List<String> skillSet, String animationProfile) {
        super(name, health, weapon, armor, skillSet, animationProfile);
    }

    public Warrior(Warrior target) {
        super(target);
    }

    @Override
    public GameCharacter clone() {
        return new Warrior(this);
    }

    public void useShield() {
        System.out.println(getName() + " uses shield to block incoming attack!");
    }

    public void equipSword() {
        System.out.println(getName() + " equips a sword for close combat!");
    }

    public void equipShield() {
        System.out.println(getName() + " equips a shield for defense!");
    }

    public int getHealth() {
        return super.getHealth();
    }

    
}
