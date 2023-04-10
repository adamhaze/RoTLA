package adventurers;

import combat.CombatDecorator.*;
import combat.TrainedBehavior;
import movement.AdventurerMoveBehavior;
import rooms.Room;
import search.CarefulSearchBehavior;

import java.util.Scanner;

public class Thief extends Adventurer {
    public Thief (String name, String ID, Room location, Scanner in) {
        //calls the Adventurer constructor, then initializes individual variables
        super(name, ID, location);
        hitpoints = 10;
        search = new CarefulSearchBehavior();
        movement = new AdventurerMoveBehavior(in);
        encounter = new TrainedBehavior();
        // creates 0-2 random celebrations when initialized
        int numCelebs = (int)(Math.random() * 3);
        /*
        THIS IS WHERE WE ARE DECORATING THE COMBAT BEHAVIOR
        We are wrapping the combat behavior in a decorator so that the
        fight functionality is the same, but now adds the celebration behavior as well
         */
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
