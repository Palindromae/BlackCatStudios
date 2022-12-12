package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.BlackScripts.ItemFactory;

public abstract class WorkStation extends BlackScripts {

    public ItemAbs Item = null;
    public ItemFactory factory = ItemFactory.factory;
    public static RecipeDict Recipes;
    public static CombinationDict combinations;
    public Recipe currentRecipe = null;
    public int i = 0;
    public float workstationSpeed;

    //Give workstation an item
    public abstract boolean giveItem(ItemAbs Item);

    //Take item from workstation
    public abstract ItemAbs takeItem();


}
