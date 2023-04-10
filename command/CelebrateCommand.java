package command;

import adventurers.Adventurer;
import messaging.EventItem;

import java.util.concurrent.SubmissionPublisher;

public class CelebrateCommand implements UserCommand{

    Adventurer user;
    SubmissionPublisher<EventItem> publisher;

    public CelebrateCommand(Adventurer a, SubmissionPublisher<EventItem> p){
        this.publisher = p;
        this.user = a;
    }

    public void execute(){
        EventItem e = new EventItem(user.getID());
        System.out.println(user.getID() + " celebrates: " + this.user.getEncounter().celebrate());
        e.celebrationString = this.user.getEncounter().celebrate();
        publisher.submit(e);
    }
}
