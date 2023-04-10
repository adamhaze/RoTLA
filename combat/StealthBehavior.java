package combat;

import creatures.Creature;
import rooms.Room;

import java.util.ArrayList;

public class StealthBehavior extends CombatBehavior {
    public boolean fight(Creature c, int combatBonus) {
        boolean result = compare(DiceRoll() + combatBonus, DiceRoll(), c);
        //if the fight is lost, 50% chance to change the outcome to not take damage
        if(!result){
            int rand = (int) (Math.random() * 100);
            if(rand < 50){
                result = true;
            }
        }

        return result;
    }
}
