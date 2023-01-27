package com.mygdx.game.CoreData.Items;
import com.mygdx.game.BlackCore.ItemAbs;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The WSChoppingBoard is an object that the player can both give and take items from. The player can also cut
 * certain given items to create a new item.
 */

public class WSChopBoard extends WorkStation{

    boolean Interacted = false;
    boolean ready;
    public static ArrayList<Items> ItemWhitelist = new ArrayList<>(
            Arrays.asList(Items.Lettuce, Items.Tomato, Items.Onion, Items.Mince));

    @Override
    public boolean giveItem(ItemAbs Item){
        if(this.Item == null){
            this.Item = Item;
            checkItem();
            return true;
        }
        return false;
    }

    @Override
    public ItemAbs takeItem(){
        returnItem = Item;
        deleteItem();
        currentRecipe = null;
        return returnItem;
    }

    // Checks if the given item is in the whitelist, if yes the item's recipe is stored in currentRecipe
    public void checkItem(){
        if(ItemWhitelist.contains(Item.name)){
            currentRecipe = Recipes.RecipeMap.get(Item.name);
        }
    }

    // Checks if currentRecipe is null if not interacted is set to true and returns true, else false is returned
    public boolean interact() {
        if (currentRecipe == null)
            return false;
        return Interacted = true;
    }

    // Calls the current step and stores returned bool variable in ready, if true a new item is produced
    public void Cut(float dt){
        ready = currentRecipe.RecipeSteps.get(Item.currentStep).timeStep(Item, dt, Interacted);
        if(ready){
            Item = factory.produceItem(currentRecipe.endItem);
        }
    }

    @Override
    public void FixedUpdate(float dt){
        if(Interacted & currentRecipe!=null){
            Cut(dt);
        }
        Interacted = false;
    }
}
