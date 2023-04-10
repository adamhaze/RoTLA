package treasure;

// so far, only handling the dice roll combat bonuses that some of the Treasure objects have
// TODO: implement bonuses for Portal, Trap and Potion

import adventurers.Adventurer;

public abstract class Treasure {
    String ID; // object identifier
    public Treasure(String item){
        this.ID = item;
    }

    public String getID() {
        return ID;
    }

    public abstract void modify(Adventurer a);
    @Override
    public String toString(){
        return getID();
    }
}

