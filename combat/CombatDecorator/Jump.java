package combat.CombatDecorator;

import combat.CombatBehavior;

import creatures.Creature;

public class Jump extends CombatDecorator {

    public Jump(CombatBehavior comb){
        this.combat = comb;
        //this.celebration += "Jump! ";
    }

    public String celebrate() {
        return combat.celebrate() + "Jump! ";
    }

//    public boolean fight(Creature c, int combatBonus){
//        //prints the celebration if the fight was won/tied
//        //if(result) System.out.print(celebrate());
//        return combat.fight(c, combatBonus);
//    }






}