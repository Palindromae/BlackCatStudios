package com.mygdx.game.CoreData.Items;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.*;
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
    public int i = 0;
    public float workstationSpeed;
    public BTexture btexture;
    public GameObject obj;
    public Integer width;
    int HowCloseDoesChefNeedToBe =45;

    public void init(){
        btexture = new BTexture("Pictures/ProgressBar.png", null, null);
        obj = new GameObject(new Rectangle(),btexture, 1, 10);
        obj.transform.position=new Vector3(gameObject.transform.position.x,gameObject.transform.position.y,gameObject.transform.position.z+1);
        width = gameObject.getTextureWidth();
    }

    //Give workstation an item
    public abstract  boolean GiveItem(ItemAbs Item);
    public abstract ItemAbs GetItem();

    public void Reset(){
        Item = null;
        returnItem = null;
        currentRecipe = null;
    }

    public void deleteItem(){
        Item = null;

    }

}
