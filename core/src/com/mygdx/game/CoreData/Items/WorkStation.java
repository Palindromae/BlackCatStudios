package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.InteractInterface;
import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.BlackScripts.ItemFactory;

/**
 * Abstract class for all Workstations.
 */
public abstract class WorkStation extends BlackScripts implements InteractInterface {

    public ItemAbs Item = null;
    public ItemAbs returnItem;
    public static RecipeDict Recipes = new RecipeDict();
    public static CombinationDict combinations = new CombinationDict();
    public Recipe currentRecipe = null;
    public float workstationSpeed;

    int HowCloseDoesChefNeedToBe =45;

    //Give workstation an item
    public abstract  boolean GiveItem(ItemAbs Item);
    public abstract ItemAbs GetItem();


    public void deleteItem(){
        Item = null;

    }

}
