# OOAD_Project4
## Evan Easton and Adam Hayes

Project 4 codebase for CSCI 5448. Evan's machine uses Java 8 with Java SDK 18. Adam's machine uses Java 18 with Java SDK 18.


## UML Diagram
We simplified the Factory patterns to have 2 simple concrete factory creators: AdventurerFactory and CreatureFactory, and also added input parameters to both Factory methods, along with a makeAll() function to the Creature factory, since it makes more than 1 of each type of Creature. We also added references to the Room class in both Creature and Adventurer factory classes.
We edited the Command interface, renamed UserCommand, to reference the Adventurer that is using it. We also added a controller to our command pattern (UserController.java).
We added input parameters to the Movement interface.

## Singleton Pattern
files: 
- `messaging/Logger.java` (eager instantiation)
- `messaging/Tracker.java` (lazy instantiation)

## Factory Pattern
files:
- `factory/CreatureFactory.java` (Concrete Creator)
- `factory/AdventurerFactory.java` (Concrete Creator)

## Assumptions
- Adventurer only searches for search if they're the only one in the room
- An ecounter is a "success" for a Sneaker (same as for the other 3) if they don't fight, but nothing else happens
- Adventurer still celebrates on a tie b/c they didn't have to fight a dangerous Creature
- The Logger doesn't technically unsubscribe and resubscribe to the Subject each turn, but rather "resets" itself by clearning it's own memory each turn to begin logging events for the given turn
- We implemented Subject / Observer using Java's Flow API, where the publisher is a concrete implementation of Flow.Publisher called SubmissionPublisher: this helped keep the publish / subscribe logic very simple for our project.
- the events that our "Subject" publishes are created and sent as instances of class EventItem, which just contains a bunch of string and boolean attributes that get instatiated as null/false, and only 1 attribute will be directly assigned to represent the type of event that's happened. Tracker and Logger then handle these EventItem's themselves.
- Decorated Celebrations are set for each Adventurer at instantiation
- The way our CombatBehavior was designed was not super conducive to Decorator, and we decided to not overhaul our CombatBehavior in this iteration of the project. Therefore, our Decorator is simply printing out the different celebrations directly to the console instead of returning them recursively to the CombatBehavior instance. Also, this prevents us from being able to record an event for the celebration string. Going forward, we will likely change our CombatBehavior to an abstract class and have it produce an EventItem, as well as maintain the celebration string following recursive returns from the Decorator. We felt that overhauling our CombatBehavior was contradictory to the spirit of the excercise for Decorator, but would have approached it much differently if we had more time.
- We will not count the Trap treasures as a treasure used for the end game condition. We thought that any time a Trap was found by the adventurer, they should take some damage. We also thought that it shouldn't be an item that an adventurer keeps with them, so for the end condition, Trap will not be checked for.
