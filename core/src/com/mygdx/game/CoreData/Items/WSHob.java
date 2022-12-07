package com.mygdx.game.CoreData.Items;
import com.mygdx.game.BlackCore.ItemAbs;

import java.util.ArrayList;
import java.util.Arrays;

// Hob Class
public class WSHob extends WorkStation{

    boolean Interacted = false;
    static float dt = 1; //placeholder value
    boolean ready;
    public static ArrayList<Items> ItemWhitelist = new ArrayList<>(
            Arrays.asList(Items.RawPatty, Items.Buns));

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

    public boolean interact(){
        if(currentRecipe!=null){
            Interacted = true;
            return true;
            }
        return false;
    }

    public void Cook(){
        ready = currentRecipe.RecipeSteps.get(i).timeStep(Item, dt, Interacted);
        if(ready & Item.cookingProgress==0){
            i++;
            if(i==currentRecipe.RecipeSteps.size()){
                Item = factory.produceItem(currentRecipe.endItem);
                checkItem();
            }
        }
        //if(ready){    if an InteractionStep is met and cookingprogress reaches max burn food
        //  burn()
        // }
    }

    // public void burn(){}

    public void update(){
        if(currentRecipe != null) {
            Cook();
        }
        Interacted = false;
    }
}
