package com.mygdx.game.CoreData.Items;

import java.util.ArrayList;

/**
 * Class to hold the steps in a recipe and the item it creates.
 */
public class Recipe {
    public Items endItem;
    public ArrayList<Step> RecipeSteps = new ArrayList<>();

    public Recipe(Items endItem, ArrayList<Step> stepList){
        this.endItem = endItem;
        RecipeSteps.addAll(stepList);
    }

}
