package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.BTexture;
import com.mygdx.game.BlackCore.GameObject;

public class CreateGameWorld
{
    int col_redct = 5;
    GameObject KitchenFloor;
    GameObject DiningFloor;
    GameObject ServingCounter;
    GameObject ChoppingBoard;
    GameObject Stove1;
    GameObject Stove2;
    GameObject CombinationCounter;
    GameObject KitchenSouthWall;

    GameObject Table1;
    GameObject Table2;
    GameObject Table3;
    GameObject Table4;
    GameObject BossTable;

    GameObject Door;
    GameObject LightBeam;


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


    public void Instantiate(){
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

        TableTexture = new BTexture("table.png",49,49);
        TableTexture.setWrap(Texture.TextureWrap.ClampToEdge,Texture.TextureWrap.ClampToEdge);

        DoorTexture = new BTexture("door.png",49,49);
        DoorTexture.setWrap(Texture.TextureWrap.ClampToEdge);

        LightBeamTexture = new BTexture("lightBeam.png",101,112);
        LightBeamTexture.setWrap(Texture.TextureWrap.ClampToEdge);

        StoveTexture = new BTexture("purple.png",50,50);
        StoveTexture.setWrap(Texture.TextureWrap.Repeat);

        ChoppingBoardTexture  = new BTexture("cyan.png",50,50);
        ChoppingBoardTexture.setWrap(Texture.TextureWrap.Repeat);



        CombinationCounterTexture = new BTexture("black.png",72,200);
        CombinationCounterTexture.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);


        KitchenFloor = new GameObject(new Rectangle(400,20,350,300), FloorTextureK);
        KitchenFloor.transform.position.x = 430;
        KitchenFloor.transform.position.z = 20;
        KitchenFloor.transform.position.y = -1;

        DiningFloor = new GameObject(new Rectangle(50,20,330,300), FloorTextureD);
        DiningFloor.transform.position.x = 50;
        DiningFloor.transform.position.z = 20;
        DiningFloor.transform.position.y = -1;

        ServingCounter = new GameObject(new Rectangle(KitchenFloor.transform.position.x-50+col_redct,KitchenFloor.transform.position.z+100-col_redct,50-col_redct*2,100-col_redct*2), ServingCounterTexture);
        ServingCounter.transform.position.x = KitchenFloor.transform.position.x-50;
        ServingCounter.transform.position.z = KitchenFloor.transform.position.z+100;
        ServingCounter.transform.position.y = 1;
        ServingCounter.addStaticCollider();


        CombinationCounter = new GameObject(new Rectangle( 150+ KitchenFloor.transform.position.x ,60+KitchenFloor.transform.position.z, 72,200),CombinationCounterTexture);
        CombinationCounter.transform.position.x =  150 + KitchenFloor.transform.position.x;
        CombinationCounter.transform.position.z = 60 + KitchenFloor.transform.position.z;
        CombinationCounter.transform.position.y = 1;
        CombinationCounter.addStaticCollider();


        Stove1 = new GameObject(new Rectangle( 300+ KitchenFloor.transform.position.x ,225+KitchenFloor.transform.position.z, 50,50),StoveTexture);
        Stove1.transform.position.x = 300 + KitchenFloor.transform.position.x;
        Stove1.transform.position.z = 225 + KitchenFloor.transform.position.z;
        Stove1.transform.position.y = 2;
        Stove1.transform.scale.set(1,0,1);
        Stove1.addStaticCollider();

        ChoppingBoard = new GameObject(new Rectangle( 300+ KitchenFloor.transform.position.x ,150+KitchenFloor.transform.position.z, 50,50),ChoppingBoardTexture);
        ChoppingBoard.transform.position.x = 300 + KitchenFloor.transform.position.x;
        ChoppingBoard.transform.position.z = 150 + KitchenFloor.transform.position.z;
        ChoppingBoard.transform.position.y = 2;
        ChoppingBoard.transform.scale.set(1,0,1);
        ChoppingBoard.addStaticCollider();

