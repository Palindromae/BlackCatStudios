package com.mygdx.game.CoreData.Items;
import com.mygdx.game.BlackCore.ItemAbs;

// Counter class
public class WSCounter extends WorkStation{
    Items temp;
    int value;
    @Override
    public boolean giveItem(ItemAbs Item){
        if(this.Item == null){
            this.Item = Item;
            return true;
        }
        return combine(Item);
    }

    @Override
    public ItemAbs takeItem(){
        ItemAbs returnItem = Item;
        Item = null;
        return returnItem;
    }

    public boolean combine(ItemAbs NewItem){
        value = Item.name.compareTo(NewItem.name);
        if(value == 0)
            return false;
        if(value > 0){
            temp = combinations.CombinationMap.get(NewItem.name.name()+Item.name.name());
        }
        else
            temp = combinations.CombinationMap.get(Item.name.name()+NewItem.name.name());
        if(temp == null)
            return false;
        Item = factory.produceItem(temp);
        return true;

    }

    @Override
    public void FixedUpdate(float dt){

    }

}
