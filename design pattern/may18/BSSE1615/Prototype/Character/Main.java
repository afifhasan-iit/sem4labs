package Prototype.Character;

public class Main {
    public static void main(String[] args) {
        
        CharacterFactory factory = new CharacterFactory();

        GameCharacter warrior = factory.createWarrior("Thorffin", 150, "Axe", "Heavy Armor", java.util.Arrays.asList("Slash", "Block"), "WarriorAnimation");
        GameCharacter mage = factory.createMage("Gandalf", 100, "Staff", "Robe", java.util.Arrays.asList("Fireball", "Teleport"), "MageAnimation");
        GameCharacter archer = factory.createArcher("Legolas", 120, "Bow", "Light Armor", java.util.Arrays.asList("Shoot Arrow", "Evade"), "ArcherAnimation");
    

        GameCharacter clonedWarrior = warrior.clone();
        GameCharacter clonedMage = mage.clone();
        GameCharacter clonedArcher = archer.clone();

        clonedWarrior.setHealth(200);
        clonedMage.setHealth(150);
        clonedArcher.setHealth(180);

        System.out.println("Original Warrior: " + warrior.getName() + ", Health: " + warrior.getHealth());
        System.out.println("Cloned Warrior: " + clonedWarrior.getName() + ", Health: " + clonedWarrior.getHealth());

        System.out.println("Original Mage: " + mage.getName() + ", Health: " + mage.getHealth());
        System.out.println("Cloned Mage: " + clonedMage.getName() + ", Health: " + clonedMage.getHealth());

        System.out.println("Original Archer: " + archer.getName() + ", Health: " + archer.getHealth());
        System.out.println("Cloned Archer: " + clonedArcher.getName() + ", Health: " + clonedArcher.getHealth());
    }
}
