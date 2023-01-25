package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.BTexture;
import com.mygdx.game.BlackCore.GameObject;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.BlackCore.Pathfinding.occupationID;
import com.mygdx.game.BlackScripts.CustomerManager;
import com.mygdx.game.CoreData.Items.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CreateGameWorld
{
    int col_redct = 5;
    GameObject KitchenFloor;

    List<GameObject> WorkStations = new LinkedList<>();
    GameObject DiningFloor;
    GameObject ServingCounter;
    GameObject ChoppingBoard;
    //Food Crates
    GameObject CheeseCrate, LettuceCrate, BunsCrate, OnionCrate, TomatoCrate, MeatCrate;
    GameObject Stove1;
    GameObject Stove2;
    GameObject CombinationCounter;
    GameObject KitchenSouthWall;

    public Vector3 SpawnPointChef1 = new Vector3 (0,0,0);
    public Vector3 SpawnPointChef2 = new Vector3(50,0,0);

    GameObject Table1;
    GameObject Table2;
    GameObject Table3;
    GameObject Table4;

    List<GameObject> Tables;

    int seat_per_side = 3;
    public List<Vector3> BossSeats;
    GameObject BossTable;

    GameObject Door;
    GameObject LightBeam;
    public Vector3 CustomerSpawnLocations;
    public List<Vector3> CustomerWaitingLocations;


    Vector3 TableGroupPosition = new Vector3(70,0,100);

    BTexture FloorTextureD;
    BTexture FloorTextureK;
    BTexture DoorTexture;
    BTexture LightBeamTexture;
    BTexture BlackTexture;
    BTexture StoveTexture;
    BTexture ChoppingBoardTexture;
    BTexture ServingCounterTexture;
    BTexture TableTexture;
    BTexture BossTableTexture;
    BTexture CombinationCounterTexture;

    public float TableRadius = 49;
   public List<GameObject> InteractableObjects = new LinkedList<>();


    public void Instantiate(GridPartition partition){
        FloorTextureK = new BTexture("wood.png",350,350);
        FloorTextureK.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);

        FloorTextureD = new BTexture("wood.png",330,350);
        FloorTextureD.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);

        BlackTexture = new BTexture("black.png",380,350);
        BlackTexture.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);

        ServingCounterTexture = new BTexture("black.png",50,100);
        ServingCounterTexture.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);

        BossTableTexture = new BTexture("black.png",190,40);
        BossTableTexture.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);

        TableTexture = new BTexture("table.png", (int) TableRadius, (int) TableRadius);
        TableTexture.setWrap(Texture.TextureWrap.ClampToEdge,Texture.TextureWrap.ClampToEdge);

        DoorTexture = new BTexture("door.png",49,49);
        DoorTexture.setWrap(Texture.TextureWrap.ClampToEdge);

        LightBeamTexture = new BTexture("lightBeam.png",101,112);
        LightBeamTexture.setWrap(Texture.TextureWrap.ClampToEdge);

        StoveTexture = new BTexture("purple.png",50,50);
        StoveTexture.setWrap(Texture.TextureWrap.Repeat);

        ChoppingBoardTexture  = new BTexture("cyan.png",50,50);
        ChoppingBoardTexture.setWrap(Texture.TextureWrap.Repeat);



        CombinationCounterTexture = new BTexture("black.png",75,150);
        CombinationCounterTexture.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);


        KitchenFloor = new GameObject(new Rectangle(400,25,350,300), FloorTextureK);
        KitchenFloor.transform.position.x = 430;
        KitchenFloor.transform.position.z = 20;
        KitchenFloor.transform.position.y = -1;

        DiningFloor = new GameObject(new Rectangle(50,25,330,300), FloorTextureD);
        DiningFloor.transform.position.x = 50;
        DiningFloor.transform.position.z = 20;
        DiningFloor.transform.position.y = -1;


        ServingCounter = new GameObject(new Rectangle(KitchenFloor.transform.position.x-50+col_redct,KitchenFloor.transform.position.z+100-col_redct,50-col_redct*2,100-col_redct*2), ServingCounterTexture);
        ServingCounter.transform.position.x = KitchenFloor.transform.position.x-50;
        ServingCounter.transform.position.z = KitchenFloor.transform.position.z+100;
        ServingCounter.transform.position.y = 1;
        ServingCounter.addStaticCollider(partition, occupationID.Station);
        InteractableObjects.add(ServingCounter);

        CustomerWaitingLocations = new ArrayList<>(CustomerManager.maxGroupSize);
        float CustomerOffsetDistance = Math.max(150.0f/CustomerManager.maxGroupSize,25);
        for (int i = 0; i < Math.max(CustomerManager.maxGroupSize,seat_per_side * 2); i++) {
            CustomerWaitingLocations.add(new Vector3( ServingCounter.transform.position.x-25, (float) 0, (float) (Math.ceil(ServingCounter.transform.position.z*50f)/50.0f +i*CustomerOffsetDistance)));
        }



        CombinationCounter = new GameObject(new Rectangle( 120+ KitchenFloor.transform.position.x ,105+KitchenFloor.transform.position.z, 75,150),CombinationCounterTexture);
        CombinationCounter.transform.position.x =  120 + KitchenFloor.transform.position.x;
        CombinationCounter.transform.position.z = 105 + KitchenFloor.transform.position.z;
        CombinationCounter.transform.position.y = 1;
        CombinationCounter.addStaticCollider(partition, occupationID.Station);
        WSCounter CC = new WSCounter();
        CombinationCounter.AppendScript(CC);
        InteractableObjects.add(CombinationCounter);

        WorkStations.add(CombinationCounter);


        createMachines(partition);

        createFoodCrates(partition);


        createTables(partition);

        Door = new GameObject(new Rectangle(50,20,330,300), DoorTexture);
        Door.transform.position.x = 0+DiningFloor.transform.position.x;
        Door.transform.position.z = 250+DiningFloor.transform.position.z;
        Door.transform.position.y = 1;


        LightBeam = new GameObject(new Rectangle(50,20,330,300), LightBeamTexture);
        LightBeam.transform.position.x = 0+DiningFloor.transform.position.x;
        LightBeam.transform.position.z = 252+DiningFloor.transform.position.z;
        LightBeam.transform.position.y = 0;
        LightBeam.transform.scale.set(.8f,1,.8f);
        CustomerSpawnLocations =  new Vector3( LightBeam.transform.position).add(new Vector3(0,0,50));

    }


    void createMachines(GridPartition partition){

        Stove1 = new GameObject(new Rectangle( 300+ KitchenFloor.transform.position.x ,225+KitchenFloor.transform.position.z, 50,50),StoveTexture);
        Stove1.transform.position.x = 300 + KitchenFloor.transform.position.x;
        Stove1.transform.position.z = 225 + KitchenFloor.transform.position.z;
        Stove1.transform.position.y = 2;
        Stove1.transform.scale.set(1,0,1);
        Stove1.addStaticCollider(partition, occupationID.Station);
        WSHob hob= new WSHob();
        Stove1.AppendScript(hob);


        ChoppingBoard = new GameObject(new Rectangle( 300+ KitchenFloor.transform.position.x ,150+KitchenFloor.transform.position.z, 50,50),ChoppingBoardTexture);
        ChoppingBoard.transform.position.x = 300 + KitchenFloor.transform.position.x;
        ChoppingBoard.transform.position.z = 150 + KitchenFloor.transform.position.z;
        ChoppingBoard.transform.position.y = 2;
        ChoppingBoard.transform.scale.set(1,0,1);
        ChoppingBoard.addStaticCollider(partition, occupationID.Station);
        WSChopBoard chb = new WSChopBoard();
        ChoppingBoard.AppendScript(chb);

        Stove2 = new GameObject(new Rectangle( 300+ KitchenFloor.transform.position.x ,75+KitchenFloor.transform.position.z, 50,50),StoveTexture);
        Stove2.transform.position.x = 300 + KitchenFloor.transform.position.x;
        Stove2.transform.position.z = 75 + KitchenFloor.transform.position.z;
        Stove2.transform.position.y = 2;
        Stove2.transform.scale.set(1,0,1);
        Stove2.addStaticCollider(partition, occupationID.Station);
        hob= new WSHob();
        Stove2.AppendScript(hob);

        InteractableObjects.add(Stove1);
        InteractableObjects.add(Stove2);
        InteractableObjects.add(ChoppingBoard);

        WorkStations.add(Stove1);
        WorkStations.add(Stove2);
        WorkStations.add(ChoppingBoard);


    }

    void createFoodCrates(GridPartition partition){




        int CrateWidth =30;
        int CrateHeight =30;
        int TopOfKitchen = 326;
        int CrateOffset = 50;
        int CrateStart = 75;
        String CratePath = "Crate.png";
        CreateCrate(LettuceCrate,Items.Lettuce,new Vector3(CrateStart + (CrateWidth +  CrateOffset)*0,1,TopOfKitchen),"lettuce"+CratePath,CrateWidth,CrateHeight, partition);
        CreateCrate(OnionCrate,Items.Onion,  new Vector3(CrateStart + (CrateWidth +  CrateOffset)*1,1,TopOfKitchen),"onion"  +CratePath,CrateWidth,CrateHeight,partition);
        CreateCrate(TomatoCrate,Items.Tomato, new Vector3(CrateStart + (CrateWidth +  CrateOffset)*2,1,TopOfKitchen),"tomato" +CratePath,CrateWidth,CrateHeight,partition);

        CreateCrate(MeatCrate,Items.Mince,  new Vector3(CrateStart + (CrateWidth +  CrateOffset)*0,1,0),"mince"  +CratePath,CrateWidth,CrateHeight,partition);
        CreateCrate(CheeseCrate,Items.Cheese, new Vector3(CrateStart + (CrateWidth +  CrateOffset)*1,1,0),"cheese" +CratePath,CrateWidth,CrateHeight,partition);
        CreateCrate(BunsCrate,Items.Buns,   new Vector3(CrateStart + (CrateWidth +  CrateOffset)*2,1,0),"buns"   +CratePath,CrateWidth,CrateHeight,partition);

    }

    void CreateCrate(GameObject crate, Items item,Vector3 position,String texture, int width, int height, GridPartition partition){

        position.x += KitchenFloor.transform.position.x;
        position.z += KitchenFloor.transform.position.z;
        BTexture tex = new BTexture(texture,width,height);
        crate = new GameObject(new Rectangle(position.x,position.z,width,height),tex);
        crate.transform.position.set(position);
        FoodCrate fCrate = new FoodCrate(item);
        crate.addStaticCollider(partition, occupationID.Station);
        crate.AppendScript(fCrate);
        WorkStations.add(crate);



        InteractableObjects.add(crate);



    }
    float floorToMultipleOfCellWidth(float a, float CellWidth){
        return (float) (Math.ceil(a/ CellWidth) *CellWidth);

    }
    void createTables(GridPartition  partition){
        float width = 1.5f;
        float offsetX = 70;
        float offsetY = 70;

        TableRadius = TableRadius * width;

        Tables = new LinkedList<>();

        Table1 = new GameObject(new Rectangle( TableGroupPosition.x + DiningFloor.transform.position.x ,TableGroupPosition.z+DiningFloor.transform.position.z, TableRadius,TableRadius),TableTexture);
        Table1.transform.position.x =  floorToMultipleOfCellWidth(TableGroupPosition.x + DiningFloor.transform.position.x, partition.sizeOfGridSpaces);
        Table1.transform.position.z =  floorToMultipleOfCellWidth(TableGroupPosition.z + DiningFloor.transform.position.z, partition.sizeOfGridSpaces);
        Table1.transform.position.y = 2;
        Table1.transform.scale.set(width,0,width);
        Table1.addStaticCollider(partition, occupationID.Blocked);
        Tables.add(Table1);

        Table2 = new GameObject(new Rectangle( width*offsetX+TableGroupPosition.x +  DiningFloor.transform.position.x ,width * offsetY+TableGroupPosition.z+ DiningFloor.transform.position.z, TableRadius,TableRadius),TableTexture);
        Table2.transform.position.x =  floorToMultipleOfCellWidth(width * offsetX+TableGroupPosition.x + DiningFloor.transform.position.x, partition.sizeOfGridSpaces);
        Table2.transform.position.z = floorToMultipleOfCellWidth(width * offsetY+TableGroupPosition.z + DiningFloor.transform.position.z, partition.sizeOfGridSpaces);
        Table2.transform.position.y = 2;
        Table2.transform.scale.set(width,0,width);
        Table2.addStaticCollider(partition, occupationID.Blocked);

        Tables.add(Table2);

        Table3 = new GameObject(new Rectangle( TableGroupPosition.x + DiningFloor.transform.position.x ,width*offsetY+TableGroupPosition.z+DiningFloor.transform.position.z, TableRadius,TableRadius),TableTexture);
        Table3.transform.position.x =  floorToMultipleOfCellWidth(TableGroupPosition.x + DiningFloor.transform.position.x, partition.sizeOfGridSpaces);
        Table3.transform.position.z = floorToMultipleOfCellWidth(width*offsetY+TableGroupPosition.z + DiningFloor.transform.position.z, partition.sizeOfGridSpaces);
        Table3.transform.position.y = 2;
        Table3.transform.scale.set(width,0,width);
        Table3.addStaticCollider(partition, occupationID.Blocked);
        Tables.add(Table3);

        Table4 = new GameObject(new Rectangle( width*offsetX+TableGroupPosition.x+ DiningFloor.transform.position.x ,TableGroupPosition.z+DiningFloor.transform.position.z, TableRadius,TableRadius),TableTexture);
        Table4.transform.position.x = floorToMultipleOfCellWidth( width*offsetX+TableGroupPosition.x + DiningFloor.transform.position.x, partition.sizeOfGridSpaces);
        Table4.transform.position.z = floorToMultipleOfCellWidth(TableGroupPosition.z + DiningFloor.transform.position.z, partition.sizeOfGridSpaces);
        Table4.transform.position.y = 2;
        Table4.transform.scale.set(width,0,width);
        Table4.addStaticCollider(partition, occupationID.Blocked);
        Tables.add(Table4);





        BossTable = new GameObject(new Rectangle(TableGroupPosition.x+ DiningFloor.transform.position.x ,width * offsetY+TableGroupPosition.z+ DiningFloor.transform.position.z, TableRadius,TableRadius),BossTableTexture);
        BossTable.transform.position.x = floorToMultipleOfCellWidth( 70 + DiningFloor.transform.position.x, partition.sizeOfGridSpaces);
        BossTable.transform.position.z = floorToMultipleOfCellWidth( 30 +DiningFloor.transform.position.z, partition.sizeOfGridSpaces);
        BossTable.transform.position.y = 2;
        BossTable.transform.scale.set(1,0,1);
        BossTable.addStaticCollider(partition,occupationID.Blocked);

        //apply seating arrangement
        int seat_per_side = 3;
        float seatOffset = 75;
        BossSeats = new LinkedList<Vector3>();
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < seat_per_side; x++) {
                BossSeats.add(new Vector3(x * seatOffset,0,y * (25+BossTableTexture.height)-25).add(BossTable.transform.position));
            }
        }

        Tables.add(BossTable);

        TableRadius /=2.0f;

    }


    public void Reset(){


        for (GameObject obj: WorkStations
             ) {
            ((WorkStation)obj.blackScripts.get(0)).Reset();

        }


    }
}
