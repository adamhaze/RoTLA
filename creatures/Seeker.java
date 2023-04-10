package creatures;

import movement.SeekerMoveBehavior;
import rooms.Room;

import java.util.ArrayList;
import java.util.Random;

// Concrete class sets the creature's ID, starting location, and move behavior
public class Seeker extends Creature {
    public Seeker (String ID, ArrayList<Room> rooms) {
        super(ID);
        movement = new SeekerMoveBehavior();
        // get a random room from the Room class for the Seeker to start in
        this.setLocation(Room.GetRandomRoom(rooms, false));
    }
}
