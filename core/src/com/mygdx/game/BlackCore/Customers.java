package com.mygdx.game.BlackCore;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.CoreData.Items.Items;

import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Customers {
    public int count;
    public List<Items> orderList = new LinkedList<>();
    public float frustration;

    public List<GameObject> customerObjects = new LinkedList<>();


    public Customers(Vector3 centralLocation, int count, List<Items> orders, BTexture cusTex){

        count = orders.size();
        orderList = orders;
        for (int i = 0; i < count; i++) {
            customerObjects.set(i, new GameObject(new Rectangle(), cusTex));
        }

        frustration = 0;
    }

    public void updateFrustration(float delta_frustration){
        frustration += delta_frustration;
    }

    public Boolean IsSatisfied(){
       return orderList.size()==0;
    }

    public Boolean TestAndRemoveItemFromOrders(ItemAbs item){
        for (int i = 0; i < orderList.size(); i++) {
            if(orderList.get(i) == item.name)
            {
                orderList.remove(i);
                return  true;
            }


        }

        return  false;
    }

}
