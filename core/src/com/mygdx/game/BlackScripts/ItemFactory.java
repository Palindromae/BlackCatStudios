package com.mygdx.game.BlackScripts;

import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.CoreData.Items.Classes.*;
import com.mygdx.game.CoreData.Items.Items;

import java.util.HashMap;
import java.util.function.Supplier;

public class ItemFactory extends BlackScripts {


    public static ItemFactory factory;
    HashMap<Items, Supplier<ItemAbs>> ItemDico = new HashMap<>();

    public ItemAbs produceItem(Items item){
        return ItemDico.get(item).get();
    }

    /**
     * Add a new item given its constructor to dico
     * @param name
     * @param constructor
     */
    public void addNewItem(Items name, Supplier<ItemAbs> constructor){
        if(ItemDico.containsKey(name))
        {
            System.out.println(name + " has already been added to the dictionary, make sure this isn't a mistake");
        }
        ItemDico.put(name,constructor);
    }

    public void implementItems(){


        addNewItem(Items.Lettuce, ItemLettuce::new);
        addNewItem(Items.CutLettuce, ItemCutLettuce::new);
        addNewItem(Items.Tomato, ItemTomato::new);
        addNewItem(Items.CutTomato, ItemCutTomato::new);
        addNewItem(Items.Onion, ItemOnion::new);
        addNewItem(Items.CutOnion, ItemCutOnion::new);
        addNewItem(Items.Mince, ItemMince::new);
        addNewItem(Items.RawPatty, ItemRawPatty::new);
        addNewItem(Items.CookedPatty, ItemCookedPatty::new);
        addNewItem(Items.Buns, ItemBuns::new);
        addNewItem(Items.ToastedBuns, ItemToastedBuns::new);
        addNewItem(Items.Cheese, ItemCheese::new);
        addNewItem(Items.LettuceOnionSalad, ItemLetOnSalad::new);
        addNewItem(Items.LettuceTomatoSalad, ItemLetTomSalad::new);
        addNewItem(Items.TomatoOnionSalad, ItemTomOnSalad::new);
        addNewItem(Items.TomatoOnionLettuceSalad, ItemFullSalad::new);
        addNewItem(Items.Burger, ItemBurger::new);
        addNewItem(Items.CheeseBurger, ItemCheeseBurger::new);


    }

    public ItemFactory(){
        if (factory != null)
            return;
        factory = this;
    }

}
