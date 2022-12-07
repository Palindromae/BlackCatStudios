package com.mygdx.game.CoreData.Items;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;

// Dictionary for all recipes in the game
public class RecipeDict {
    public Map<Items, Recipe> RecipeMap = new HashMap<>();
    public ArrayList<Step> steps = new ArrayList<>();

    public RecipeDict() {
        Collections.addAll(steps, new TimeStep(), new InteractionStep(), new TimeStep());
        RecipeMap.put(Items.RawPatty, new Recipe(Items.CookedPatty, steps));
    }
}
