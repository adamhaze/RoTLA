package treasure;

import adventurers.Adventurer;

public class Gem extends Treasure {
    // Gem bonus is +1 to Creature roll, same as -1 to Adventurer roll
    public Gem() {
        super("Gem");
    }

    public void modify(Adventurer a){ a.combatBonus -=1; }
}
