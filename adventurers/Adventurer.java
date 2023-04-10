package adventurers;

import creatures.Creature;
import combat.CombatBehavior;
import movement.MoveBehavior;
import search.SearchBehavior;
import rooms.Room;
import treasure.Treasure;
import messaging.EventItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Adventurer {

    private String ID;
    private String name;
    protected int hitpoints;
    private Room location;

    public int combatBonus;
    SearchBehavior search;
    MoveBehavior movement;
    CombatBehavior encounter;
    ArrayList<Treasure> inventory;

    HashMap<String,Boolean> seenItem;
    public Adventurer(String name, String ID, Room location){
        // used by every subclass
        this.location = location;
        this.ID = ID;
        this.inventory = new ArrayList<>();
        this.name = name;
        combatBonus = 0;

        seenItem = new HashMap<>();
        seenItem.put("Armor",false);
        seenItem.put("Gem",false);
        seenItem.put("Portal",false);
        seenItem.put("Potion",false);
        seenItem.put("Sword",false);

    }
    //public void setID(String id){ ID = id; }
    public String getID(){
        return ID;
    }
    public void decrementHitpoints(){hitpoints -= 1;}
    public void incrementHitpoints(){hitpoints+=1;}
    public int getHitpoints(){
        return hitpoints;
    }
    public void setLocation(Room loc){
        location = loc;
    }
    public Room getLocation(){
        return location;
    }
    public CombatBehavior getEncounter(){return encounter;}
    public ArrayList<Treasure> getInventory(){return inventory;}

    public HashMap<String, Boolean> getSeenItem() {return seenItem;}
    /*
    THESE 3 FUNCTIONS BELOW ARE EXAMPLES OF ABSTRACTION
    This is because these methods do very important things for the program
    but the implementation of these functions is not necessary for the user to know
    to call these methods. All they need to do is call the method that they want to use
    for a certain action and the methods will do what they need. This should also mean
    that changing the implementation of these methods should not affect any of the users
    code because, as long as the implementation still does what it is supposed to,
    the user will not need to know
     */

    // This function executes the specific search behavior of each Adventurer, and
    // handles the resulting Treasure item accordingly (same for every Adventurer)
    // It returns an EventItem representing the result of the Adventurer's search
    public EventItem searchForTreasure(){
        Treasure searchResult = search.search(location);
        EventItem result = null;
        if (searchResult != null){
            if(!this.hasItem(searchResult.getID())) {
                System.out.println(ID + " found a " + searchResult.getID());
                seenItem.put(searchResult.getID(), true);
                // add item to Adventurer's inventory, as long as its not a trap item
                if(!searchResult.getID().equals("Trap")) {
                    inventory.add(searchResult);
                }
                // apply correct modifications based on item
                searchResult.modify(this);
                //item needs to be removed from room
                this.getLocation().treasureObject = null;

                // create Event for Adventurer finding treasure
                result = new EventItem(ID);
                result.treasureFound = searchResult.getID();
            }else{
                System.out.println(ID + " already has one of these treasures, so cannot pick another up.");
            }
        } else {
            System.out.println(ID + " couldn't find treasure...");
        }
        return result;
    }

    // takes in all rooms, and each MoveBehavior class handles choosing the next room
    // directly sets the Adventurer's new location
    public void performMove(ArrayList<Room> rooms) {
        //if there is a portal in the inventory, portal to a random room and remove the portal from the inventory
        if(hasItem("Portal")){
            //set the current rooms flag to false
            System.out.println("You found a portal and were transported!");
            location.adventurerPresent = false;
            setLocation(Room.GetRandomRoom(rooms,false));
            int remInd=-1;
            //find index of portal
            for(int i=0;i<inventory.size();i++){
                if(inventory.get(i).getID().equals("Portal")){
                    remInd = i;
                    break;
                }
            }
            //remove it from inv
            inventory.remove(remInd);
        }else{
            setLocation(movement.move(location, rooms));
        }

        //System.out.println(ID + " moves to " + location.getName());
    }

    // takes in all creatures in the room, which is determined in GameDriver, and passed here
    // Returns a list of combat events of win/loss and who the Adventurer fights
    // Assumption: if Sneaker avoids fight, we say that Sneaker wins combat against Creature, but Creature doesn't die
    public ArrayList<EventItem> performCombat(ArrayList<Creature> creatures){
        ArrayList<EventItem> events = new ArrayList<>();
        for (Creature c : creatures){
            if (!c.alive) continue;
            // each event includes the Creature encountered and if Adventurer takes damage
            EventItem e = new EventItem(ID);
            e.creatureBattled = c.getID();
            // fight returns true if Adventurer wins OR doesn't fight, false = decrement hitpoints
            if (!encounter.fight(c, combatBonus)) {
                decrementHitpoints();
                e.damageTaken = true;
                System.out.println("You lost your fight and took some damage!");
            }else{
                System.out.println("You defeated " + c.getID() +"!");
            }
            //System.out.println();
            events.add(e);
        }
        return events;
    }

    public boolean hasItem(String itemID){
        boolean hasItem = false;
        for(Treasure t: inventory){
            if(t.getID().equals(itemID)){
                hasItem = true;
                break;
            }
        }
        return hasItem;
    }

    public String statsString(){
        return "=== Adventurer " + this.name + " stats ===\n" +
                "Hitpoints: " + getHitpoints() + "\n" +
                "Treasure found: " + inventory.toString() + "\n" +
                "Current location: " + getLocation().getName();
    }

}

