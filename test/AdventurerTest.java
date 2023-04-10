package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import adventurers.*;
import rooms.Room;

class AdventurerTest {
    private final Room startRoom = new Room(0,1,1);
    private final Brawler brawler = new Brawler("testBrawler","BR", startRoom,null);
    private final Runner runner = new Runner("testRunner","RN", startRoom,null);
    private final Sneaker sneaker = new Sneaker("testSneaker","SN", startRoom,null);
    private final Thief thief = new Thief("testThief","TH", startRoom,null);

    @Test
    void checkIDs(){
        assertEquals("BR", brawler.getID());
        assertEquals("RN", runner.getID());
        assertEquals("SN", sneaker.getID());
        assertEquals("TH", thief.getID());
    }

    @Test
    void checkLocation() {
        assertEquals(startRoom, brawler.getLocation());
        assertEquals(startRoom, runner.getLocation());
        assertEquals(startRoom, sneaker.getLocation());
        assertEquals(startRoom, thief.getLocation());
    }
}

