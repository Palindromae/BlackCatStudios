package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;

import static java.lang.Math.min;

/**
 * Step for cooking/cutting/baking etc..
 */

public class TimeStep extends Step{
    /**
     * Increments item's cookingProgress then checks if it is equal to MaxProgress, if yes cookingProgress is reset
     * and returns true else returns false
     * @param item
     * @param dt Time constant
     * @param Interacted
     * @return boolean
     */
    @Override
    public boolean timeStep(ItemAbs item, float dt, boolean Interacted) {

        item.cookingProgress = min(item.cookingProgress + dt,item.MaxProgress);
        if(item.cookingProgress==item.MaxProgress){
            return true;
        }

        return false;
    }
}
