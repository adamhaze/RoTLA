package treasure;

import adventurers.Adventurer;

public class Armor extends Treasure {
    // Armor bonus is -1 to Creature roll, same as +1 to Adventurer roll
    public Armor() {
        super("Armor");
    }

    public void modify(Adventurer a){
        a.combatBonus+=1;
    }
}
