package com.mygdx.game.BlackScripts;

import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.CoreData.Items.ItemLettuce;
import com.mygdx.game.CoreData.Items.ItemCutLettuce;
import com.mygdx.game.CoreData.Items.ItemTomato;
import com.mygdx.game.CoreData.Items.ItemCutTomato;
import com.mygdx.game.CoreData.Items.ItemOnion;
import com.mygdx.game.CoreData.Items.ItemCutOnion;
import com.mygdx.game.CoreData.Items.ItemMince;
import com.mygdx.game.CoreData.Items.ItemRawPatty;
import com.mygdx.game.CoreData.Items.ItemCookedPatty;
import com.mygdx.game.CoreData.Items.ItemBuns;
import com.mygdx.game.CoreData.Items.ItemToastedBuns;
import com.mygdx.game.CoreData.Items.ItemCheese;
import com.mygdx.game.CoreData.Items.Items;

import java.util.HashMap;
import java.util.function.Supplier;

public class ItemFactory extends BlackScripts {


    public static ItemFactory factory;
    HashMap<Items, Supplier<ItemAbs>> ItemDico = new HashMap<>();

    public ItemAbs produceItem(Items item){
        return ItemDico.get(item).get();
    }

    public void addNewItem(Items name, Supplier<ItemAbs> constructor){
        if(ItemDico.containsKey(name))
        {
            System.out.println(name + " has already been added to the dictionary, make sure this isn't a mistake");
        }
        ItemDico.put(name,constructor);
    }

    public void implementItems(){


        addNewItem(Items.Lettuce, () -> new ItemLettuce());
        addNewItem(Items.CutLettuce, () -> new ItemCutLettuce());
        addNewItem(Items.Tomato, () -> new ItemTomato());
        addNewItem(Items.CutTomato, () -> new ItemCutTomato());
        addNewItem(Items.Onion, () -> new ItemOnion());
        addNewItem(Items.CutOnion, () -> new ItemCutOnion());
        addNewItem(Items.Mince, () -> new ItemMince());
        addNewItem(Items.RawPatty, () -> new ItemRawPatty());
        addNewItem(Items.CookedPatty, () -> new ItemCookedPatty());
        addNewItem(Items.Buns, () -> new ItemBuns());
        addNewItem(Items.ToastedBuns, () -> new ItemToastedBuns());
        addNewItem(Items.Cheese, () -> new ItemCheese());


    }

    public ItemFactory(){
        if (factory != null)
            return;
        factory = this;
    }

}
