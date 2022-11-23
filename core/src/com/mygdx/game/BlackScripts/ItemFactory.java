package com.mygdx.game.BlackScripts;

import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.CoreData.Items.ItemLettuce;
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


        addNewItem(Items.Lettuce, new Supplier<ItemAbs>() {
            @Override
            public ItemAbs get() {
                return new ItemLettuce();
            }
        });


    }

    public ItemFactory(){
        if (factory != null)
            return;
        factory = this;
    }

}
