package com.mygdx.game.CoreData.Items;


import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.InteractInterface;
import com.mygdx.game.BlackCore.ItemAbs;

public class TrashCan extends BlackScripts implements InteractInterface {
    public Object giveItem(){
        return null;
    }

    @Override
    public boolean TestGetItem() {
        return false;
    }

    @Override
    public boolean TestGiveItem() {
        return false;
    }

    @Override
    public ItemAbs GetItem() {
        return null;
    }

    @Override
    public boolean GiveItem(ItemAbs give) {

        return true;
    }
}
