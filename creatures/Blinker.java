package creatures;

import movement.BlinkerMoveBehavior;
import rooms.Room;

import java.util.ArrayList;
import java.util.Random;

// Concrete class sets the creature's ID, starting location, and move behavior
// Blinker finds its own random room to start in on the 4th floor b/c
// getRandomRoom becomes too complicated when accounting for setting Blinkers starting
// location and moving the Blinker
public class Blinker extends Creature {
    public Blinker (String ID, ArrayList<Room> rooms) {
        super(ID);
        movement = new BlinkerMoveBehavior();
        String name = "4"; // Blinker starts on 4th floor
        int x,y;
        do {
            x = (int) (Math.random() * 3);
            y = (int) (Math.random() * 3);
        } while(x==0 && y==0);

        name+=x;
        name+=y;
        this.setLocation(Room.GetRoomByName(rooms, name));
    }
}
