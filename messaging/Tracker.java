package messaging;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

/*
* This class is part of our OBSERVER design pattern
* this is one of the observer classes which takes in events that
* are published by the Subject (publisher) in a PUSH method
* */

public class Tracker implements Flow.Subscriber<EventItem>{
    private Subscription subscription; // necessary for communication between Publisher and Subscriber
    private int turnNumber;
    private int numAdventurers;
    private int numCreatures;

    private static Tracker uniqueInstance;

    // Data Structure to update throughout the game that maintains the following for each character:
    //              name, room, damage taken (only Adventurer), treasure objects (only Adventurer)
    ArrayList<TrackerDataStructure> trackingObject;
    private Tracker() {

        this.trackingObject = new ArrayList<>();
        this.turnNumber = 0;
        this.numAdventurers = 4;
        this.numCreatures = 12;
    }

    // FACTORY PATTERN - lazy instantiation
    public static synchronized Tracker getTracker() {
        if (uniqueInstance == null){
            uniqueInstance = new Tracker();
        }
        return uniqueInstance;
    }

    private final Map<String, String> nameMap = Map.of(
            "BR","Brawler",
            "RN","Runner",
            "SN","Sneaker",
            "TH","Thief",
            "OR","Orbiter",
            "SK","Seeker",
            "BL","Blinker"

    );


    @Override
    public void onSubscribe(Subscription subscription) {
        //System.out.println("onSubscribe");
        this.subscription = subscription;
        subscription.request(1);

    }

    // This function called everytime Publisher publishes an event (publisher.submit() in GameDriver)
    // receives the EventItem passed in publisher.submit(event)
    // parses EventItem into new TrackerDataStructure object if the character involved in event has not yet been tracked
    // else the EventItem is used to update the characters TrackerDataStructure object in trackingObject ArrayList
    @Override
    public void onNext(EventItem event) {
        //System.out.println("onNext");
        if (event.turnChange) turnNumber++;

        // if character has not yet been added to trackingObject
        if (characterNotYetTracked(event.character) && !event.character.equals("TURN CHANGE")){
            // add to trackingObject
            TrackerDataStructure addition = new TrackerDataStructure(event.character);
            // figure out which event happened, update addition object
            if (event.characterRoom != null) addition.trackerRoom = event.characterRoom;
            if (event.damageTaken) addition.trackerDamage = 1;
            if (event.treasureFound != null) addition.trackerTreasure.add(event.treasureFound);
            trackingObject.add(addition);
        } else{ // case of updating entry in trackingObject
            // update tracking object
            for(TrackerDataStructure t : trackingObject){
                if (t.trackerName.equals(event.character)){
                    // update t
                    if (event.characterRoom != null) t.trackerRoom = event.characterRoom;
                    if (event.damageTaken) t.trackerDamage++;
                    if (event.treasureFound != null) t.trackerTreasure.add(event.treasureFound);
                    break;
                }
            }
        }
        // handle case of eliminating character from array
        if (event.defeated) {
            trackingObject.removeIf(t -> Objects.equals(t.trackerName, event.character));
            // decrementing running number of Creatures/Adventurers
            if (isCreature(event.character)) numCreatures-=1;
            else numAdventurers-=1;
        }

        subscription.request(1);

    }

    private boolean isCreature(String name){
        return name.substring(0, 2).equals("BL") || name.substring(0, 2).equals("OR") || name.substring(0, 2).equals("SK");
    }

    // function to query the trackingObject, to see if character involved in current event has been tracked yet
    // if character HAS been tracked, we need to update trackingObject
    // if character HAS NOT been tracked, we need to add them to trackingObject
    private boolean characterNotYetTracked(String name){
        if (trackingObject.isEmpty()) return true;
        for (TrackerDataStructure t : trackingObject){
            if (t.trackerName.equals(name)) return false;
        }
        return true;
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("onError");
    }

    // Once the subscription ends, print out the cumulative results
    @Override
    public void onComplete() {
        // prints out data structure to terminal?
        printResults();
    }

    private void printResults(){
        //System.out.println("print results");
        System.out.println("\n");
        System.out.println("Tracker: Turn " + turnNumber);
        System.out.printf("Total Active Adventurers: "+numAdventurers);
        System.out.println("\n");
        System.out.printf("%-20s %-20s %-20s %-20s\n", "Adventurers", "Room", "Damage", "Treasure");
        System.out.println("--------------------------------------------------------------------------");
        for (TrackerDataStructure t : trackingObject){
            if (isCreature(t.trackerName)) continue;
            System.out.printf("%-20s %-20s %-20s %-20s\n",nameMap.get(t.trackerName),t.trackerRoom, t.trackerDamage, t.formatTreasureObjects());
            //String printString = t.trackerName + " " + t.trackerRoom + " " + t.trackerDamage;
            //System.out.println(printString);
        }
        System.out.println("\n");
        System.out.println("Total Active Creatures: "+numCreatures);
        System.out.println();

        if (numCreatures>0){
            System.out.printf("%-20s %-20s\n", "Creatures", "Room");
            System.out.println("--------------------------");
            for (TrackerDataStructure t : trackingObject){
                if (!isCreature(t.trackerName)) continue;
                System.out.printf("%-20s %-20s\n", nameMap.get(t.trackerName.substring(0,2)), t.trackerRoom);
                //String printString = t.trackerName + " " + t.trackerRoom + " " + t.trackerDamage;
                //System.out.println(printString);
            }
        }
    }
}

// Data structure to track each character's events throughout the game
// Every Adventurer/Creature will have a TrackerDataStructure instance that gets updated in Tracker (above)

class TrackerDataStructure {
    public String trackerName;
    public String trackerRoom;
    public int trackerDamage;
    public ArrayList<String> trackerTreasure;

    TrackerDataStructure(String name){
        this.trackerName=name;
        this.trackerDamage = 0;
        this.trackerTreasure = new ArrayList<>();
    }

    public String formatTreasureObjects(){
        String printString = "";
        for (String t : trackerTreasure){
            printString += (t + ",");
        }
        return printString;
    }

}



