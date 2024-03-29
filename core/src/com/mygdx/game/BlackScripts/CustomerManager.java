package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.CoreData.Items.Items;
import com.mygdx.game.MyGdxGame;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import static com.mygdx.game.BlackCore.DisplayOrders.displayOrders;


public class CustomerManager extends BlackScripts {
public static CustomerManager customermanager;
public Vector3 spawningLocation;
public List<Vector3> WaitingPositions;
List<Customers> WaitingCustomers = new LinkedList<>();
List<Customers> LeavingCustomers = new LinkedList<>();

String[] CustomerAvatarsPaths = new String[]{
        "Characters/RedWoman.png","Characters/ScarfWoman.png","Characters/OldMan.png","Characters/IvernMan.png","Characters/strongman.png"
};

List<String> CustomerAvatars = new LinkedList<>();

public float Score = 0;

int wavesOfCustomers = 5;

int NumberOfCustomersSeen = 0;
int MaxNumberOfCustomers = 5;//(int)Math.round(wavesOfCustomers * (minGroupSize+ maxGroupSize)/2.0f)
int bossWave = 0;
int bossAmount = 6;

int currentWave = 0;
int OrderID = 0;

private static Date startTime;
public double accumPauseTime = 0;



public Consumer<Score> EndGameCommand;

int NumberOfTables = 4;
 List<Customers> SeatedCustomers = new LinkedList<>();
 List<Table> Tables = new LinkedList<>();
 Table BossTable;
 public List<GameObject> RealTables = new LinkedList<>();
 public List<Vector3> BossTableSeats = new LinkedList<>();
 public float TableRadius;
 private float TableRadiusOffset = 10;
List<MenuItem> Menu = new ArrayList<>();//this is a list sets of items. So that one type of item isnt over represented because it has more variations
Random rand = new Random();

public GridPartition gridPartition;

float EatingTime = 10;

float TimeToNextLeave = EatingTime;

int StockFloor = 5;
int MaxStockCapacity = 15;
int refillCapability = 5;
public static int minGroupSize = 1;
public static int maxGroupSize = 3;
public double ElapsedTime = 0;
enum RandomisationStyle{
    Random,
    LimitedRandom
    }

    /**
     * Create a customer manager
     * @param _RealTables list of all tables
     * @param Partition GridPartition customers walk on
     * @param TableRadius Size of table
     */
    public CustomerManager(List<GameObject> _RealTables, GridPartition Partition, float TableRadius){
        if (customermanager != null)
            return;

        customermanager = this;
        gridPartition = Partition;

        CreateMenu();

        RealTables = _RealTables;

        int i = 0;

        //Sets up each table
        for (GameObject t: RealTables
             ) {
            i++;
            if(i != RealTables.size())
            Tables.add(new Table(t.transform.position, TableRadius + TableRadiusOffset, TableRadiusOffset));
            else
                BossTable = new Table(t.transform.position,-1, 0);


        }

        for (String path:CustomerAvatarsPaths
             ) {
            CustomerAvatars.add(path);
        }

        Collections.shuffle(CustomerAvatars);

//        startTime = new Date(TimeUtils.millis());
    }

    public void setStartTime(){
        startTime = new Date(TimeUtils.millis());
    }


    /**
     * Returns the next table
     * @return
     */
    Table getNextFreeTable(){
        for (Table t: Tables
        ) {
            if (t.seats != null && t.seats.size() >0)
                continue;
            return t;
        }

        return null;
    }

    /**
     * Fully create a new customer
     */
    public void invokeNewCustomer(){
        int count = GetNumberOfCustomersInWave();

        Table tableToUse = getNextFreeTable();
        tableToUse.DefineSeatingArrangement(count);

        List<String> textures = new LinkedList<>();

        //Get new image
        for (int i = 0; i < count; i++) {
            textures.add(CustomerAvatars.get(i-count+NumberOfCustomersSeen));
        }

        //Create a customer group and give it a limited random orders
        Customers customerGroup = new Customers(spawningLocation, CreateRandomOrder(count,RandomisationStyle.LimitedRandom),textures,gridPartition,tableToUse);
        WaitingCustomers.add(customerGroup);
    }

    //Randomly chooses each item in order
    List<Items> CreateRandomOrder(int count, RandomisationStyle ranStyle){
        LinkedList<Items> allOrders = new LinkedList<>();
        OrderID = ThreadLocalRandom.current().nextInt(1,1000);
        if(ranStyle == RandomisationStyle.Random){
            allOrders = (LinkedList<Items>) CreatePureRandomOrder(count);
            displayOrders.orderDict.put(OrderID, allOrders);
            DisplayOrders.allSeen = false;
            OrderAlerts.checkIfToShowAlert();
            return allOrders;
        }
        if(ranStyle == RandomisationStyle.LimitedRandom){
            allOrders = (LinkedList<Items>) CreateLimitedRandomOrder(count);;
            displayOrders.orderDict.put(OrderID, allOrders);
            DisplayOrders.allSeen = false;
            OrderAlerts.checkIfToShowAlert();
            return allOrders;
        }
        throw new IllegalArgumentException("you failed to set a correct to set a correct randomisation pattern");
    }


