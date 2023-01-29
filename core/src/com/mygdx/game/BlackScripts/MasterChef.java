package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackCore.Pathfinding.DistanceCalculator;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.BlackCore.Pathfinding.PathfindingConfig;
import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;
import com.mygdx.game.Chef;
import com.mygdx.game.CoreData.Items.FoodCrate;
import com.mygdx.game.CoreData.Items.WorkStation;
import com.mygdx.game.SoundFrame;

import java.util.*;

public class MasterChef extends BlackScripts {

    Chef[] chefs;
    public int numberOfChefs = 2;

    private int currentlySelectedChef = 0;
    public GridPartition KitchenPartition;
    public Camera camera;

    public BTexture chefTex;
    private int chefWidth = 20;
    private int chefHeight = 20;

    Vector3[] spawns;

    float chefInteractionDistance = 45;

    public Boolean AllowTouch = true; //There will be a mouseMaster class that controls what the mouse clicks,
    // to allow for muting ect and prevent the mouse from forcing movement in all cases
    PathfindingConfig pathfindingConfig;


    String[] CharacterSheets = new String[]{
        "Characters/ChefMaleFull.png", "Characters/ChefFemFull.png"
    };
    String[] CharacterSheetsSelected = new String[]{
            "Characters/ChefMaleFullSelected.png", "Characters/ChefFemFullSelected.png"
    };

    List<BTexture> charSheets = new LinkedList<>();
    List<BTexture> charSheetsSelected = new LinkedList<>();

    public MasterChef(Vector3... spawns){
    this.spawns = spawns;
    }
    @Override
    public void Start() {
        super.Start();
        
        chefs = new Chef[numberOfChefs];

        BTexture chefFem = new BTexture("Characters/ChefFemTest.png",null,null);
        List<FrameIDs> frameIDsList = new LinkedList<>();


        for (int i = 0; i < CharacterSheets.length; i++) {
            charSheets.add( new BTexture(CharacterSheets[i],null,null));
            charSheetsSelected.add( new BTexture(CharacterSheetsSelected[i],null,null));

        }

        for (int i = 0; i < numberOfChefs; i++) {


            Collections.reverse(frameIDsList);

            ChefController controller = new ChefController();
            Animate Animator = new ChefAnimator(.125f,CharacterSheets[i], chefWidth,chefHeight,21,13,controller);
            GameObject obj = new GameObject(new Rectangle(0,0,chefWidth,chefHeight),Animator.tex,chefWidth*3,chefHeight*3);
            obj.setMaintainedOffset(16,0);
           // Animator.tex.textureOrigin = new Vector3(64,0,0);
            obj.transform.gridPartition = KitchenPartition;
            obj.transform.position.y = 2;
            obj.addDynamicCollider();
            obj.AppendScript(controller);
            obj.AppendScript(Animator);

            chefs[i] = new Chef(i, controller);

            chefs[i].controller.getGameObject().transform.position = new Vector3(spawns[i]);

        }


        SelectChef(0);
        RunInteract.interact.chefs = chefs;

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
                 {
                     SelectChef(i);

                 }


        }




    if(AllowTouch)
     TouchAllowedCurrently();

    AllowTouch = true;
    }

    void SelectChef(int i){
        ((CharacterAnimator)chefs[i].controller.getGameObject().blackScripts.get(1)).setTex( charSheetsSelected.get(i));;
        ((CharacterAnimator)chefs[i].controller.getGameObject().blackScripts.get(1)).SwapTextureToNewFrame();

        for (int k = 0; k < numberOfChefs; k++) {

            if(k==i)
                continue;
            ((CharacterAnimator)chefs[k].controller.getGameObject().blackScripts.get(1)).setTex(charSheets.get(k));

        }
        currentlySelectedChef = i;//Chef to select
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
        if (Gdx.input.isKeyJustPressed(InputsDefaults.disposeHeldItem)) {
            DisposeItem();
        }

    }


    InteractInterface getInterface(boolean GiveOrGet){
        List<BlackScripts> scripts =  RunInteract.interact.Interact(getCurrentChef().controller.getGameObject(),chefInteractionDistance,GiveOrGet);
        CleanForKitchenObjects(scripts);

        //is anything nearby that can be interacted with that can give and take items
        if(scripts.size()==0)
            return null;

        float distance = 100000;
        float d1 = 0;
        int ii = 0;
        for (int i = 0; i < scripts.size(); i++)
        {
            d1 = scripts.get(i).getGameObject().transform.position.dst(getCurrentChef().controller.getGameObject().transform.position);

            if(d1 < distance){
                distance = d1;
                ii= i;
            }
        }

        return (InteractInterface)scripts.get(ii);
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

        //If the chef isnt holding anything return false, shouldnt as check already done but just in case
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

    /**
     * Disposes of the item the chef is holding
     * @return
     */
    boolean DisposeItem(){
        InteractInterface inter = getInterface(false);
        Optional<ItemAbs> item = getCurrentChef().controller.GetItem();
        //If the chef isnt holding anything return false, shouldnt as check already done but just in case
        if(Objects.isNull(item)||!item.isPresent()){
            return false;
        }else{
            System.out.println("Trying to dispose");
            return true;
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
