package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;

public abstract class Step {

    protected float MaxProgress;
    public abstract boolean timeStep(ItemAbs item, float dt);
}