    /**
     * Creates a limited random order where each time a type is selected its less likely to be selected
     * @param count number of orders
     * @return
     */
    List<Items> CreateLimitedRandomOrder(int count){
        List<Items> _items = new LinkedList<>();
        int pot = 0;
        //Sum the stock
        for (int i = 0; i < Menu.size(); i++)
            pot += Menu.get(i).count;



        for (int i = 0; i < count; i++) {
            //find random index in the pot
            int n = rand.nextInt(pot+1);
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
        Score += head.getScore();
       // head.frustration = 0;
        //Customer should now leave for a table
        return;
    }

    head.updateFrustration(1 * dt);

    }

    void UpdateSeatedCustomer(float dt){
        //Reset the counter if there its time for someone to leave
        if(TimeToNextLeave<= 0){
            TimeToNextLeave = EatingTime;
            Customers head = SeatedCustomers.get(0);
            SeatedCustomers.remove(0);
            LeavingCustomers.add(head);
            head.MoveCustomersToExit();
            //Should Signal here to pathfind to exit

        }

        //Decrement the count if there is someone seated
        if(SeatedCustomers.size()>0)
            TimeToNextLeave -= dt;
    }
    void UpdateWaitingCustomers(float dt){
        //f(SeatedCustomers.size()<NumberOfTables){

            if(WaitingCustomers.size()!=0)
                UpdateCustomerHead(dt);


       // }
        //Check if theres a seated customer
        if(SeatedCustomers.size()>0)
            UpdateSeatedCustomer(dt);



    }


    int GetNumberOfCustomersInWave(){
    int count = rand.ints(1,minGroupSize,maxGroupSize+1).sum();
    count = Math.max(1, Math.min(MaxNumberOfCustomers- (wavesOfCustomers-currentWave)-NumberOfCustomersSeen, count));
    NumberOfCustomersSeen += count;
    return count;



    }

    void UpdateWave(){

    if(getNextFreeTable() != null) {
        currentWave++;
        if (currentWave <= wavesOfCustomers) {
            invokeNewCustomer();


       // }// else if (currentWave < wavesOfCustomers + bossWave) {
           // BossTable.DefineSeatingArrangement(BossTableSeats);
            //Customers customerGroup = new Customers(spawningLocation, CreateRandomOrder(BossTableSeats.size(), RandomisationStyle.Random), CustomerAvatars, gridPartition, BossTable);
           // WaitingCustomers.add(customerGroup);
        } else{
            //end the game
            if (MyGdxGame.getGameRunning()){
                // Gets the time when the game started (user pressed play)
                ElapsedTime = startTime.getTime();
                // Calculates the time elapsed since the game started (in seconds)
                ElapsedTime = (new Date(TimeUtils.millis()).getTime() - ElapsedTime)/1000;
                // Subtracts the time paused from the total time elapsed (in seconds)
                ElapsedTime -= accumPauseTime;
                // Passes the score to the end game function
                EndGameCommand.accept(new Score(Score,ElapsedTime));
            }


        }

    }

    }

    @Override
    public void Update(float dt) {
        super.Update(dt);
        UpdateWaitingCustomers(dt);
        if(WaitingCustomers.size() == 0)
        {
            UpdateWave();
        }

        for (int i = LeavingCustomers.size()-1; i >= 0; i--) {

            if(LeavingCustomers.get(i).TryDestroyOnEmptyPaths())
                LeavingCustomers.remove(i);
        }

        /*if (Gdx.input.isKeyJustPressed(Input.Keys.S)){
            if(WaitingCustomers.size()>0)
            WaitingCustomers.get(0).RemoveFirstCustomer();

        }*/
    }
        public void CreateMenu(){
            Menu = new LinkedList<>();
            List<Items> ItemVar = new LinkedList<>();
            ItemVar.add(Items.Burger);
            ItemVar.add(Items.CheeseBurger);
            MenuItem burger = new MenuItem(Items.Burger,MaxStockCapacity, ItemVar);

            ItemVar = new LinkedList<>();
            ItemVar.add(Items.TomatoOnionLettuceSalad);
            ItemVar.add(Items.LettuceOnionSalad);
            ItemVar.add(Items.LettuceTomatoSalad);
            MenuItem salad = new MenuItem(Items.TomatoOnionLettuceSalad,MaxStockCapacity,ItemVar);

            Menu.add(burger);
            Menu.add(salad);
        }
    public void Reset()
    {
        Score = 0;
        accumPauseTime = 0;
        ElapsedTime = 0;
        NumberOfCustomersSeen = 0;
        currentWave = 0;
        CreateMenu();

        for (Customers cust: WaitingCustomers
             ) {
            cust.Destroy();
        }

        for (Customers cust: SeatedCustomers
        ) {
            cust.Destroy();
        }

        for (Customers cust:LeavingCustomers
             ) {
            cust.Destroy();
        }

        WaitingCustomers.clear();
        LeavingCustomers.clear();
        Collections.shuffle(CustomerAvatars);
        DisplayOrders.displayOrders.orderDict.clear();
    }
}
