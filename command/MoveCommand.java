package command;

import adventurers.Adventurer;
import messaging.EventItem;
import rooms.Room;

import java.util.ArrayList;
import java.util.concurrent.SubmissionPublisher;

public class MoveCommand implements UserCommand{
    Adventurer user;
    ArrayList<Room> rooms;
    SubmissionPublisher<EventItem> publisher;

    public MoveCommand(Adventurer a, ArrayList<Room> r,SubmissionPublisher<EventItem> p){
        this.user = a;
        this.rooms = r;
        this.publisher = p;
    }

    public void execute(){
        user.performMove(this.rooms);
        // publish event of Adventurer MOVE
        EventItem e = new EventItem(user.getID());
        e.characterRoom = user.getLocation().getName();
        publisher.submit(e);
    }
}
