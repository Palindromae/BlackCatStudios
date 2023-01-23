package com.mygdx.game.CoreData.Items;
import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.BlackScripts.ItemFactory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The WSHob is an object that the player can both give and take items from. It also
 * allows the user to cook certain items to produce a new item.
 */
public class WSHob extends WorkStation{

    boolean Interacted = false;
    boolean ready;
    public static ArrayList<Items> ItemWhitelist = new ArrayList<>(
            Arrays.asList(Items.RawPatty, Items.Buns));

    @Override
    public boolean GiveItem(ItemAbs Item){
        if(this.Item == null){
            this.Item = Item;
            checkItem();
            return true;
        }
        return false;
    }

    @Override
    public boolean TestGetItem() {
        return true;
    }

    @Override
    public boolean TestGiveItem() {
        return true;
    }

    @Override
    public ItemAbs GetItem(){

        if(Item!=null && canTakeItem()) {

            returnItem = Item;
            deleteItem();
            currentRecipe = null;
            return returnItem;
        }
        interact();
        return null;

    }

    // Checks if the given item is in the whitelist, if yes the item's recipe is stored in currentRecipe
    public void checkItem(){
        if(ItemWhitelist.contains(Item.name)){
            currentRecipe = Recipes.RecipeMap.get(Item.name);
        }
    }

    // Checks if currentRecipe is null if not interacted is set to true and returns true, else false is returned
    public boolean interact(){
        if(currentRecipe==null)
            return false;
        return Interacted = true;
    }


    public boolean isItemReady(float dt){
        return currentRecipe.RecipeSteps.get(i).timeStep(Item, dt, Interacted);
    }

    public boolean canTakeItem(){
        return currentRecipe == null || Item.name == currentRecipe.endItem;
    }
    /**
     * Calls current step in recipe and stores returned boolean in ready, if ready is true and the item's
     * cookingProgress is equal to 0, the counter will increment to select the next step, if it has
     * reached the end of the list the new item will be produced.
     * @param dt Time constant
     */

    public void Cook(float dt){
        ready = isItemReady(dt);
        if(ready & Item.cookingProgress==0){
            i++;
            System.out.println("Changed step: "+ currentRecipe.RecipeSteps.get(Math.min(i,currentRecipe.RecipeSteps.size()-1)));

            if(i==currentRecipe.RecipeSteps.size()){
                Item = ItemFactory.factory.produceItem(currentRecipe.endItem);
                i = 0;
                checkItem();
            }
            return;
        }
        /* Burn can be implemented here, cookingProgress will only be set to zero if player has interacted during
            with the hob during an interaction step or at the end of a TimeStep.
         */
        //if(ready){
        //  burn()
        // }
    }

    // public void burn(){}
    @Override
    public void FixedUpdate(float dt){
        if(currentRecipe != null)
            Cook(dt);

        Interacted = false;
    }
}
