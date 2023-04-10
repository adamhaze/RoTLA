package rooms;
import java.util.*;

import treasure.*;

public class Room {
    private ArrayList<Room> connectedRooms;
    private final int[] roomNumber = {0, 0, 0};
    private String name;
    public Treasure treasureObject = null; // assumption: Rooms can only hold 1 treasure object at a time

    public boolean adventurerPresent;

    public Room(int floor, int row, int col){
        roomNumber[0] = floor;
        roomNumber[1] = row;
        roomNumber[2] = col;
        connectedRooms = new ArrayList<Room>();
        adventurerPresent = false;
        name = floor + "" + row + "" + col;
    }


    /*THESE FUNCTIONS BELOW ARE AN EXAMPLE OF ENCAPSULATION
    This demonstrates this principle because all of the attributes of a room are private
    to shield them from improper access, and can only be accessed and set by using these
    methods which manipulates the data in the way that the developer lays out.
    All of the details relating to the Room objects is contained within the object, and can only be
    retrieved by the getters and setters.
     */

    public int[] getRoomNumber () { return roomNumber; }

    public ArrayList<Room> getConnectedRooms(){
        return connectedRooms;
    }

    public String getName(){ return name; }

    //THIS WAS MIRRORED FROM BRUCE'S PROJECT 2 CODE
    public static ArrayList<Room> CreateAllRooms() {
        ArrayList<Room> rooms = new ArrayList<Room>();

        Random rand = new Random();

        rooms.add(new Room(0, 1, 1));
        for (int level = 1; level <= 4; level++) {
            for (int x = 0; x <= 2; x++) {
                for (int y = 0; y <= 2; y++) {
                    //if (x==0 & y==0) continue;
                    Room newRoom = new Room(level, x, y);
//                    Treasure tr = null;
//                    //TODO: this is a 40% chance rn, but we can change that if the early rooms get too much of the treasure
//                    if(rand.nextInt(10) >= 3){
//                        int trType;
//                        do {
//                            trType = rand.nextInt(6);
//                        }while(treasureCounts[trType]==0);
//
//                        switch(trType){
//                            case 0 -> {tr = new Sword(); treasureCounts[trType] -= 1;}
//                            case 1 -> {tr = new Gem(); treasureCounts[trType] -= 1;}
//                            case 2 -> {tr = new Armor(); treasureCounts[trType] -= 1;}
//                            case 3 -> {tr = new Portal(); treasureCounts[trType] -= 1;}
//                            case 4 -> {tr = new Trap(); treasureCounts[trType] -= 1;}
//                            case 5 -> {tr = new Potion(); treasureCounts[trType] -= 1;}
//                        }
//                        //System.out.println(Arrays.toString(newRoom.getRoomNumber()));
//                    }
//                    if(!(tr==null)) newRoom.treasureObject = tr;
                    rooms.add(newRoom);
                }
            }
        }
        ArrayList<Treasure> newTreasures = new ArrayList<>();
        for(int tr=0; tr<6; tr++){
            for(int i=0;i<4;i++){
                switch(tr){
                    case 0 -> newTreasures.add(new Sword());
                    case 1 -> newTreasures.add(new Gem());
                    case 2 -> newTreasures.add(new Armor());
                    case 3 -> newTreasures.add(new Portal());
                    case 4 -> newTreasures.add(new Trap());
                    case 5 -> newTreasures.add(new Potion());
                }
            }
        }
        for(Treasure tr : newTreasures){
            Room random;
            do {
                random = Room.GetRandomRoom(rooms, false);
            }while(random.treasureObject != null);
            random.treasureObject = tr;
        }

        for (Room room : rooms) {
            room.ConnectRoom(rooms);
        }
        return rooms;
    }

    // borrowed from Bruce's project 2 code
    public static Room GetRoomByName(ArrayList<Room> rooms, String name) {
        Room found = null;
        for (Room r : rooms) {
            if (Objects.equals(r.name, name)) {
                found = r;
                break;
            }
        }
        return found;
    }

    //THIS WAS MIRRORED FROM BRUCE'S PROJECT 2 CODE
    private void ConnectRoom(ArrayList<Room> rooms) {
        for (int i = 0; i < connections.length; i++) {
            // given the room name, look it up in the connections array and get the
            // corresponding list of rooms
            if (Objects.equals(connections[i], name)) {
                ArrayList<String> names = new ArrayList<>(Arrays.asList(connections[i + 1].split(",")));
//                out("Room " + this.name + " is connected to ");
                for (String name : names) {
                    Room r = GetRoomByName(rooms, name);
                    //System.out.println("Room " + r.name);
                    this.connectedRooms.add(r);
                }
            }
        }
    }

