import adventurers.*;
import command.*;
import creatures.Creature;
import factory.*;
import messaging.Logger;
import rooms.Room;
import messaging.EventItem;
import messaging.Tracker;

import java.util.*;
import java.util.concurrent.SubmissionPublisher;


public class GameDriver {

    private ArrayList<Room> rooms;
    private ArrayList<Adventurer> adventurers;
    private ArrayList<Creature> creatures;
    private int turnNumber;
    private boolean gameOver;
    private static Scanner input = new Scanner(System.in);
    SubmissionPublisher<EventItem> publisher;
    Tracker tracker;
    Logger logger;
    // used to Map character ID's into human-readable names (only used in Tracker and Logger)


    public GameDriver(){
        turnNumber = 0;
        rooms = new ArrayList<>();
        adventurers = new ArrayList<>();
        creatures = new ArrayList<>();
        gameOver = false;
        publisher = new SubmissionPublisher<>();

        // tracker maintains a data structure to track each character's events throughout the game
        // and prints a current game status at the end of the game
        tracker = Tracker.getTracker();
        // logger compiles a list of events for a given turn, and formats them into a single string
        // which is written to a txt file at the end of each turn, summarizing all the events of the turn
        logger = Logger.getLogger();
    }

    public void initGame(){
        // TODO: randomly place treasure objects in Rooms (max 1 per room)
        rooms = Room.CreateAllRooms();

        System.out.println("Welcome to RotLA! Enter your name:");
        String name = input.nextLine();

        System.out.print("""
                Now select the type of Adventurer you would like to be:
                1. Brawler
                2. Runner
                3. Sneaker
                4. Theif
                """);
        int adventurerType = input.nextInt();

        createAdventurer(name,adventurerType);
        createCreatures();

        // subscribe the tracker and logger to our publisher
        // logger gets reset each turn, while tracker stays active until end of game
        publisher.subscribe(tracker);
        publisher.subscribe(logger);

    }

    // This function runs until single game has ended
    // InterruptedException necessary for Thread.sleep (used to ensure Tracker onComplete() function finishes)
    public void play() throws InterruptedException {
        initGame();
        UserController controller = new UserController();
        while (!gameOver) {
            turnNumber++;
            // display board and stats here...
            display();

            // perform 1 full turn
            performTurn(controller);

            // end of turn check
            gameOver = endOfTurnCheck();

            // publish event of a turn change
            // this resets the logger by writing the events of the previous turn to a file
            // tracker simply increments the turn number in its internal data structure
            EventItem turnChange = new EventItem("TURN CHANGE");
            turnChange.turnChange=true;
            publisher.submit(turnChange);

            if (turnNumber==15) break;

        }
        System.out.println("FINAL STATE");
        display();

        // closes the subscription and calls onComplete() method for both Tracker and Logger
        publisher.close();
        Thread.sleep(5000); // necessary to ensure Tracker has time to print game state stats
    }

