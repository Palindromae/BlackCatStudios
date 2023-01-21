package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.BlackScripts.ItemFactory;

public class FoodCrate {
    public Items ingredient;
    public ItemFactory factory = ItemFactory.factory;

    public FoodCrate(Items ingredient){
        this.ingredient = ingredient;
    }

    public ItemAbs getFood(){
        return factory.produceItem(ingredient);
    }
}
