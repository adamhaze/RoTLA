package command;

import adventurers.Adventurer;
import creatures.Creature;
import messaging.EventItem;

import java.util.ArrayList;
import java.util.concurrent.SubmissionPublisher;

//THIS IS A COMMAND THAT IS IMPLEMENTED FROM USERCOMMAND FOR FIGHTING
public class FightCommand implements UserCommand{

    Adventurer user;
    ArrayList<Creature> enemies;
    SubmissionPublisher<EventItem> publisher;

    public FightCommand(Adventurer a, ArrayList<Creature> c, SubmissionPublisher<EventItem> p){
        this.user = a;
        this.enemies = c;
        this.publisher = p;
    }

    public void execute(){
        // handle combat for current Adventurer
        ArrayList<EventItem> events = user.performCombat(enemies);
        // publish event for each fight that occurs in current room
        for (EventItem event : events) publisher.submit(event);
    }
}