    // This function borrowed from Bruce's example (slightly modified)
    private void performTurn(UserController controller){
        ArrayList<Creature> creaturesInCurrRoom = getCreaturesInRoom(adventurers.get(0).getLocation());
        //process users action choice
        if(creaturesInCurrRoom.isEmpty()){
            emptyRoomAction(controller);
        }else{
            roomWithCreaturesAction(controller);
        }

        sweepEliminatedCharacters(); // if any Adventurer or Creature died
        setAdventurerPresenceInRooms(); // set each Room objects adventurerPresent flag

        //if you die before the creatures move, the game is over
        if(adventurers.size()==0){
            return;
        }
//
//        /*THIS IS AN EXAMPLE OF POLYMORPHISM
//         * These loops are going through an ArrayList of Adventurers/Creatures, which are abstract classes
//         * Each object is of its own type and inherits from the parent abstract class
//         * Each of the objects, however, have a method that they all must inherit and implement from the abstract class
//         * but each of them are calling their own version of the methods.
//         * */
//
        System.out.println("The creatures moved rooms ...");
        for (Creature c: creatures) performOneCreatureTurn(c);
        // handle combat after creatures move
        Adventurer a = adventurers.get(0);
        ArrayList<Creature> creaturesInRoom = getCreaturesInRoom(a.getLocation());
        if (!creaturesInRoom.isEmpty()){
            // handle combat for current Adventurer
            controller.setCommand(new FightCommand(a,getCreaturesInRoom(a.getLocation()),publisher));
            controller.performCommand();
        }

        sweepEliminatedCharacters();  // remove any eliminated characters post fights
    }
    private void emptyRoomAction(UserController controller){
        Adventurer a = adventurers.get(0);
        if(a.getLocation().getName().equals("011")) {
            System.out.println("Begin your adventure! Press 1 to enter the dungeon.");
        }else {
            System.out.println("""
                    There are no creatures in your room! What will you do?
                    1.Move
                    2.Search for treasure
                    3.Celebrate
                    """);
        }
        boolean invalid = true;
        int choice;
        do {
            choice = input.nextInt();
            switch (choice) {
                case 1 -> {controller.setCommand(new MoveCommand(a, rooms,publisher)); invalid=false;}
                case 2 -> {controller.setCommand(new SearchCommand(a,publisher));invalid=false;}
                case 3 -> {controller.setCommand(new CelebrateCommand(a,publisher));invalid=false;}
                default -> {System.out.println("invalid input");}
            }
        }while(invalid);

        controller.performCommand();
        //if its a runner and they chose to move, ask them if they want to move again
        if(choice == 1 && a.getID().equals("RN")){
            displayBoard();
            System.out.println("""
                    As a runner, you may skip this room and move again or remain in this room. Which would you like?
                    1. Stay
                    2. Move again
                    """);
            choice = input.nextInt();
            if(choice == 2){
                controller.performCommand();
            }
        }
    }
    private void roomWithCreaturesAction(UserController controller){
        System.out.println("""
                You have found yourself in a room with creatures! What will you do?
                1.Move (take 1 damage for every creature in room)
                2.Fight
                """);
        boolean invalid = true;
        Adventurer a = adventurers.get(0);
        int choice;
        do {
            choice = input.nextInt();
            switch (choice) {
                case 1 -> {
                    //if they move from a room with creatures, must take damage for each one in room
                    for (Creature c : getCreaturesInRoom(a.getLocation())){
                        a.decrementHitpoints();
                    }
                    controller.setCommand(new MoveCommand(a, rooms,publisher));
                    invalid=false;
                }
                case 2 -> {controller.setCommand(new FightCommand(a,getCreaturesInRoom(a.getLocation()),publisher));invalid=false;}
                default -> {System.out.println("invalid input");}
            }
        }while(invalid);

        controller.performCommand();
        //if its a runner and they chose to move, ask them if they want to move again
        if(choice == 1 && a.getID().equals("RN")){
            displayBoard();
            System.out.println("""
                    As a runner, you may skip this room and move again or remain in this room. Which would you like?
                    1. Stay
                    2. Move again
                    """);
            choice = input.nextInt();
            if(choice == 2){
                controller.performCommand();
            }
        }
    }

    // This function borrowed from Bruce's example (slightly modified)
    // this only moves the Creatures (handle combat in performTurn())
    private void performOneCreatureTurn(Creature c){
        // only move creature if no Adventurer currently in same room
        if (!c.getLocation().adventurerPresent){
            c.performMove(rooms);
            // publish event of Creature MOVE
            EventItem e = new EventItem(c.getID());
            e.characterRoom = c.getLocation().getName();
            publisher.submit(e);
        }
    }

    // after each Adventurer moves, set all their room object's adventurerPresent boolean
    private void setAdventurerPresenceInRooms(){
        for (Adventurer a : adventurers){
            a.getLocation().adventurerPresent = true;
        }
    }

    // This function borrowed from Bruce's example
    ArrayList<Creature> getCreaturesInRoom(Room room) {
        ArrayList<Creature> creaturesInRoom = new ArrayList<Creature>();
        for (Creature c: creatures) {
            if (Objects.equals(c.location.getName(), room.getName())) creaturesInRoom.add(c);
        }
        return creaturesInRoom;
    }

    // This function borrowed directly from Bruce's code, and simplified by IntelliJ
    // removes Adventurer or Creature from respective ArrayList if dead
    // it also publishes the event of Creature/Adventurer dying and being removed from game
    void sweepEliminatedCharacters() {
        // publish event of Adventurer/Creature DEATH b4 removing them from game
        for (Adventurer a : adventurers) {
            if(a.getHitpoints() <= 0){
                // publish event of Adventurer DEATH
                EventItem e = new EventItem(a.getID());
                e.defeated = true;
                publisher.submit(e);
            }
        }
        for (Creature c : creatures) {
            if(!c.alive){
                // publish event of Creature DEATH
                EventItem e = new EventItem(c.getID());
                e.defeated = true;
                publisher.submit(e);
            }
        }
        // removing from main adventurers and creatures list
        //adventurers.removeIf(a -> a.getHitpoints() <= 0);
        creatures.removeIf(c -> !c.alive);
    }

