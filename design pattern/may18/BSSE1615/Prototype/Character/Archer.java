package Prototype.Character;

public class Archer extends GameCharacter {

    public Archer(String name, int health, String weapon, String armor, java.util.List<String> skillSet, String animationProfile) {
        super(name, health, weapon, armor, skillSet, animationProfile);
    }

    public Archer(Archer target) {
        super(target);
    }

    @Override
    public GameCharacter clone() {
        return new Archer(this);
    }

    public void shootArrow() {
        System.out.println(getName() + " shoots an arrow with precision!");
    }

    public void evade() {
        System.out.println(getName() + " swiftly evades the attack!");
    }
    
    public int getHealth() {
        return super.getHealth();
    }
}
