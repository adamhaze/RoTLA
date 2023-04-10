package movement;

import rooms.Room;

import java.util.ArrayList;
import java.util.Scanner;

public class AdventurerMoveBehavior implements MoveBehavior {
    Scanner input;
    public AdventurerMoveBehavior(Scanner in){
        this.input = in;
    }
    public Room move(Room currentRoom, ArrayList<Room> rooms) {
        // set currentRoom as no adventurer present (handle after all Adventurers move)
        currentRoom.adventurerPresent = false;
        // get adjacent rooms to adventurer current location
        ArrayList<Room> adj = currentRoom.getConnectedRooms();
        // randomly select next room
        System.out.println("Here are your choices for rooms to move:");
        for(Room r: adj){
            System.out.print(r.getName()+ " ");
        }
        System.out.println();
        System.out.print("Enter a room number to move to:");
        boolean isNotValid = true;
        String choice;
        input.nextLine();
        do {
            choice = input.nextLine();

            for(Room r: adj){
                if(choice.equals(r.getName())){
                    isNotValid=false;
                }
            }
            if(isNotValid) System.out.println("Invalid room number, try again:");
        }while(isNotValid);

        return Room.GetRoomByName(rooms, choice);
        //return adj.get((int) (Math.random() * adj.size()));

    }
}
