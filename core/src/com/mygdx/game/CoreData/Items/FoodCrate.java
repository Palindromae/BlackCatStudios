package com.mygdx.game.CoreData.Items;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackScripts.ItemFactory;

public class FoodCrate extends BlackScripts implements InteractInterface{
    public Items ingredient;

    private GameObject ItemImage;


    public FoodCrate(Items ingredient){

        this.ingredient = ingredient;


    }


    public void init(){
        BTexture tex = new BTexture(ItemAbs.getImagePath(ingredient),null,null);

        ItemImage = new GameObject(new Rectangle(), tex,25,25);

        ItemImage.transform.position = new Vector3(gameObject.transform.position);
        ItemImage.transform.position.x += 10;
        ItemImage.transform.position.z += 12.5;
        ItemImage.transform.position.y += 1;
    }



    @Override
    public boolean GiveItem(ItemAbs item) {
        return false;
    }
    @Override
    public ItemAbs GetItem(){
        return ItemFactory.factory.produceItem(ingredient);
    }
    @Override
    public boolean TestGetItem() {
        return true;
    }

    @Override
    public boolean TestGiveItem() {
        return false;
    }
}
