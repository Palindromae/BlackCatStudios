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

/*
This file creates all world game objects and stores them in case of future use
 */
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
    List<GameObject> CombinationCounters = new LinkedList<>();
    GameObject KitchenSouthWall;

    public Vector3 SpawnPointChef1 = new Vector3 (500,0,250);
    public Vector3 SpawnPointChef2 = new Vector3(500,0,150);

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


    Vector3 TableGroupPosition = new Vector3(70,0,110);

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
    BTexture CombinationCounterTextureEnd;
    BTexture CombinationCounterTextureEndDown;
    BTexture DividingWallTex;

    List<GameObject> DivWalls = new LinkedList<>();

    public float TableRadius = 75;
   public List<GameObject> InteractableObjects = new LinkedList<>();


    public void Instantiate(GridPartition partition){
        FloorTextureK = new BTexture("Pictures/KitchenTiles.png",null,null,4);
        FloorTextureK.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
        DividingWallTex = new BTexture("DividingWall.png", null,null);

        FloorTextureD = new BTexture("Pictures/Carpet.png",null,null,4);
        FloorTextureD.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);

        BlackTexture = new BTexture("Black.png",380,350);
        BlackTexture.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);

        ServingCounterTexture = new BTexture("Pictures/ServingCounter.png",null, null, 1,3);
        ServingCounterTexture.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);


        BossTableTexture = new BTexture("Pictures/BossTable.png",null,null);
        BossTableTexture.setWrap(Texture.TextureWrap.ClampToEdge,Texture.TextureWrap.ClampToEdge);

        TableTexture = new BTexture("Pictures/RoundTable.png", null , null);
        TableTexture.setWrap(Texture.TextureWrap.ClampToEdge,Texture.TextureWrap.ClampToEdge);

        DoorTexture = new BTexture("Door.png",49,49);
        DoorTexture.setWrap(Texture.TextureWrap.ClampToEdge);

        LightBeamTexture = new BTexture("lightBeam.png",101,112);
        LightBeamTexture.setWrap(Texture.TextureWrap.ClampToEdge);

        StoveTexture = new BTexture("Pictures/Hob.png",null,null);
        StoveTexture.setWrap(Texture.TextureWrap.ClampToEdge);

        ChoppingBoardTexture  = new BTexture("Pictures/ChoppingBoard.png",null,null);
        ChoppingBoardTexture.setWrap(Texture.TextureWrap.ClampToEdge);



        CombinationCounterTexture = new BTexture("Pictures/Counter.png",null,null);
        CombinationCounterTexture.setWrap(Texture.TextureWrap.ClampToEdge);

        CombinationCounterTextureEnd = new BTexture("Pictures/Counter-end.png",null,null);
        CombinationCounterTextureEnd.setWrap(Texture.TextureWrap.ClampToEdge);
        CombinationCounterTextureEndDown = new BTexture("Pictures/Counter-endDown.png",null,null);
        CombinationCounterTextureEndDown.setWrap(Texture.TextureWrap.ClampToEdge);

        KitchenFloor = new GameObject(new Rectangle(400,25,350,300), FloorTextureK,350,350);
        KitchenFloor.transform.position.x = 430;
        KitchenFloor.transform.position.z = 20;
        KitchenFloor.transform.position.y = -1;

        DiningFloor = new GameObject(new Rectangle(50,25,330,300), FloorTextureD,330,350);
        DiningFloor.transform.position.x = 50;
        DiningFloor.transform.position.z = 20;
        DiningFloor.transform.position.y = -1;


        WSServingCounter servingCounter = new WSServingCounter();
        ServingCounter = new GameObject(new Rectangle(KitchenFloor.transform.position.x-50+col_redct,KitchenFloor.transform.position.z+105-col_redct,50,100), ServingCounterTexture,50,100);
        ServingCounter.transform.position.x = KitchenFloor.transform.position.x-50;
        ServingCounter.transform.position.z = KitchenFloor.transform.position.z+105;
        ServingCounter.transform.position.y = 1;
        ServingCounter.addStaticCollider(partition, occupationID.Station);
        ServingCounter.AppendScript(servingCounter);
        InteractableObjects.add(ServingCounter);

        CustomerWaitingLocations = new ArrayList<>(CustomerManager.maxGroupSize);
        float CustomerOffsetDistance = Math.max(150.0f/CustomerManager.maxGroupSize,25);
        for (int i = 0; i < Math.max(CustomerManager.maxGroupSize,seat_per_side * 2); i++) {
            CustomerWaitingLocations.add(new Vector3( ServingCounter.transform.position.x-25, (float) 0, (float) (Math.ceil(ServingCounter.transform.position.z*50f)/50.0f +i*CustomerOffsetDistance)));
        }

        GameObject DivWalV;
        DivWalV = new GameObject(new Rectangle(KitchenFloor.transform.position.x-50+col_redct,KitchenFloor.transform.position.z+155-col_redct,50,200), DividingWallTex,
                50,200);
        DivWalV.transform.position.x = KitchenFloor.transform.position.x-50;
        DivWalV.transform.position.z = KitchenFloor.transform.position.z+155;
        DivWalV.transform.position.y = 1;
        DivWalV.addStaticCollider(partition, occupationID.Station);

        DivWalls.add(DivWalV);

        DivWalV = new GameObject(new Rectangle(KitchenFloor.transform.position.x-50+col_redct,KitchenFloor.transform.position.z-col_redct,50,105), DividingWallTex,
                50,105);
        DivWalV.transform.position.x = KitchenFloor.transform.position.x-50;
        DivWalV.transform.position.z = KitchenFloor.transform.position.z;
        DivWalV.transform.position.y = 1;
        DivWalV.addStaticCollider(partition, occupationID.Station);

        DivWalls.add(DivWalV);


        DivWalV = new GameObject(new Rectangle(KitchenFloor.transform.position.x-50+col_redct,KitchenFloor.transform.position.z+355-col_redct,100,50), DividingWallTex,
                500,50);
        DivWalV.transform.position.x = KitchenFloor.transform.position.x-50;
        DivWalV.transform.position.z = KitchenFloor.transform.position.z+355;
        DivWalV.transform.position.y = 1;
        DivWalV.addStaticCollider(partition, occupationID.Station);

        DivWalls.add(DivWalV);

        DivWalV = new GameObject(new Rectangle(KitchenFloor.transform.position.x+KitchenFloor.getTextureWidth(),KitchenFloor.transform.position.z-col_redct,50,KitchenFloor.getTextureHeight()), DividingWallTex,
                50,KitchenFloor.getTextureHeight());
        DivWalV.transform.position.x = KitchenFloor.transform.position.x+KitchenFloor.getTextureWidth();
        DivWalV.transform.position.z = KitchenFloor.transform.position.z;
        DivWalV.transform.position.y = 1;
        DivWalV.addStaticCollider(partition, occupationID.Station);

        DivWalls.add(DivWalV);


        DivWalV = new GameObject(new Rectangle(KitchenFloor.transform.position.x-50+col_redct,KitchenFloor.transform.position.z-col_redct-25,500,25), DividingWallTex,
                500,25);
        DivWalV.transform.position.x = KitchenFloor.transform.position.x-50;
        DivWalV.transform.position.z = KitchenFloor.transform.position.z-25;
        DivWalV.transform.position.y = 1;
        DivWalV.addStaticCollider(partition, occupationID.Station);

        DivWalls.add(DivWalV);

        createCombinationCounters(partition);

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

    void createCombinationCounters(GridPartition partition)
    {

        int middleSections = 2;
        int width = 2;
        int segmentHeight =  160/(middleSections+2);
        int segmentWidth = 75/2;

        GameObject CombinationCounter;
        float _x,_y = 0;
        for (int i = 1; i <= middleSections; i++)
        {
            for (int j = 0; j < 2; j++) {

                _x = j*segmentWidth+120 + KitchenFloor.transform.position.x;
                _y = i * segmentHeight + 105 + KitchenFloor.transform.position.z;
                CombinationCounter = new GameObject(new Rectangle(_x, _y, segmentWidth, segmentHeight), CombinationCounterTexture, segmentWidth, segmentHeight);
                CombinationCounter.transform.position.x = _x;
                CombinationCounter.transform.position.z = _y;
                CombinationCounter.transform.position.y = 1;
                CombinationCounter.addStaticCollider(partition, occupationID.Station);
                WSCounter CC = new WSCounter();
                CombinationCounter.AppendScript(CC);
                InteractableObjects.add(CombinationCounter);
                CombinationCounters.add(CombinationCounter);
                WorkStations.add(CombinationCounter);
            }

        }
        for (int i = 0; i < width; i++) {

            _x = i*segmentWidth+120 + KitchenFloor.transform.position.x;
            _y = 105 + KitchenFloor.transform.position.z;
            CombinationCounter = new GameObject(new Rectangle( _x ,_y, 75,150),CombinationCounterTextureEnd,segmentWidth,segmentHeight);
            CombinationCounter.transform.position.x =  _x;
            CombinationCounter.transform.position.z =  _y;
            CombinationCounter.transform.position.y = 1;
            CombinationCounter.addStaticCollider(partition, occupationID.Station);
            WSCounter CC = new WSCounter();
            CombinationCounter.AppendScript(CC);
            InteractableObjects.add(CombinationCounter);
            CombinationCounters.add(CombinationCounter);

            WorkStations.add(CombinationCounter);

            _x = i*segmentWidth+120 + KitchenFloor.transform.position.x;
            _y =  (middleSections+1)*segmentHeight + 105 + KitchenFloor.transform.position.z;
            CombinationCounter = new GameObject(new Rectangle( _x ,_y, segmentWidth,segmentHeight),CombinationCounterTextureEndDown,segmentWidth,segmentHeight);
            CombinationCounter.transform.position.x = _x;
            CombinationCounter.transform.position.z = _y;
            CombinationCounter.transform.position.y = 1;
            CombinationCounter.addStaticCollider(partition, occupationID.Station);
            CC = new WSCounter();
            CombinationCounter.AppendScript(CC);
            InteractableObjects.add(CombinationCounter);
            CombinationCounters.add(CombinationCounter);
            WorkStations.add(CombinationCounter);
        }



    }


    void createMachines(GridPartition partition){

        Stove1 = new GameObject(new Rectangle( 295+ KitchenFloor.transform.position.x ,225+KitchenFloor.transform.position.z, 50,50),StoveTexture,50,50);
        Stove1.transform.position.x = 300 + KitchenFloor.transform.position.x;
        Stove1.transform.position.z = 225 + KitchenFloor.transform.position.z;
        Stove1.transform.position.y = 2;
        Stove1.transform.scale.set(1,0,1);
        Stove1.addStaticCollider(partition, occupationID.Blocked);
        WSHob hob= new WSHob();
        Stove1.AppendScript(hob);
        hob.init();


        ChoppingBoard = new GameObject(new Rectangle( 300+ KitchenFloor.transform.position.x ,150+KitchenFloor.transform.position.z, 50,50),ChoppingBoardTexture,50,50);
        ChoppingBoard.transform.position.x = 300 + KitchenFloor.transform.position.x;
        ChoppingBoard.transform.position.z = 150 + KitchenFloor.transform.position.z;
        ChoppingBoard.transform.position.y = 2;
        ChoppingBoard.addStaticCollider(partition, occupationID.Blocked);
        WSChopBoard chb = new WSChopBoard();
        ChoppingBoard.AppendScript(chb);
        chb.init();


        Stove2 = new GameObject(new Rectangle( 300+ KitchenFloor.transform.position.x ,75+KitchenFloor.transform.position.z, 50,50),StoveTexture,50,50);
        Stove2.transform.position.x = 300 + KitchenFloor.transform.position.x;
        Stove2.transform.position.z = 75 + KitchenFloor.transform.position.z;
        Stove2.transform.position.y = 2;
        Stove2.transform.scale.set(1,0,1);
        Stove2.addStaticCollider(partition, occupationID.Blocked);
        hob= new WSHob();
        Stove2.AppendScript(hob);
        hob.init();

        InteractableObjects.add(Stove1);
        InteractableObjects.add(Stove2);
        InteractableObjects.add(ChoppingBoard);

        WorkStations.add(Stove1);
        WorkStations.add(Stove2);
        WorkStations.add(ChoppingBoard);




    }

    void createFoodCrates(GridPartition partition){




        int CrateWidth =45;
        int CrateHeight =50;
        int TopOfKitchen = 326;
        int CrateOffset = 50;
        int CrateStart = 75;
        String CratePath = "Pictures/Crate.png";
        CreateCrate(LettuceCrate,Items.Lettuce, new Vector3(CrateStart + (CrateWidth +  CrateOffset)*0,1,TopOfKitchen-CrateHeight/2+5),CratePath,CrateWidth,CrateHeight, partition);
        CreateCrate(OnionCrate,Items.Onion,     new Vector3(CrateStart + (CrateWidth +  CrateOffset)*1,1,TopOfKitchen-CrateHeight/2+5),  CratePath,CrateWidth,CrateHeight,partition);
        CreateCrate(TomatoCrate,Items.Tomato,   new Vector3(CrateStart + (CrateWidth +  CrateOffset)*2,1,TopOfKitchen-CrateHeight/2+5),CratePath,CrateWidth,CrateHeight,partition);


        CreateCrate(MeatCrate,Items.Mince, new Vector3(CrateStart + (CrateWidth +  CrateOffset)*0,1,5),CratePath,CrateWidth,CrateHeight, partition);
        CreateCrate(CheeseCrate,Items.Cheese,     new Vector3(CrateStart + (CrateWidth +  CrateOffset)*1,1,5),  CratePath,CrateWidth,CrateHeight,partition);
        CreateCrate(BunsCrate,Items.Buns,   new Vector3(CrateStart + (CrateWidth +  CrateOffset)*2,1,5),CratePath,CrateWidth,CrateHeight,partition);

        //  CreateCrate(MeatCrate,Items.Mince,      new Vector3(CrateStart + (CrateWidth +  CrateOffset)*0,1,50),CratePath,CrateWidth,CrateHeight,partition);
        //CreateCrate(CheeseCrate,Items.Cheese,   new Vector3(CrateStart + (CrateWidth +  CrateOffset)*1,1,50),CratePath,CrateWidth,CrateHeight,partition);
      //  CreateCrate(BunsCrate,Items.Buns,       new Vector3(CrateStart + (CrateWidth +  CrateOffset)*2,1,50),CratePath,CrateWidth,CrateHeight,partition);

    }

    void CreateCrate(GameObject crate, Items item,Vector3 position,String texture, int width, int height, GridPartition partition){

        position.x += KitchenFloor.transform.position.x;
        position.z += KitchenFloor.transform.position.z;
        BTexture tex = new BTexture(texture,null,null);
        crate = new GameObject(new Rectangle(position.x,position.z,width,height),tex,width,height);
        crate.transform.position.set(position);
        FoodCrate fCrate = new FoodCrate(item);
        crate.addStaticCollider(partition, occupationID.Station);
        crate.AppendScript(fCrate);
        WorkStations.add(crate);

        fCrate.init();



        InteractableObjects.add(crate);



    }
    float floorToMultipleOfCellWidth(float a, float CellWidth){
        return (float) (Math.ceil(a/ CellWidth) *CellWidth);

    }
    void createTables(GridPartition  partition){
        float width = 1f;
        float offsetX = 105;
        float offsetY = 105;

        TableRadius = TableRadius * width;

        Tables = new LinkedList<>();

        Table1 = new GameObject(new Rectangle( TableGroupPosition.x + DiningFloor.transform.position.x ,TableGroupPosition.z+DiningFloor.transform.position.z, TableRadius,TableRadius),TableTexture, (int)TableRadius,(int)TableRadius);
        Table1.transform.position.x =  floorToMultipleOfCellWidth(TableGroupPosition.x + DiningFloor.transform.position.x, partition.sizeOfGridSpaces);
        Table1.transform.position.z =  floorToMultipleOfCellWidth(TableGroupPosition.z + DiningFloor.transform.position.z, partition.sizeOfGridSpaces);
        Table1.transform.position.y = 2;
        Table1.transform.scale.set(width,0,width);
        Table1.addStaticCollider(partition, occupationID.Blocked);
        Tables.add(Table1);

        Table2 = new GameObject(new Rectangle( width*offsetX+TableGroupPosition.x +  DiningFloor.transform.position.x ,width * offsetY+TableGroupPosition.z+ DiningFloor.transform.position.z, TableRadius,TableRadius),TableTexture, (int)TableRadius,(int)TableRadius);
        Table2.transform.position.x =  floorToMultipleOfCellWidth(width * offsetX+TableGroupPosition.x + DiningFloor.transform.position.x, partition.sizeOfGridSpaces);
        Table2.transform.position.z = floorToMultipleOfCellWidth(width * offsetY+TableGroupPosition.z + DiningFloor.transform.position.z, partition.sizeOfGridSpaces);
        Table2.transform.position.y = 2;
        Table2.transform.scale.set(width,0,width);
        Table2.addStaticCollider(partition, occupationID.Blocked);

        Tables.add(Table2);

        Table3 = new GameObject(new Rectangle( TableGroupPosition.x + DiningFloor.transform.position.x ,width*offsetY+TableGroupPosition.z+DiningFloor.transform.position.z, TableRadius,TableRadius),TableTexture, (int)TableRadius,(int)TableRadius);
        Table3.transform.position.x =  floorToMultipleOfCellWidth(TableGroupPosition.x + DiningFloor.transform.position.x, partition.sizeOfGridSpaces);
        Table3.transform.position.z = floorToMultipleOfCellWidth(width*offsetY+TableGroupPosition.z + DiningFloor.transform.position.z, partition.sizeOfGridSpaces);
        Table3.transform.position.y = 2;
        Table3.transform.scale.set(width,0,width);
        Table3.addStaticCollider(partition, occupationID.Blocked);
        Tables.add(Table3);

        Table4 = new GameObject(new Rectangle( width*offsetX+TableGroupPosition.x+ DiningFloor.transform.position.x ,TableGroupPosition.z+DiningFloor.transform.position.z, TableRadius,TableRadius),TableTexture, (int)TableRadius,(int)TableRadius);
        Table4.transform.position.x = floorToMultipleOfCellWidth( width*offsetX+TableGroupPosition.x + DiningFloor.transform.position.x, partition.sizeOfGridSpaces);
        Table4.transform.position.z = floorToMultipleOfCellWidth(TableGroupPosition.z + DiningFloor.transform.position.z, partition.sizeOfGridSpaces);
        Table4.transform.position.y = 2;
        Table4.transform.scale.set(width,0,width);
        Table4.addStaticCollider(partition, occupationID.Blocked);
        Tables.add(Table4);





        BossTable = new GameObject(new Rectangle(TableGroupPosition.x+ DiningFloor.transform.position.x ,width * offsetY+TableGroupPosition.z+ DiningFloor.transform.position.z, TableRadius,TableRadius),BossTableTexture,190,60);
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
                BossSeats.add(new Vector3(x * seatOffset,0,y * (25+
                        BossTable.getTextureHeight())-25).add(BossTable.transform.position));
            }
        }

        Tables.add(BossTable);

        TableRadius /=2.0f;

    }


    public void Reset(){


        for (GameObject obj: WorkStations
             ) {
            if (obj.blackScripts.get(0) instanceof WorkStation){
                ((WorkStation)obj.blackScripts.get(0)).Reset();
            }
        }


    }
}
