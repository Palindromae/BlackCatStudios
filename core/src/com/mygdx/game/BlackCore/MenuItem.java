package com.mygdx.game.BlackCore;

import com.mygdx.game.CoreData.Items.Items;

import java.util.List;

import static java.lang.Math.min;

public class MenuItem {
    public Items itemType;
    public int count;
    public List<Items> Variations;

    /**
     * Creates a menu item
     * @param item item to base the type on
     * @param count how many is in stock by default/Max
     * @param _Variations What items can be ordered
     */
    public MenuItem(Items item, int count, List<Items> _Variations){
        itemType = item;
        this.count = count;
        Variations = _Variations;
    }

    /**
     * Removes values upto stock floor
     * @param stockFloor
     * @return
     */
    public boolean removeStock(int stockFloor){
        count--;
        if(count<=stockFloor)
        {
            count = stockFloor;
            return false;
        }

        return true;
    }
}