    // borrowed from Bruce's project 2 code
    public static Room GetRandomRoom(ArrayList<Room> rooms, boolean outsideOnly) {
        //int level = Random.rndFromRange(1,4);
        int level = (int) ((Math.random() * ((4+1) - 1)) + 1);
        int x,y;
        do {
            x = (int) (Math.random() * 3);
            y = (int) (Math.random() * 3);
        } while (outsideOnly && (x == 1) && (y == 1));
        return GetRoomByName(rooms, ""+level+x+y);
    }

    // borrowed from Bruce's project 2 code
    public static Room FindOrbitRoom(ArrayList<Room> rooms, Room room, String dir) {
        int index = -1;
        for (int i = 0; i < orbitRooms.length; i++) {
            // given the room name, look it up in the orbitRooms array and determine the orbit room
            // based on direction
            if (Objects.equals(orbitRooms[i], room.name)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            // get the 2 possible rooms from the orbitRooms list
            ArrayList<String> names = new ArrayList<>(Arrays.asList(orbitRooms[index + 1].split(",")));
            String name;
            if (dir.equals("clockwise")) name = names.get(0);
            else name = names.get(1);  // Direction.COUNTERCLOCKWISE
            return GetRoomByName(rooms, name);
        }
        else return null;
    }

    // borrowed from Bruce's project 2 code
    private static final String[] orbitRooms = {
            "100", "110,101",
            "101", "100,102",
            "102", "101,112",
            "110", "100,120",
            "112", "102,122",
            "120", "121,110",
            "121", "122,120",
            "122", "112,121",
            "200", "210,201",
            "201", "200,202",
            "202", "201,212",
            "210", "200,220",
            "212", "202,222",
            "220", "221,210",
            "221", "222,220",
            "222", "212,221",
            "300", "310,301",
            "301", "300,302",
            "302", "301,312",
            "310", "300,320",
            "312", "302,322",
            "320", "321,310",
            "321", "322,320",
            "322", "312,321",
            "400", "410,401",
            "401", "400,402",
            "402", "401,412",
            "410", "400,420",
            "412", "402,422",
            "420", "421,410",
            "421", "422,420",
            "422", "412,421"
    };
    // borrowed from Bruce's project 2 code
    private static final String[] connections = {
            "011", "111",
            "100", "110,101",
            "101", "100,111,102",
            "102", "101,112",
            "110", "100,111,120",
            "111", "011,110,101,121,112,211",
            "112", "102,111,122",
            "120", "110,121",
            "121", "120,111,122",
            "122", "112,121",
            "200", "210,201",
            "201", "200,211,202",
            "202", "201,212",
            "210", "200,211,220",
            "211", "210,201,221,212,111,311",
            "212", "202,211,222",
            "220", "210,221",
            "221", "220,211,222",
            "222", "212,221",
            "300", "310,301",
            "301", "300,311,302",
            "302", "301,312",
            "310", "300,311,320",
            "311", "310,301,321,312,211,411",
            "312", "302,311,322",
            "320", "310,321",
            "321", "320,311,322",
            "322", "312,321",
            "400", "410,401",
            "401", "400,411,402",
            "402", "401,412",
            "410", "400,411,420",
            "411", "410,401,421,412,311",
            "412", "402,411,422",
            "420", "410,421",
            "421", "420,411,422",
            "422", "412,421"
    };

    // fill all adjacent rooms for current room within class itself
//    public void addAdjacentRooms(){
//         // System.out.println("Room: " + roomNumber[0] + " - " + roomNumber[1] + " - " + roomNumber[2]);
//
//        // add all adjacent rooms on CURRENT FLOOR
//        if (roomNumber[0] != 0) {
//            for (int row = 0; row <= 2; row++) {
//                for (int col = 0; col <= 2; col++) {
//                    // ignore current location
//                    if (row == roomNumber[1] && col == roomNumber[2]) {
//                        continue;
//                    }
//                    if (dist(row, col)) { // if distance < 1, we can reach it from current room
//                        int[] temp = {roomNumber[0], row, col};
//                        adjacentRooms.add(temp);
//                        // System.out.println("Adjacent: " + temp[0] + " - " + temp[1] + " - " + temp[2]);
//                    }
//                }
//            }
//        }
//
//        // add prev/next floor access for center room
//        if (roomNumber[1] ==1 && roomNumber[2] == 1){
//            for (int floor = roomNumber[0]-1; floor <= roomNumber[0]+1; floor++){
//                // can't go to floor -1 or 5, and don't want to include current location
//                if (floor <= 0 || floor == 5 || floor == roomNumber[0]) { continue; }
//
//                int[] temp = {floor, 1, 1};
//                adjacentRooms.add(temp);
//                // System.out.println("Adjacent: " + temp[0] + " - " + temp[1] + " - " + temp[2]);
//            }
//        }
//    }

}
