package combat.CombatDecorator;

import combat.CombatBehavior;

import creatures.Creature;

public class Shout extends CombatDecorator {

    public Shout(CombatBehavior comb){
        this.combat = comb;
        //this.celebration += "Shout! ";
    }

    public String celebrate() {
        return combat.celebrate() + "Shout! ";
    }

//    public boolean fight(Creature c, int combatBonus){
//        //prints the celebration if the fight was won/tied
//        //if(result) System.out.print(celebrate());
//        return combat.fight(c, combatBonus);
//    }






}