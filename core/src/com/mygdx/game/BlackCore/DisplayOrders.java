package com.mygdx.game.BlackCore;
import com.mygdx.game.CoreData.Items.Items;
import com.mygdx.game.MyGdxGame;

import java.util.*;


public class DisplayOrders extends MyGdxGame {
    public static DisplayOrders displayOrders;


    public DisplayOrders(){
        if(displayOrders != null)
            return;
        displayOrders = this;
    }
    public HashMap<Integer, List<Items>> orderDict = new HashMap<>();
    public static HashMap<Integer, Boolean> seen = new HashMap<>();



    public void addOrder(int id, List<Items> order) {
        displayOrders.orderDict.put(id, order);
    }

    public void removeOrder(int id) {
        displayOrders.orderDict.remove(id);
    }

    /**
     * Get all orders
     * @return List of Lists of orders
     */
    public List<List<Items>> getAllOrders() {
        LinkedList<List<Items>> allOrders = new LinkedList<>();
        for (Map.Entry<Integer, List<Items>> entry : displayOrders.orderDict.entrySet()) {
            List<Items> value = entry.getValue();
            allOrders.add(value);
        }
        return allOrders;
    }

    public List<Items> getOrder(Integer id){
        return displayOrders.orderDict.get(id);
    }
}
