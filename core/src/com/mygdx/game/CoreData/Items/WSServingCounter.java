package com.mygdx.game.CoreData.Items;

import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.BlackScripts.CustomerManager;

public class WSServingCounter extends WorkStation{
    boolean answer;


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
    public void FixedUpdate(float dt){

    }
}
