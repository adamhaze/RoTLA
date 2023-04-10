package combat;

import creatures.Creature;
import rooms.Room;

public class UntrainedBehavior extends CombatBehavior {
    public boolean fight(Creature enemy, int combatBonus) {
        // no CombatBehavior bonus (still could have bonus from Treasure object)
        return compare(DiceRoll()+combatBonus, DiceRoll(), enemy);

    }
}
