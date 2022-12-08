package com.mygdx.game.CoreData.Items;
import com.mygdx.game.BlackCore.ItemAbs;

// Counter class
public class WSCounter extends WorkStation{

    @Override
    public boolean giveItem(ItemAbs Item){
        if(this.Item == null){
            this.Item = Item;
            return true;
        }
        return false;
    }

    @Override
    public ItemAbs takeItem(){
        ItemAbs returnItem = Item;
        Item = null;
        return returnItem;
    }

    @Override
    public void FixedUpdate(float dt){

    }

}
