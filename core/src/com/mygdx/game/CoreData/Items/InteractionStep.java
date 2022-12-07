package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;

import static java.lang.Math.min;

// Step for when an interaction in a recipe is necessary
public class InteractionStep extends Step {

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
