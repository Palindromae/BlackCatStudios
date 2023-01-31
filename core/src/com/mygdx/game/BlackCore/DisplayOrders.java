package com.mygdx.game.BlackCore;
import com.mygdx.game.CoreData.Items.Items;
import com.mygdx.game.MyGdxGame;

import java.util.*;


public class DisplayOrders extends MyGdxGame {
    public static DisplayOrders displayOrders; //Initializes variable of DisplayOrders


    public DisplayOrders(){
        if(displayOrders != null)
            return;
        displayOrders = this;
    }
    public HashMap<Integer, List<Items>> orderDict = new HashMap<>(); //Stores all the orders placed by customers
    public static HashMap<Integer, Boolean> seen = new HashMap<>();
    public static boolean allSeen = false; //Boolean variable to show if all the orders have been seen


    /**
     * Adds to orderDict based on order id and the order
     * @param id
     * @param order
     */
    public void addOrder(int id, List<Items> order) {
        displayOrders.orderDict.put(id, order);
    }

    /**
     * Removes the order from orderDict based on the order id
     * @param id
     */
    public void removeOrder(int id) {
        displayOrders.orderDict.remove(id);
    }

    /**
     * Get all orders
     * @return List of Lists of orders
     */
    public List<List<Items>> getAllOrders() { //Returns all the orders stored in the orderDict (returns all active orders)
        LinkedList<List<Items>> allOrders = new LinkedList<>();
        for (Map.Entry<Integer, List<Items>> entry : displayOrders.orderDict.entrySet()) {
            List<Items> value = entry.getValue();
            allOrders.add(value);
        }
        return allOrders;
    }


    /**
     * Gets a single order based on id
     * @param id
     * @return the order requested
     */
    public List<Items> getOrder(Integer id){
        return displayOrders.orderDict.get(id);
    } // Returns the ID of an order placed
}
