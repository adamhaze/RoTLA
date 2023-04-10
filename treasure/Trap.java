package treasure;

import adventurers.Adventurer;

public class Trap extends Treasure {
    public Trap() {
        super("Trap");
    }

    public void modify(Adventurer a){ a.decrementHitpoints(); }
}
