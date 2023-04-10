package combat.CombatDecorator;

import combat.CombatBehavior;
import creatures.Creature;

public abstract class CombatDecorator extends CombatBehavior {

    CombatBehavior combat;
    public abstract String celebrate();

    // fight function actually not changing with concrete decorators b/c
    // of way we set it up to return boolean, didn't make sense to modify it for each
    // concrete decorator
    public boolean fight(Creature c, int combatBonus){
        //prints the celebration if the fight was won/tied
        //if(result) System.out.print(celebrate());
        return combat.fight(c, combatBonus);
    }


}