        Stove2 = new GameObject(new Rectangle( 300+ KitchenFloor.transform.position.x ,75+KitchenFloor.transform.position.z, 50,50),StoveTexture);
        Stove2.transform.position.x = 300 + KitchenFloor.transform.position.x;
        Stove2.transform.position.z = 75 + KitchenFloor.transform.position.z;
        Stove2.transform.position.y = 2;
        Stove2.transform.scale.set(1,0,1);
        Stove2.addStaticCollider();




        createTables();

        Door = new GameObject(new Rectangle(50,20,330,300), DoorTexture);
        Door.transform.position.x = 0+DiningFloor.transform.position.x;
        Door.transform.position.z = 250+DiningFloor.transform.position.z;
        Door.transform.position.y = 1;


        LightBeam = new GameObject(new Rectangle(50,20,330,300), LightBeamTexture);
        LightBeam.transform.position.x = 0+DiningFloor.transform.position.x;
        LightBeam.transform.position.z = 252+DiningFloor.transform.position.z;
        LightBeam.transform.position.y = 0;
        LightBeam.transform.scale.set(.8f,1,.8f);

    }

    void createTables(){
        float width = 1.5f;
        float offsetX = 80;
        float offsetY = 70;
        Table1 = new GameObject(new Rectangle( TableGroupPosition.x + DiningFloor.transform.position.x ,TableGroupPosition.z+DiningFloor.transform.position.z, 49,49),TableTexture);
        Table1.transform.position.x =  TableGroupPosition.x + DiningFloor.transform.position.x;
        Table1.transform.position.z =  TableGroupPosition.z + DiningFloor.transform.position.z;
        Table1.transform.position.y = 2;
        Table1.transform.scale.set(width,0,width*6.5f/8.0f);


        Table2 = new GameObject(new Rectangle( width*offsetX+TableGroupPosition.x +  DiningFloor.transform.position.x ,width * offsetY+TableGroupPosition.z+ DiningFloor.transform.position.z, 49,49),TableTexture);
        Table2.transform.position.x =  width * offsetX+TableGroupPosition.x + DiningFloor.transform.position.x;
        Table2.transform.position.z = width * offsetY+TableGroupPosition.z + DiningFloor.transform.position.z;
        Table2.transform.position.y = 2;
        Table2.transform.scale.set(width,0,width*6.5f/8.0f);

        Table3 = new GameObject(new Rectangle( TableGroupPosition.x + DiningFloor.transform.position.x ,TableGroupPosition.z+DiningFloor.transform.position.z, 49,49),TableTexture);
        Table3.transform.position.x =  TableGroupPosition.x + DiningFloor.transform.position.x;
        Table3.transform.position.z = width*offsetY+TableGroupPosition.z + DiningFloor.transform.position.z;
        Table3.transform.position.y = 2;
        Table3.transform.scale.set(width,0,width*6.5f/8.0f);

        Table4 = new GameObject(new Rectangle( width*offsetX+TableGroupPosition.x+ DiningFloor.transform.position.x ,TableGroupPosition.z+DiningFloor.transform.position.z, 49,49),TableTexture);
        Table4.transform.position.x =  width*offsetX+TableGroupPosition.x + DiningFloor.transform.position.x;
        Table4.transform.position.z = TableGroupPosition.z + DiningFloor.transform.position.z;
        Table4.transform.position.y = 2;
        Table4.transform.scale.set(width,0,width*6.5f/8.0f);


        BossTable = new GameObject(new Rectangle(TableGroupPosition.x+ DiningFloor.transform.position.x ,width * offsetY+TableGroupPosition.z+ DiningFloor.transform.position.z, 49,49),BossTableTexture);
        BossTable.transform.position.x =  70 + DiningFloor.transform.position.x;
        BossTable.transform.position.z =  30 +DiningFloor.transform.position.z;
        BossTable.transform.position.y = 2;
        BossTable.transform.scale.set(1,0,1);

    }
}
