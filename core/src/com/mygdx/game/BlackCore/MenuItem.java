package com.mygdx.game.BlackCore;

import com.mygdx.game.CoreData.Items.Items;

import java.util.List;

import static java.lang.Math.min;

public class MenuItem {
    public Items itemType;
    public int count;
    public List<Items> Variations;

    public MenuItem(Items item, int count, List<Items> _Variations){
        itemType = item;
        this.count = count;
        Variations = _Variations;
    }

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
