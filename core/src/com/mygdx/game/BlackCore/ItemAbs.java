package com.mygdx.game.BlackCore;

import com.mygdx.game.CoreData.Items.Items;

public abstract class ItemAbs {
    public Items name;
    public float cookingProgress = 0; //not 0 to 100, is an abstract number
    public float MaxProgress = 5;
    public int currentStep = 0;



}

