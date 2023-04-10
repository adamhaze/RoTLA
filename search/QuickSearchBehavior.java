package search;
import rooms.Room;
import treasure.Treasure;

public class QuickSearchBehavior implements SearchBehavior {
    public Treasure search(Room current) {

        // needs a dice roll >= 9 to find the item
        if (DiceRoll() >= 6) return current.treasureObject;
        else return null;

    }
}
