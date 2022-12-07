package com.mygdx.game.CoreData.Items;

import java.util.ArrayList;


public class Recipe {
    public Items endItem;
    public ArrayList<Step> RecipeSteps = new ArrayList<>();

    public Recipe(Items endItem, ArrayList<Step> stepList){
        this.endItem = endItem;
        RecipeSteps.addAll(stepList);
    }

}
