package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import rooms.Room;
import treasure.*;

import java.util.ArrayList;

class RoomTest {
    private final ArrayList<Room> rooms = Room.CreateAllRooms();
    //private int treasureCnt = 0;


    @Test
    void numRooms() {
        assertEquals(37, rooms.size());
    }

    @Test
    void checkAdventurerStartRoomFeatures() {
        assertEquals("011", rooms.get(0).getName());
        assertEquals(1, rooms.get(0).getConnectedRooms().size());
        assertEquals("111", rooms.get(0).getConnectedRooms().get(0).getName());
    }

    @Test
    void numTreasures() {
        // count number of treasure items scattered throughout rooms
        int treasureCnt=0;
        for (Room r : rooms){
            if (r.treasureObject != null) treasureCnt++;
        }
         assertEquals(24, treasureCnt);
    }
}