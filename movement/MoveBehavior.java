package movement;
import rooms.Room;

import java.util.ArrayList;
import java.util.Random;

// interface for each concrete Creature and Adventurer to implement
// from current location, select next location from list of Rooms, and return to
// Creature or Adventurer instance
public interface MoveBehavior {
    Room move(Room currentRoom, ArrayList<Room> rooms);
}


