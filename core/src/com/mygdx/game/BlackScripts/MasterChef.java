package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackCore.Pathfinding.DistanceCalculator;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.BlackCore.Pathfinding.PathfindingConfig;
import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;
import com.mygdx.game.Chef;
import com.mygdx.game.CoreData.Items.FoodCrate;
import com.mygdx.game.CoreData.Items.WorkStation;
import com.mygdx.game.SoundFrame;

import java.util.List;
import java.util.Optional;

public class MasterChef extends BlackScripts {

    Chef[] chefs;
    public int numberOfChefs = 2;

    private int currentlySelectedChef = 0;
    public GridPartition KitchenPartition;
    public Camera camera;

    public BTexture chefTex;
    private int chefWidth = 20;
    private int chefHeight = 20;

    float chefInteractionDistance = 45;

    private Boolean AllowTouch = true; //There will be a mouseMaster class that controls what the mouse clicks,
    // to allow for muting ect and prevent the mouse from forcing movement in all cases
    PathfindingConfig pathfindingConfig;



    @Override
    public void Start() {
        super.Start();
        
        chefs = new Chef[numberOfChefs];

        for (int i = 0; i < numberOfChefs; i++) {

            ChefController controller = new ChefController();
            GameObject obj = new GameObject(new Rectangle(0,0,chefWidth,chefHeight),chefTex);
            obj.transform.gridPartition = KitchenPartition;
            obj.addDynamicCollider();
            obj.AppendScript(controller);

            chefs[i] = new Chef(i, controller);
        }

        RunInteract.interact.chefs = chefs;

       chefs[1].controller.getGameObject().transform.position = new Vector3(460,0,10);
        ((com.badlogic.gdx.math.Rectangle) chefs[1].controller.getGameObject().shape).x = 460;
        ((com.badlogic.gdx.math.Rectangle) chefs[1].controller.getGameObject().shape).y = 10;

        pathfindingConfig = new PathfindingConfig();
        pathfindingConfig.StepCost = 1;
        pathfindingConfig.DiagonalCost = 1;
        pathfindingConfig.PathMutliplier = 1;
        pathfindingConfig.maxIterations = 500;
        pathfindingConfig.DistanceCost  = 1.5f;//1.41421356

    }

    Chef getCurrentChef(){
        return chefs[currentlySelectedChef];
    }
    @Override
    public void Update(float dt) {
        super.Update(dt);


        for (int i = 0; i < numberOfChefs; i++) {
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_1 + i)) // increments to next number for each chef 1,2,3 ect (dont go above 9)
                currentlySelectedChef = i;//Chef to select
        }




    if(AllowTouch)
     TouchAllowedCurrently();


    }

    void TouchAllowedCurrently(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.B))
            getCurrentChef().controller.getGameObject().transform.TellItsGridPos();

        if( Gdx.input.isButtonJustPressed(0)){
            Vector3 touchpos = new Vector3();
            touchpos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            touchpos = camera.unproject(touchpos);

            touchpos.z = touchpos.y;
            touchpos.y = 0; //swizzle

            //turn this into grid positions and set a pathfind
            Vector3 pv3 = chefs[currentlySelectedChef].controller.getGameObject().transform.position;
            List<Vector2> paths = KitchenPartition.pathfindFromWorldCoord(pv3.x,pv3.z,touchpos.x,touchpos.z,pathfindingConfig, DistanceCalculator.Euler);

            chefs[currentlySelectedChef].controller.updatePath(paths);
        }

        if(Gdx.input.isKeyJustPressed(InputsDefaults.interactGet)){

            //try and interact
            GetItem();

        }
        if(Gdx.input.isKeyJustPressed(InputsDefaults.interactGive)){

            //try and interact

           GiveItem();


        }

    }


    InteractInterface getInterface(boolean GiveOrGet){
        List<BlackScripts> scripts =  RunInteract.interact.Interact(getCurrentChef().controller.getGameObject(),chefInteractionDistance,GiveOrGet);
        CleanForKitchenObjects(scripts);

        //is anything nearby that can be interacted with that can give and take items
        if(scripts.size()==0)
            return null;

        return (InteractInterface)scripts.get(0);
    }
    boolean GetItem(){
        System.out.println("Trying to Get");
        if(!getCurrentChef().controller.canGiveChef())//can give item to chef
            return false;


        InteractInterface inter = getInterface(true);

        if(inter == null)
            return false;

        ItemAbs item = inter.GetItem();
        if(item != null){
            //Interaction succeeded
            getCurrentChef().controller.GiveItem(item); //Put item on top of
            System.out.println("Succeded on Getting");
            SoundFrame.SoundEngine.playSound("Item Equip");
            return true;
        } else{
            //Failed
            //Nothing needs to be done
            return  false;
        }
    }

    boolean GiveItem(){
        System.out.println("Trying to give");
        if(!getCurrentChef().controller.canChefGet())
            return false;


        InteractInterface inter = getInterface(false);

        if(inter == null)
            return false;

        Optional<ItemAbs> item = getCurrentChef().controller.GetItem();

        //If the chef isnt holding anything return, shouldnt as check already done but just in case
        if(!item.isPresent())
            return false;

        if(inter.GiveItem(item.get())){
            //Interaction succeeded
            //Nothing else needed to be done
            System.out.println("Succeeded in giving");
            SoundFrame.SoundEngine.playSound("Item Drop");
            return true;
        } else{
            //Failed
            getCurrentChef().controller.GiveItem(item.get()); //Give item back to stack
            return  false;
        }



    }
    void CleanForKitchenObjects(List<BlackScripts> scripts)
    {
        BlackScripts a;
        for (int i = scripts.size()-1; i >= 0; i--)
        {
            a = scripts.get(i);
            if(a instanceof FoodCrate)
                continue;
            if(a instanceof WorkStation)
                continue;

            scripts.remove(i);

        }

    }



    @Override
    public void FixedUpdate(float dt){



        //force a specific update order on the chef and masterchef class
        for (Chef chef: chefs
             ) {
            chef.controller.AfterFixedUpdate(dt);
        }
    }

    public void setTouch(){
        AllowTouch = true;
    }


    public void ResetSequence(Vector3 c1p, Vector3 c2p){
        chefs[0].controller.reset(c1p);
        chefs[1].controller.reset(c2p);

    }
}
