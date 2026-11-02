package Prototype.Character;

public class CharacterFactory {
    
    public GameCharacter createWarrior(String name, int health, String weapon, String armor, java.util.List<String> skillSet, String animationProfile) {
        return new Warrior(name, health, weapon, armor, skillSet, animationProfile);
    }

    public GameCharacter createMage(String name, int health, String weapon, String armor, java.util.List<String> skillSet, String animationProfile) {
        return new Mage(name, health, weapon, armor, skillSet, animationProfile);
    }

    public GameCharacter createArcher(String name, int health, String weapon, String armor, java.util.List<String> skillSet, String animationProfile) {
        return new Archer(name, health, weapon, armor, skillSet, animationProfile);
    }
}
