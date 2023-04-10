package treasure;

import adventurers.Adventurer;

public class Potion extends Treasure {
    // potion has no combat bonus
    public Potion() {
        super("Potion");
    }

    public void modify(Adventurer a){ a.incrementHitpoints(); }
}
