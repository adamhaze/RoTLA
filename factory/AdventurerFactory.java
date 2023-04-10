package factory;

import adventurers.*;
import rooms.Room;

import java.util.ArrayList;
import java.util.Scanner;

// FACTORY PATTERN (adventurers)

public class AdventurerFactory {

    public ArrayList<Room> rooms;
    public AdventurerFactory(ArrayList<Room> rooms){
        this.rooms = rooms;
    }

    public Adventurer createAdventurer(String name, int advType, Scanner input){
        return switch (advType) {
            case 1 -> new Brawler(name, "BR", rooms.get(0),input);
            case 2 -> new Runner(name, "RN", rooms.get(0),input);
            case 3 -> new Sneaker(name, "SN", rooms.get(0),input);
            case 4 -> new Thief(name, "TH", rooms.get(0),input);
            default -> null;
        };
    }
}

