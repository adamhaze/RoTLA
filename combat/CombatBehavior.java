package combat;

import creatures.Creature;
import rooms.Room;

import java.util.ArrayList;

/*
* This interface is an example of STRATEGY
* We are delegating the combat behavior of each adventurer to this interface,
* and handling all varying combat behaviors within the specific implementations of
* the interface
* */

public abstract class CombatBehavior {

    public String celebration = "";

    public abstract boolean fight(Creature c, int combatBonus);
    public String celebrate() { return celebration; }

    public int DiceRoll(){
        // code for random num generation: https://www.baeldung.com/java-generating-random-numbers-in-range
        return ((int) ((Math.random() * 6) + 1) + (int) ((Math.random() * 6) + 1));
    }
    // default function to handle comparison between creature and adventurer dice roll
    // eliminates duplicate code
    public boolean compare(int aRoll, int cRoll, Creature c){
        if (aRoll == cRoll){
            //System.out.print(" escapes combat and celebrates: ");
            return true;
        }
        if (aRoll > cRoll){
            c.alive = false;
            //System.out.print(" wins their fight and celebrates: "+celebrate());
            return true;
        } else {
            return false;
        }

    }
}


