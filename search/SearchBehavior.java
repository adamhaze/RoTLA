package search;
import treasure.Treasure;
import rooms.Room;

/*
 * This interface is an example of STRATEGY
 * We are delegating the search behavior of each adventurer to this interface,
 * and handling all varying search behaviors within the specific implementations
 * */

// Takes in Room that is being searched, returns Treasure item that's found
// or null if unsuccessful search
public interface SearchBehavior {
    // return Treasure item found, or null if unsuccessful search
   Treasure search(Room current);

    default int DiceRoll(){
        // code for random num generation: https://www.baeldung.com/java-generating-random-numbers-in-range
        return (int) ((Math.random() * 6) + 1) + (int) ((Math.random() * 6) + 1);
    }
}



