package search;

import rooms.Room;
import treasure.Treasure;

public class CarefulSearchBehavior implements SearchBehavior {
    public Treasure search(Room current) {
        if (DiceRoll() >= 4) { return current.treasureObject; }
        else return null;
    }
}
