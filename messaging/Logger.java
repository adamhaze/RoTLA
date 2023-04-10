package messaging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Flow;

/*
 * This class is part of our OBSERVER design pattern
 * this is one of the observer classes which takes in events that
 * are published by the Subject (publisher) in a PUSH method
 * */

public class Logger implements Flow.Subscriber<EventItem>{
    private Flow.Subscription subscription; // necessary for communication between Publisher and Subscriber
    //private Map nameMap;

    // maintain a single string that formats each event from a given turn into human-readable
    private String eventString;
    private int turnNumber; // maintain a running turn number

    // FACTORY PATTERN - eager instantiation
    private static Logger uniqueInstance = new Logger();

    private Logger() {
        this.eventString = "";
        this.turnNumber = 0;
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

    public static Logger getLogger() {
        return uniqueInstance;
    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        //System.out.println("onSubscribe");
        this.subscription = subscription;
        subscription.request(1);

    }

    // This function called everytime Publisher publishes an event (publisher.submit() in GameDriver)
    // receives the EventItem passed in publisher.submit(event)
    // The function simply takes the event and formats it into a human-readable string, and adds it to eventString
    // eventString compiles all events as 'new lines' for a given turn, then writes to a file at the turn change
    @Override
    public void onNext(EventItem event) {
        // format character name via nameMap
        String name = (String) nameMap.get(event.character.substring(0,2));
        // event of character moving to new room (both Adventurers and Creatures)
        if (event.characterRoom != null) eventString += String.format("%s enters room %s\n",name,event.characterRoom);
        // event of Adventurer vs Creature battle
        if (event.creatureBattled != null){
            // event that Creature wins
            if (event.damageTaken) eventString += String.format("%s defeats %s in battle!\n",nameMap.get(event.creatureBattled.substring(0,2)), name);
            // event that Adventurer wins
            else eventString += String.format("%s defeats %s in battle!\n",name, nameMap.get(event.creatureBattled.substring(0,2)));
        }
        if (event.damageTaken) eventString += String.format("%s takes 1 point of damage.\n",name);
        if (event.defeated) eventString += String.format("%s has been defeated, and is now removed from the game. RIP...\n",name);
        if (event.treasureFound != null) eventString += String.format("%s found a treasure item: %s\n",name,event.treasureFound);
        if (event.celebrationString != null) eventString += String.format("%s celebrates: %s\n",name, event.celebrationString);
        if (event.turnChange){
            // On event of a turn change, write the cumulative events (compiled in eventString) to a file Logger-n.txt
            // Then, reset eventString so we can compile the events for the next turn from scratch
            turnNumber++;
            try {
                writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            eventString = "";
        }
        subscription.request(1);

    }

    // utility function used to write eventString to a txt file at end of each turn
    private void writeToFile() throws IOException {
        String filename = String.format("LoggerFiles/Logger-%d.txt",turnNumber);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(eventString);
        writer.close();
    }


    @Override
    public void onError(Throwable throwable) {
        System.out.println("onError");
    }

    // chose not to implement onComplete() b/c Logger is already performing its logging
    // duties at each event of a turn change, which will happen once before the game ends as well
    @Override
    public void onComplete() {

    }


}
