package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import creatures.*;
import org.junit.jupiter.api.Test;
import rooms.Room;

import java.util.ArrayList;

public class CreatureTest {
    private final ArrayList<Room> rooms = Room.CreateAllRooms();
    private final Blinker blinker = new Blinker("BL"+1, rooms);
    private final Orbiter orbiter = new Orbiter("OR"+1, rooms);
    private final Seeker seeker = new Seeker("SK"+1, rooms);

    @Test
    void checkIDs() {
        assertEquals("BL"+1, blinker.getID());
        assertEquals("OR"+1, orbiter.getID());
        assertEquals("SK"+1, seeker.getID());
    }


}
