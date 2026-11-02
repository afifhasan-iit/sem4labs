package Prototype.Character;

public class Mage extends GameCharacter {

    public Mage(String name, int health, String weapon, String armor, java.util.List<String> skillSet, String animationProfile) {
        super(name, health, weapon, armor, skillSet, animationProfile);
    }

    // Copy constructor
    public Mage(Mage target) {
        super(target);
    }

    @Override
    public GameCharacter clone() {
        return new Mage(this);
    }

    public void castSpell() {
        System.out.println(getName() + " casts a powerful spell!");
    }

    public void manaRecharge() {
        System.out.println(getName() + " recharges mana to prepare for the next spell!");
    }
    
    public int getHealth() {
        return super.getHealth();
    }
}
