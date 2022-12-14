package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;

import static java.lang.Math.min;

/**
 * A step for timed interactions when cooking an item
 */

public class InteractionStep extends Step {

    /**
     * Checks if user has interacted with workstation, if true cookingProgress is reset and returns true
     * else cookingProgress is increased and returns false.
     * @param item
     * @param dt time constant
     * @param Interacted
     * @return boolean
     */
    @Override
    public boolean timeStep(ItemAbs item, float dt, boolean Interacted) {
        if(Interacted){
            item.cookingProgress = 0;
            return true;
        }
        item.cookingProgress = min(item.cookingProgress + dt,item.MaxProgress);
        // if(item.cookingProgress == MaxProgress){
        //    return true;
        // }
        return false;
    }
}
