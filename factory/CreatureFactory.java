package factory;

import creatures.*;
import rooms.Room;

import java.util.ArrayList;

// FACTORY PATTERN (creatures)

public class CreatureFactory {
    public ArrayList<Room> rooms;
    public CreatureFactory(ArrayList<Room> rooms){
        this.rooms = rooms;
    }
    public ArrayList<Creature> makeAll(String ID) {
        ArrayList<Creature> creatures = new ArrayList<>();
        for(int i=1; i<5; i++){
            creatures.add(createCreature(i,ID));
        }
        return creatures;
    }

    public Creature createCreature(int num, String ID){
        return switch(ID) {
            case "BL" ->  new Blinker("BL" + num, this.rooms);
            case "SK" ->  new Seeker("SK" + num, this.rooms);
            case "OR" ->  new Orbiter("OR" + num, this.rooms);
            default ->  null;
        };
    };
}

