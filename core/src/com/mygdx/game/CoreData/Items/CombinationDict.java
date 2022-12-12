package com.mygdx.game.CoreData.Items;

import java.util.HashMap;
import java.util.Map;

public class CombinationDict {
    public Map<String, Items> CombinationMap = new HashMap<>();

    public CombinationDict(){

        //Salads
        CombinationMap.put(Items.CutLettuce.name()+Items.CutTomato.name(), Items.LetTomSalad);
        CombinationMap.put(Items.CutLettuce.name()+Items.CutOnion.name(), Items.LetOnSalad);
        CombinationMap.put(Items.CutTomato.name()+Items.CutOnion.name(), Items.TomOnSalad);
        CombinationMap.put(Items.CutLettuce.name()+Items.TomOnSalad.name(), Items.FullSalad);
        CombinationMap.put(Items.CutTomato.name()+Items.LetOnSalad.name(), Items.FullSalad);
        CombinationMap.put(Items.CutOnion.name()+Items.LetTomSalad.name(), Items.FullSalad);

        //Burgers
        CombinationMap.put(Items.CookedPatty.name()+Items.ToastedBuns.name(), Items.Burger);
        CombinationMap.put(Items.Cheese.name()+Items.Burger.name(), Items.CheeseBurger);
    }

}
