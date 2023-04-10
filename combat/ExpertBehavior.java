package combat;

import creatures.Creature;
import rooms.Room;

public class ExpertBehavior extends CombatBehavior {
    public boolean fight(Creature enemy, int combatBonus) {
        return compare(DiceRoll() + 2 + combatBonus, DiceRoll(), enemy);
    }
}
