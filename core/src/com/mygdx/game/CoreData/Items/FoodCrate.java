package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.InteractInterface;
import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.BlackScripts.ItemFactory;

public class FoodCrate extends BlackScripts implements InteractInterface {
    public Items ingredient;


    public FoodCrate(Items ingredient){
        this.ingredient = ingredient;
    }




    @Override
    public boolean GiveItem(ItemAbs item) {
        return false;
    }
    @Override
    public ItemAbs GetItem(){
        return ItemFactory.factory.produceItem(ingredient);
    }
    @Override
    public boolean TestGetItem() {
        return true;
    }

    @Override
    public boolean TestGiveItem() {
        return false;
    }
}
