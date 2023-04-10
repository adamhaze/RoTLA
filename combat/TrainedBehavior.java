package combat;

import creatures.Creature;
import rooms.Room;

public class TrainedBehavior extends CombatBehavior {
    public boolean fight(Creature enemy, int combatBonus) {
        return compare(DiceRoll() + 1 + combatBonus, DiceRoll(), enemy);
    }
}
