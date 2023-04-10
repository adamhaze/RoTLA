package search;

import rooms.Room;
import treasure.Treasure;

public class CarelessSearchBehavior implements SearchBehavior {
    public Treasure search(Room current){
        if (DiceRoll() >= 7) return current.treasureObject;
        else return null;
    }
}
