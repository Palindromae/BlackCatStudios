package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;

import static java.lang.Math.min;

// Step for cooking/cutting/baking etc..
public class TimeStep extends Step{
    @Override
    public boolean timeStep(ItemAbs item, float dt, boolean Interacted) {

        item.cookingProgress = min(item.cookingProgress + dt,item.MaxProgress);
        if(item.cookingProgress==item.MaxProgress){
            item.cookingProgress=0;
            return true;
        }

        return false;
    }
}
