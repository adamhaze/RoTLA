package treasure;

import adventurers.Adventurer;
import rooms.Room;

import java.util.ArrayList;

public class Portal extends Treasure {
    // Portal has no combat bonus
    public Portal() {
        super("Portal");
    }

    //THIS DOES NOTHING, MOVEMENT GETS HANDLED IN ADVENTURER
    public void modify(Adventurer a){};
}
