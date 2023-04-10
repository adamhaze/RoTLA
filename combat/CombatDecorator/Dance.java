package combat.CombatDecorator;

import combat.CombatBehavior;

import creatures.Creature;

public class Dance extends CombatDecorator {

    public Dance(CombatBehavior comb){
        this.combat = comb;
        //this.celebration += "Dance! ";
    }

    public String celebrate(){
        return combat.celebrate() + "Dance! ";
    }
    /*
    THIS IS AN EXAMPLE OF DECORATOR
    What is happening here is that when the fight method is called for a combat behavior,
    it gets wrapped in a Combat Decorator like Dance. This decorator calls the fight method from the
    combat behavior, but it also prints its celebration string if the fight was a victory. This
    means that we didn't need to change the combat code to allow for celebrations, we just needed
    to wrap them in the decorators that would add the functionality.

    To see the wrapping of the combat behavior by a decorator, see any of the adventurer concrete classes
     */

//    public boolean fight(Creature c, int combatBonus){
//        //prints the celebration if the fight was won/tied
//        //if(result) System.out.print(celebrate());
//        return combat.fight(c, combatBonus);
//    }






}
