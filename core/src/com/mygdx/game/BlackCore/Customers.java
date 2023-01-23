package com.mygdx.game.BlackCore;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.Pathfinding.DistanceCalculator;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.BlackScripts.CustomerManager;
import com.mygdx.game.BlackScripts.PathfindingAgent;
import com.mygdx.game.CoreData.Items.Items;
import com.mygdx.game.MyGdxGame;

import java.util.LinkedList;
import java.util.List;

public class Customers {
    public int count;
    public List<Items> orderList = new LinkedList<>();
    public float frustration;

    public List<GameObject> customerObjects = new LinkedList<>();

    public List<GameObject> satisfiedCustomers = new LinkedList<>();


    Table table;

    public Customers(Vector3 centralLocation, List<Items> orders, BTexture cusTex, GridPartition partition, Table table){

        int count = orders.size();
        orderList = orders;
        this.table = table;
        for (int i = 0; i < count; i++) {
            customerObjects.add(i, new GameObject(new Rectangle(), cusTex));
            customerObjects.get(i).transform.position = new Vector3(centralLocation);
            customerObjects.get(i).transform.gridPartition = CustomerManager.customermanager.gridPartition;

            PathfindingAgent PA = new PathfindingAgent();
            customerObjects.get(i).AppendScript(PA);//Needs to be the first script
        }
        MoveCustomersToLinePosition();
        frustration = 0;
    }

    

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

    void MoveCustomerToSeat(){
        GameObject obj  = customerObjects.get(0);
        customerObjects.remove(0);
        Vector3 nextSeat = table.GetSeat();
        PathfindingAgent agent = ((PathfindingAgent) satisfiedCustomers.get(0).blackScripts.get(0));
        agent.updatePath(obj.transform.gridPartition.pathfindFromWorldCoord(obj.transform.position.x,obj.transform.position.z,nextSeat.x,nextSeat.z, MyGdxGame.pathfindingConfig,DistanceCalculator.Manhatten));

        satisfiedCustomers.add(obj);
        MoveCustomersToLinePosition();
    }
    public void GetCustomersToLeave(){

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
                MoveCustomerToSeat();
                orderList.remove(i);
                return  true;
            }


        }

        return  false;
    }

}
