package movement;

import rooms.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class OrbiterMoveBehavior implements MoveBehavior {
    public Room move(Room currentRoom, ArrayList<Room> rooms) {
        // TODO: make this a random decision between clockwise and counterclock
        String direction = "clockwise";
        // TODO: implement findOrbitRoom, which needs to consider direction
        return Room.FindOrbitRoom(rooms, currentRoom, direction);
    }
}
