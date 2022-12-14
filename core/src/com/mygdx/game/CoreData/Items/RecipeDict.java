package com.mygdx.game.CoreData.Items;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Dictionary for all recipes in the game
 */

public class RecipeDict {
    public Map<Items, Recipe> RecipeMap = new HashMap<>();

    public RecipeDict() {
        ArrayList<Step> steps;

        //Chopping Board Recipes

        //Lettuce -> CutLettuce
        steps = new ArrayList<>(Arrays.asList(new TimeStep()));
        RecipeMap.put(Items.Lettuce, new Recipe(Items.CutLettuce, steps));

        //Tomato -> CutTomato
        RecipeMap.put(Items.Tomato, new Recipe(Items.CutTomato, steps));

        //Onion -> CutOnion
        RecipeMap.put(Items.Onion, new Recipe(Items.CutOnion, steps));

        //Mince -> RawPatty
        RecipeMap.put(Items.Mince, new Recipe(Items.RawPatty, steps));

        //Hob Recipes

        //RawPatty -> CookedPatty
        steps = new ArrayList<>(Arrays.asList(new TimeStep(), new InteractionStep(), new TimeStep()));
        RecipeMap.put(Items.RawPatty, new Recipe(Items.CookedPatty, steps));

        //Buns -> ToastedBuns;
        RecipeMap.put(Items.Buns, new Recipe(Items.ToastedBuns, steps));

    }
}
