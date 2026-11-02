package Prototype.Character;

public class Assassin extends GameCharacter {

    public Assassin(String name, int health, String weapon, String armor, java.util.List<String> skillSet, String animationProfile) {
        super(name, health, weapon, armor, skillSet, animationProfile);
    }

    public Assassin(Assassin target) {
        super(target);
    }

    @Override
    public GameCharacter clone() {
        return new Assassin(this);
    }

    public void performStealthAttack() {
        System.out.println(getName() + " : Omae wa mou shindeiru... Stealth attack executed!");
    }

    public void vanish() {
        System.out.println(getName() + " I am batman... Vanishes into the shadows!");
    }

    public int getHealth() {
        return super.getHealth();
    }
    
}
