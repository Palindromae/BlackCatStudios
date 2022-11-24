package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;

import static java.lang.Math.min;

//
public class TimeStep extends  Step{
    @Override
    public boolean timeStep(ItemAbs item, float dt) {

        //if(MaxProgressed and some time limit reach){}

        item.cookingProgress = min(item.cookingProgress + dt,MaxProgress);


    return item.cookingProgress == MaxProgress;
    }
}
