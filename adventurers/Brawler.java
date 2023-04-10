package adventurers;

import combat.CombatDecorator.Dance;
import combat.CombatDecorator.Jump;
import combat.CombatDecorator.Shout;
import combat.CombatDecorator.Spin;
import combat.ExpertBehavior;
import movement.AdventurerMoveBehavior;
import search.CarefulSearchBehavior;
import rooms.Room;
import search.CarelessSearchBehavior;

import java.util.Scanner;

public class Brawler extends Adventurer {
    public Brawler (String name, String ID, Room location, Scanner in) {
        //calls the Adventurer constructor, then initializes individual variables
        super(name, ID, location);
        hitpoints = 12;
        search = new CarelessSearchBehavior();
        movement = new AdventurerMoveBehavior(in);
        encounter = new ExpertBehavior();
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
        /*THE VARIABLE ID IS AN EXAMPLE OF IDENTITY
        This is identity because this variable is specific each adventurer. Each adventurer only gets instantiated once
        so the Brawler will have the ID of BR which can be used to uniquely identify the Brawler adventurer from
        any of the other adventurers
        */
    }



}
