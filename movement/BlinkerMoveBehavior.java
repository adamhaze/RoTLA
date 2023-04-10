package movement;

import rooms.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class BlinkerMoveBehavior implements MoveBehavior {
    public Room move(Room currentRoom, ArrayList<Room> rooms) {
        Room nextRoom;
        do{
            nextRoom = Room.GetRandomRoom(rooms, false);
        }while(Objects.equals(currentRoom.getName(), nextRoom.getName()));
        return nextRoom;
    }
}
