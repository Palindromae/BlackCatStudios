package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;

/**
 * Abstract class for all steps
 */
public abstract class Step {

    public abstract boolean timeStep(ItemAbs item, float dt, boolean Interacted);
}
