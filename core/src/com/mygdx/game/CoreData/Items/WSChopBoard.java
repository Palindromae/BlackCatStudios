package com.mygdx.game.CoreData.Items;
import com.mygdx.game.BlackCore.ItemAbs;

import java.util.ArrayList;
import java.util.Arrays;

// Chopping board class
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
        if (currentRecipe == null)
            return false;
        Interacted = true;
        return true;
    }

    public void Cut(float dt){
        ready = currentRecipe.RecipeSteps.get(i).timeStep(Item, dt, Interacted);
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
