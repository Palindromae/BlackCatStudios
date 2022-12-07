package com.mygdx.game.CoreData.Items;
import com.mygdx.game.BlackCore.ItemAbs;

import java.util.ArrayList;
import java.util.Arrays;

// Chopping board class
public class WSChopBoard extends WorkStation{

    boolean Interacted = false;
    static float dt = 1;
    boolean ready;
    public static ArrayList<Items> ItemWhitelist = new ArrayList<>(
            Arrays.asList(Items.Lettuce));

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
        ItemAbs returnItem = Item;
        Item = null;
        currentRecipe = null;
        return returnItem;
    }

    public void checkItem(){
        if(ItemWhitelist.contains(Item.name)){
            currentRecipe = Recipes.RecipeMap.get(Item.name);
        }
    }

    public boolean interact() {
        if (currentRecipe != null) {
            Interacted = true;
            return true;
        }
        return false;
    }

    public void Cut(){
        ready = currentRecipe.RecipeSteps.get(i).timeStep(Item, dt, Interacted);
        if(ready){
            Item = factory.produceItem(currentRecipe.endItem);
        }
    }

    public void update(){
        if(Interacted & currentRecipe!=null){
            Cut();
        }
        Interacted = false;
    }
}
