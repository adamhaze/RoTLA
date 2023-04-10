package creatures;

import movement.OrbiterMoveBehavior;
import rooms.Room;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

// Concrete class sets the creature's ID, starting location, and move behavior
public class Orbiter extends Creature {
    public Orbiter (String ID, ArrayList<Room> rooms) {
        super(ID);
        movement = new OrbiterMoveBehavior();
        // get a random room from the Room class for the Orbiter to start in
        this.setLocation(Room.GetRandomRoom(rooms, true));
    }
}
