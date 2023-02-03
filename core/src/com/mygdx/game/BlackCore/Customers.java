package com.mygdx.game.BlackCore;

import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.Pathfinding.DistanceCalculator;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.BlackScripts.Animate;
import com.mygdx.game.BlackScripts.CustomerManager;
import com.mygdx.game.BlackScripts.PathfindingAgent;
import com.mygdx.game.CoreData.Items.Items;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SoundFrame;

import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;

public class Customers {
    public int count;
    public List<Items> orderList = new LinkedList<>();
    public float frustration;

    private float MaxFrustration = 120;
    private float change_in_frustration_after_feeding = -20;
    public List<GameObject> customerObjects = new LinkedList<>();

    public List<GameObject> satisfiedCustomers = new LinkedList<>();

Vector3 spawnPos;
    Table table;

    /**
     * Createsa  group of customers
     * @param centralLocation Spawn location
     * @param orders orders they are making
     * @param cusTex Textures
     * @param partition The grid partition they are standingon
     * @param table Table they have "resevered"
     */
    public Customers(Vector3 centralLocation, List<Items> orders, List<String> cusTex, GridPartition partition, Table table){

        int count = orders.size();
        orderList = orders;
        this.table = table;
        spawnPos = centralLocation;
        for (int i = 0; i < count; i++) {

            PathfindingAgent PA = new PathfindingAgent();
            Animate animator = new CharacterAnimator(.25f, cusTex.get(i), 20,20, 21,13,PA);
            customerObjects.add(i, new GameObject(new Rectangle(),  animator.tex,64,64));

            customerObjects.get(i).setMaintainedOffset(32,0);
            customerObjects.get(i).transform.position = new Vector3(centralLocation);
            customerObjects.get(i).transform.gridPartition = CustomerManager.customermanager.gridPartition;

            customerObjects.get(i).AppendScript(PA);//Needs to be the first script
            customerObjects.get(i).AppendScript(animator);
        }

        SoundFrame.SoundEngine.playSound("Customer Arrived Bell");
        MoveCustomersToLinePosition();
        frustration = 0;
    }

    /**
     * Returns the score
     * @return the score as a flow
     */
    public float getScore(){
        return MaxFrustration - frustration;
    }

    /**
     * Moves customers to be lined up correctly
     */
    void MoveCustomersToLinePosition(){
        int i = 0;
        for (GameObject obj :customerObjects
             ) {
            PathfindingAgent agent = ((PathfindingAgent) obj.blackScripts.get(0));
            Vector3 p = CustomerManager.customermanager.WaitingPositions.get(i);

            agent.updatePath(obj.transform.gridPartition.pathfindFromWorldCoord(obj.transform.position.x,obj.transform.position.z,p.x,p.z, MyGdxGame.pathfindingConfig, DistanceCalculator.Manhatten));
            i++;
        }
    }

    /**
     * Move customer to exit
     */
    public void MoveCustomersToExit(){
        for (GameObject obj: satisfiedCustomers
             ) {
        MoveCustomerToPos(obj,spawnPos);
        }
    }

    /**
     * Try and delete the customers when they reach the exit or any empty path
     * @return whether it was successfully able to delete all customers
     */
    public boolean TryDestroyOnEmptyPaths(){


        for (GameObject obj: satisfiedCustomers
             ) {

            if(((PathfindingAgent) obj.blackScripts.get(0)).pathLength()>0)
                return false;

        }

        Destroy();
    return  true;
    }

    /**
     * Move customers to seats around tables
     */
    void MoveCustomerToSeat(){
        GameObject obj  = customerObjects.get(0);
        customerObjects.remove(0);
        Vector3 nextSeat = table.GetSeat();

        MoveCustomerToPos(obj,nextSeat);
        satisfiedCustomers.add(obj);
    }

    /**
     * Destory everything and reset it all
     */
    public void Destroy(){
        for (GameObject obj: satisfiedCustomers
        ) {

            obj.Destroy();

        }

        for (GameObject obj: customerObjects
        ) {

            obj.Destroy();

        }

        table.reset();

    }

    /**
     * Move Customer to specific location
     * @param obj obj to move
     * @param pos, position to go to
     */
    void MoveCustomerToPos(GameObject obj, Vector3 pos){
        PathfindingAgent agent = ((PathfindingAgent) obj.blackScripts.get(0));
        agent.updatePath(obj.transform.gridPartition.pathfindFromWorldCoord(obj.transform.position.x,obj.transform.position.z,pos.x,pos.z, MyGdxGame.pathfindingConfig,DistanceCalculator.Manhatten));
        MoveCustomersToLinePosition();
    }

    /**
     * Updates the frustration
     * @param delta_frustration how much it should increase by
     */
    public void updateFrustration(float delta_frustration){
        frustration = Math.min(MaxFrustration, frustration + delta_frustration);
    }

    /**
     * Have the customer group been satisified
     * @return truth value to this
     */
    public Boolean IsSatisfied(){
       return orderList.size()==0;
    }

    /**
     * See if the item is part of there order, if so delete the item and remove it from order. Reduces frustration
     * @param item
     * @return If it was in the order
     */
    public Boolean TestAndRemoveItemFromOrders(ItemAbs item){
        for (int i = 0; i < orderList.size(); i++) {
            if(orderList.get(i) == item.name)
            {
                MoveCustomerToSeat();
                orderList.remove(i);

                updateFrustration(change_in_frustration_after_feeding);
                return  true;
            }


        }


        return  false;
    }

    public void RemoveFirstCustomer(){
        MoveCustomerToSeat();
        orderList.remove(0);
    }

}
