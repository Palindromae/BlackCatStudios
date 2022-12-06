package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;

public class WorkStation {
    public ItemAbs Item = null;

    //Give workstation an item
    public void giveItem(ItemAbs Item){
        this.Item = Item;
    }

    //Take item from workstation
    public ItemAbs takeItem(){
        ItemAbs returnItem = Item;
        Item = null;
        return returnItem;
    }
}
