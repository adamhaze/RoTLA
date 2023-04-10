package movement;

import rooms.Room;

import java.util.ArrayList;

// Seeker gets all connected rooms from Room class, and checks to see if any
// of the connected rooms have an Adventurer present --> return first room that satisfies this
// else, return current room = no move
public class SeekerMoveBehavior implements MoveBehavior {
    public Room move(Room currentRoom, ArrayList<Room> rooms) {
        ArrayList<Room> connected = currentRoom.getConnectedRooms();
        int floor = currentRoom.getRoomNumber()[0];
        for(Room r : connected){
            // require Seeker to choose room on same floor
            if (!(r.getRoomNumber()[0] == floor)) continue;
            if (r.getRoomNumber()[1]==0 && r.getRoomNumber()[2]==0) continue;
            if (r.adventurerPresent){
                return r;
            }
        }
        return currentRoom;

    }
}
