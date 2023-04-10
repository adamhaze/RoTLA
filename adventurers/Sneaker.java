package adventurers;

import combat.CombatDecorator.Dance;
import combat.CombatDecorator.Jump;
import combat.CombatDecorator.Shout;
import combat.CombatDecorator.Spin;
import combat.ExpertBehavior;
import combat.StealthBehavior;
import movement.AdventurerMoveBehavior;
import rooms.Room;
import search.CarefulSearchBehavior;
import search.QuickSearchBehavior;

import java.util.Scanner;

public class Sneaker extends Adventurer {
    public Sneaker (String name, String ID, Room location, Scanner in) {
        //calls the Adventurer constructor, then initializes individual variables
        super(name, ID, location);
        hitpoints = 8;
        search = new QuickSearchBehavior();
        movement = new AdventurerMoveBehavior(in);
        encounter = new StealthBehavior();
        // creates 0-2 random celebrations when initialized
        int numCelebs = (int)(Math.random() * 3);
        for(int i=0; i<numCelebs; i++) {
            int randomCeleb = (int)(Math.random() * 4);
            switch(randomCeleb){
                case 0 -> encounter = new Dance(encounter);
                case 1 -> encounter = new Shout(encounter);
                case 2 -> encounter = new Jump(encounter);
                case 3 -> encounter = new Spin(encounter);
            }
        }
    }

}