    private void createAdventurer(String name, int advType) {
        AdventurerFactory factory = new AdventurerFactory(rooms);
        adventurers.add(factory.createAdventurer(name,advType,input));
    }

    private void createCreatures() {
        CreatureFactory factory = new CreatureFactory(rooms);
        this.creatures.addAll(factory.makeAll("BL"));
        this.creatures.addAll(factory.makeAll("SK"));
        this.creatures.addAll(factory.makeAll("OR"));
    }

    //returns true if an endgame condition is met
    private boolean endOfTurnCheck(){
        boolean end = false;
        Adventurer a = adventurers.get(0);
        boolean hasAllItems = true;
        for(Map.Entry<String, Boolean> entry : a.getSeenItem().entrySet()){
            if(!entry.getValue()){
                hasAllItems = false;
                break;
            }
        }

        if(hasAllItems && a.getLocation().getName().equals("011")) {
            System.out.println("ADVENTURERS WIN! They have gotten all treasure and escaped!");
            end = true;
        }else if(!hasAllItems && a.getLocation().getName().equals("011")){
            System.out.println("ADVENTURERS LOSE! They were unable to get all the treasure before escaping!");
            end = true;
        }else if(creatures.size()==0) {
            System.out.println("ADVENTURERS WIN! They have slain all the creatures in the dungeon!");
            end = true;
        }else if(a.getHitpoints() == 0){
            System.out.println("ADVENTURERS LOSE! They have all been slain!");
            end = true;
        }
        return end;
    }


    // used to format the occupants of a room into a String for the display() function
    private String formatOccupants(Room r){
        String adv = "-";
        for (Adventurer a : adventurers){
            if (a.getLocation().getName().equals(r.getName())){
                if (adv.equals("-")) adv="";
                adv += (a.getID()+" ");
            }
        }
        String cre="";
        ArrayList<Creature> cInRoom = getCreaturesInRoom(r);
        if (cInRoom.size() == 0) cre="- ";
        else {
            for (Creature c : cInRoom) {
                cre += (c.getID() + " ");
            }
        }
        return adv + ":" + cre + "| ";
    }

    private void display(){
        System.out.println("TURN " + turnNumber);
        displayBoard();
        for(Adventurer a : adventurers){
            System.out.println(a.statsString());
        }
        System.out.println();
        //{Blinker, Orbiter, Seeker}
        int[] crCounts = {0,0,0};
        for(Creature cr : creatures){
            // only thing I changed was referencing alive, since I made it public, instead of getAlive()
            if(cr.alive) {
                switch (cr.getID().substring(0,2)) {
                    case "BL" -> crCounts[0]++;
                    case "OR" -> crCounts[1]++;
                    case "SK" -> crCounts[2]++;
                }
            }
        }
        System.out.println("Blinkers: " + crCounts[0] + " remaining");
        System.out.println("Orbiters: " + crCounts[1] + " remaining");
        System.out.println("Seekers: " + crCounts[2] + " remaining");
    }

    private void displayBoard(){
        breakString();
        //print the ground floor room
        // System.out.println(formatRoomNumber(rooms.get(0).getRoomNumber()) + formatOccupants(rooms.get(0).getRoomNumber()));
        System.out.println("| " + rooms.get(0).getName() + ": " + formatOccupants(rooms.get(0)));
        String line="| ";
        int cnt=0;
        for (Room r : rooms){
            if (r.getName().equals("011")){continue;}
            else if (r.getName().equals("100") || r.getName().equals("200") ||r.getName().equals("300") ||r.getName().equals("400")){breakString();}
            line += (r.getName() + ": " + formatOccupants(r));
            cnt+=1;
            if (cnt==3){
                System.out.println(line);
                //breakString();
                line="| ";
                cnt=0;
            }
        }
    }

    private void breakString(){
        System.out.println("=========================================");
    }


    //Used this to check the arithmetic of the array access to make sure it was outputting the right room
    private void checkAlgo(int[] roomNumber){
        if(roomNumber[0] ==  0){return;}
        int index = (9 * (roomNumber[0]-1)) + (3 * roomNumber[1]) + roomNumber[2] + 1;
        System.out.println(Arrays.toString(roomNumber).equals(Arrays.toString(rooms.get(index).getRoomNumber())));
    }


}
