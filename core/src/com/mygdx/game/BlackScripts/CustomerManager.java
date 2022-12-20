package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.math.Vector3;
import com.dongbat.jbump.Item;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.CoreData.Items.Items;

import java.util.*;

public class CustomerManager extends BlackScripts {
public static CustomerManager customermanager;
Vector3 spawningLocation;
BTexture customerTexture;

List<Customers> WaitingCustomers = new LinkedList<>();

int NumberOfTables = 10;
List<Customers> SeatedCustomers = new LinkedList<>();
List<MenuItem> Menu = new ArrayList<>();//this is a list sets of items. So that one type of item isnt over represented because it has more variations
Random rand = new Random();

float TimeToNextLeave = 0;
float EatingTime = 10;
int StockFloor = 5;
int MaxStockCapacity = 15;
int refillCapability = 5;
int minGroupSize = 2;
int maxGroupSize = 5;
enum RandomisationStyle{
    Random,
    LimitedRandom
    }


    public CustomerManager(){
        if (customermanager != null)
            return;
        customermanager = this;
    }

    public void setCustomerTexture(BTexture customerTexture) {
        this.customerTexture = customerTexture;
    }

    public void invokeNewCustomer(){
        int count = rand.nextInt(minGroupSize,maxGroupSize+1);
        Customers customerGroup = new Customers(spawningLocation, count, CreateRandomOrder(count,RandomisationStyle.LimitedRandom),customerTexture);
    }

    List<Items> CreateRandomOrder(int count, RandomisationStyle ranStyle){
        if(ranStyle == RandomisationStyle.Random)
            return CreatePureRandomOrder(count);

        if(ranStyle == RandomisationStyle.LimitedRandom){

        }
        throw new IllegalArgumentException("you failed to set a correct to set a correct randomisation pattern");
    }



    List<Items> CreateLimitedRandomOrder(int count){
        List<Items> _items = new LinkedList<>();
        int pot = 0;
        //Sum the stock
        for (int i = 0; i < count; i++)
            pot += Menu.get(i).count;



        for (int i = 0; i < count; i++) {
            //find random index in the pot
            int n = rand.nextInt(pot);
            //Correct menu item
            MenuItem cmi = null;
            for (MenuItem mi: Menu
                 ) {
                //Reduce the position until you find the correct grouping
                n -= mi.count;
                if(n<=0){
                    cmi = mi;
                    break;
                }

            }
            //if there was a stock reduction reduce the pot size
            if(cmi.removeStock(StockFloor))
                pot--;
           _items.add(cmi.Variations.get(rand.nextInt(cmi.Variations.size())));
        }

        return _items;
    }
    List<Items> CreatePureRandomOrder(int count){

    List<Items> _items = new LinkedList<>();

        for (int i = 0; i < count; i++) {
            int n = rand.nextInt(Menu.size());
            _items.add( Menu.get(n).Variations.get(rand.nextInt(Menu.get(n).Variations.size())));
        }

    return _items;
    }


    void RefillStock(){
        for (MenuItem i: Menu
             ) {

            i.count = Math.max(i.count + refillCapability, MaxStockCapacity);

        }
    }


    public Boolean IsFoodInOrder(ItemAbs item){
    if(WaitingCustomers.get(0).TestAndRemoveItemFromOrders(item)){

        return true;
    }
    return false;
    }

    //Implementation of customer management

    void UpdateCustomerHead(float dt){
    Customers head =     WaitingCustomers.get(0);

    if(head.IsSatisfied()){
        WaitingCustomers.remove(0);
        SeatedCustomers.add(head);
       // head.frustration = 0;
        //Customer should now leave for a table
        return;
    }

    head.updateFrustration(1);

    }

    void UpdateSeatedCustomer(float dt){
        //Reset the counter if there its time for someone to leave
        if(TimeToNextLeave<= 0){
            TimeToNextLeave = EatingTime;
            SeatedCustomers.remove(0);
            //Should Signal here to pathfind to exit
        }

        //Decrement the count if there is someone seated
        if(SeatedCustomers.size()>0)
            EatingTime -= dt;
    }
    void UpdateWaitingCustomers(float dt){
        if(SeatedCustomers.size()<NumberOfTables){

            if(WaitingCustomers.size()!=0)
                UpdateCustomerHead(dt);


        }
        //Check if theres a seated customer
        if(SeatedCustomers.size()>0)
            UpdateSeatedCustomer(dt);



    }


    //Im using fixed update so customers are updated at frame rate so they cant leave inbetween frames
    @Override
    public void Update(float dt) {
        super.Update(dt);
        UpdateWaitingCustomers(dt);
    }
}
