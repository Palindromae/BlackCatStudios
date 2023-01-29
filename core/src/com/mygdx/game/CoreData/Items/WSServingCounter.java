package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.BlackScripts.CustomerManager;

public class WSServingCounter extends WorkStation{
    boolean answer;


    @Override
    public boolean GiveItem(ItemAbs Item){
        if(this.Item == null){
            changeItem(Item);
            interact();
            return true;
        }
        interact();

        return false;
    }

    @Override
    public boolean TestGetItem() {
        return true;
    }

    @Override
    public boolean TestGiveItem() {
        return true;
    }

    @Override
    public ItemAbs GetItem(){
        returnItem = Item;
        deleteItem();
        return returnItem;
    }

    public boolean interact(){
        if(Item == null)
            return false;
        answer = CustomerManager.customermanager.IsFoodInOrder(Item);
        if(answer)
            deleteItem();
        return answer;
    }


    @Override
    public void Reset(){
        super.Reset();

        answer = false;
    }

    @Override
    public void FixedUpdate(float dt){

    }
}
