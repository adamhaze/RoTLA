package command;

import adventurers.Adventurer;
import messaging.EventItem;

import java.util.concurrent.SubmissionPublisher;

public class SearchCommand implements UserCommand{

    Adventurer user;
    SubmissionPublisher<EventItem> publisher;

    public SearchCommand(Adventurer a, SubmissionPublisher<EventItem> p){
        this.publisher = p;
        this.user = a;
    }

    public void execute(){

        EventItem e = this.user.searchForTreasure();
        if(e!=null){
            publisher.submit(e);
        }
    }
}
