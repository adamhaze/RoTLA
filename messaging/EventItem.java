package messaging;


/*
 * This class is part of our OBSERVER design pattern
 * this class defines the events that are sent from Publisher to Subscriber
 * */

// each class attribute in EventItem indicates a specific event that happened
// NAME is the only attribute set on instantiation, b/c every event is tied to a Creature/Adventurer ID
// (i.e. characterRoom is set when the event is a character MOVE to new room)
// Tracker and Logger receive the EventItem and process them accordingly
public class EventItem {
    public String character; // every event item includes character name
    public String characterRoom; // included in event of MOVE
    public String celebrationString; // included in event of Adventurer celebrates
    public boolean damageTaken; // included in event of Adventurer taking damage
    public boolean defeated; // included in event of character being defeated
    public String treasureFound; // included in event of Adventurer finding treasure

    // if creatureBattled!=null AND damageTaken=true, Adventurer LOSES combat; else Adventurer WINS combat
    public String creatureBattled; // included in event of combat

    public boolean turnChange; // included in event of EndOfTurn


    public EventItem(String name){
        this.character = name;
        this.characterRoom=null;
        this.celebrationString=null;
        this.damageTaken=false;
        this.defeated=false;
        this.treasureFound=null;
        this.creatureBattled=null;
        this.turnChange=false;
    }

}
