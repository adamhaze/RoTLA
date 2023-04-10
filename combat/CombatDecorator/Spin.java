package combat.CombatDecorator;

import combat.CombatBehavior;

import creatures.Creature;

public class Spin extends CombatDecorator {

    public Spin(CombatBehavior comb){
        this.combat = comb;
        //this.celebration += "Spin! ";
    }

    public String celebrate() {
        return combat.celebrate() + "Spin! ";
    }

//    public boolean fight(Creature c, int combatBonus){
//        boolean result = combat.fight(c, combatBonus);
//        //prints the celebration if the fight was won/tied
//        //if(result) System.out.print(celebrate());
//        return result;
//    }






}