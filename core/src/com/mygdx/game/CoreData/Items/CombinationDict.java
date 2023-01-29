package com.mygdx.game.CoreData.Items;

import java.util.HashMap;
import java.util.Map;

public class CombinationDict {
    public Map<String, Items> CombinationMap = new HashMap<>();

    public CombinationDict(){

        //Salads
        CombinationMap.put(Items.CutLettuce.name()+Items.CutTomato.name(), Items.LettuceTomatoSalad);
        CombinationMap.put(Items.CutLettuce.name()+Items.CutOnion.name(), Items.LettuceOnionSalad);
        CombinationMap.put(Items.CutTomato.name()+Items.CutOnion.name(), Items.TomatoOnionSalad);
        CombinationMap.put(Items.CutLettuce.name()+Items.TomatoOnionSalad.name(), Items.TomatoOnionLettuceSalad);
        CombinationMap.put(Items.CutTomato.name()+Items.LettuceOnionSalad.name(), Items.TomatoOnionLettuceSalad);
        CombinationMap.put(Items.CutOnion.name()+Items.LettuceTomatoSalad.name(), Items.TomatoOnionLettuceSalad);

        //Burgers
        CombinationMap.put(Items.CookedPatty.name()+Items.ToastedBuns.name(), Items.Burger);
        CombinationMap.put(Items.Cheese.name()+Items.Burger.name(), Items.CheeseBurger);
    }

}
