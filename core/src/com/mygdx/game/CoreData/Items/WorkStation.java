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

    int ItemSize = 30;
    float offset = 12.5f;

    GameObject HeldItem;

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
        changeItem(null);
        returnItem = null;
        currentRecipe = null;
    }

    public void deleteItem(){
        changeItem(null);


    }
    public void changeItem(ItemAbs item){
        Item = item;
        UpdateItem();
    }

    public void UpdateItem(){
        if(Item == null) {
            if(HeldItem == null)
                return;
            HeldItem.Destroy();
            HeldItem = null;
            return;
        }
        if(HeldItem == null){
            BTexture B = new BTexture(Item.getImagePath(),null,null);
            HeldItem = new GameObject(new Rectangle(),B, ItemSize, ItemSize);
            HeldItem.transform.position = new Vector3(gameObject.transform.position).add(new Vector3(offset,1,offset));
        }
        else
            HeldItem.UpdateTexture(Item.getImagePath());

    }

}
