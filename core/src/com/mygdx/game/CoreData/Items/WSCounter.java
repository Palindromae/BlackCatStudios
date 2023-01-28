package com.mygdx.game.CoreData.Items;
import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.BlackScripts.ItemFactory;

/**
 * The WSCounter is an object that the player can both give and take items from. It also
 * allows the user to combine certain items to create meals.
 */
public class WSCounter extends WorkStation{
    Items temp;
    int value;
    @Override
    public boolean GiveItem(ItemAbs Item){
        if(this.Item == null){
            changeItem(Item);
            return true;
        }
        return combine(Item);
    }

    public WSCounter(){
        ItemSize = 20;
        offset = 7.5f;
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

    /**
     * NewItem's name is compared to Item's, if they are equal false is returned, else
     * they will be ordered and turned into a string to get the meal from the combination dictionary. See
     * {@link com.mygdx.game.CoreData.Items.CombinationDict} for possible combinations. False is returned if
     * combination does not exist, else the new item will be created and return true
     * @param NewItem
     * @return boolean
     */
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
        changeItem(ItemFactory.factory.produceItem(temp));
        return true;
    }

    @Override
    public void FixedUpdate(float dt){

    }

    @Override
    public void Reset(){
        super.Reset();

        temp = null;
        value = 0;
    }

}
