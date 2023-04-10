package treasure;

import adventurers.Adventurer;

public class Sword extends Treasure {
    // Sword adds +1 to Adventurer combat roll
    public Sword() {
        super("Sword");
    }

    public void modify(Adventurer a){ a.combatBonus+=1; }
}
