package adventurers;

import combat.CombatDecorator.Dance;
import combat.CombatDecorator.Jump;
import combat.CombatDecorator.Shout;
import combat.CombatDecorator.Spin;
import combat.UntrainedBehavior;
import movement.AdventurerMoveBehavior;
import rooms.Room;
import search.CarefulSearchBehavior;
import search.QuickSearchBehavior;

import java.util.Scanner;
/*THIS CLASS AND THE OTHER CLASSES THAT EXTEND ADVENTURER ARE EXAMPLES OF INHERITANCE
This is represented here because all 4 of the concrete adventurer classes extend the
abstract Adventurer class and inherits many of its methods and attributes to use in each
subclass. You can see this because when we use super() in the constructor, this is calling
the superclasses constructor to set up attributes and then setting others to things specific
to this subclass.
 */

public class Runner extends Adventurer {
    public Runner (String name, String ID, Room location, Scanner in) {
        //calls the Adventurer constructor, then initializes individual variables
        super(name, ID, location);
        hitpoints = 10;
        search = new QuickSearchBehavior();
        movement = new AdventurerMoveBehavior(in);
        encounter = new UntrainedBehavior();
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
