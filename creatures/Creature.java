package creatures;

import movement.MoveBehavior;
import rooms.Room;

import java.util.ArrayList;

public abstract class Creature {

    private String ID;
    public Room location;
    public boolean alive;

    MoveBehavior movement;

    public Creature(String ID)
    {
        this.ID = ID;
        this.alive = true;
        this.location = null;

    }
    /* THIS IS AN EXAMPLE OF COHESION
    This is a good example of a class that has good cohesion because the only things that it is
    assigned to do is manipulate the variables within its own class, and there are no methods in the
    class that would be better delegated to a different portion of the code. This class accomplishes only the
    tasks that it needs to and nothing more.
     */

    public void setID(String i){
        ID = i;
    }
    public String getID(){
        return ID;
    }
    public void setLocation(Room loc){
        location = loc;
    }
    public Room getLocation(){
        return location;
    }

    // takes in all rooms, and each MoveBehavior class handles choosing the next room
    public void performMove(ArrayList<Room> rooms){
        setLocation(movement.move(location, rooms));
        //System.out.println(ID + " moves to " + location.getName());
    }
}

