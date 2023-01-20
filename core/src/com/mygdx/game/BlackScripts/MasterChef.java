package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.BTexture;
import com.mygdx.game.BlackCore.BlackScriptManager;
import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.GameObject;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.BlackCore.Pathfinding.PathfindingConfig;
import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;
import com.mygdx.game.Chef;

import java.util.List;

public class MasterChef extends BlackScripts {

    Chef[] chefs;
    public int numberOfChefs = 2;

    private int currentlySelectedChef = 0;
    public GridPartition KitchenPartition;
    public Camera camera;

    public BTexture chefTex;
    private int chefWidth = 20;
    private int chefHeight = 20;

    private Boolean AllowTouch = true; //There will be a mouseMaster class that controls what the mouse clicks,
    // to allow for muting ect and prevent the mouse from forcing movement in all cases
    PathfindingConfig pathfindingConfig;
    @Override
    public void Start() {
        super.Start();
        
        chefs = new Chef[numberOfChefs];

        for (int i = 0; i < numberOfChefs; i++) {

            ChefController controller = new ChefController();
            GameObject obj = new GameObject(new Rectangle(10,10,chefWidth,chefHeight),chefTex);
            obj.transform.gridPartition = KitchenPartition;
            obj.addDynamicCollider();
            obj.AppendScript(controller);

            chefs[i] = new Chef(i, controller);
        }

       chefs[1].controller.getGameObject().transform.position = new Vector3(500,0,10);

        pathfindingConfig = new PathfindingConfig();
        pathfindingConfig.StepCost = 1;
        pathfindingConfig.DiagonalCost = 1;
        pathfindingConfig.PathMutliplier = 1;
        pathfindingConfig.maxIterations = 500;
        pathfindingConfig.DistanceCost  = 1.5f;//1.41421356

    }

    @Override
    public void Update(float dt) {
        super.Update(dt);


        for (int i = 0; i < numberOfChefs; i++) {
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_1 + i)) // increments to next number for each chef 1,2,3 ect (dont go above 9)
                currentlySelectedChef = i;//Chef to select
        }


        if(AllowTouch && Gdx.input.isTouched()){
            Vector3 touchpos = new Vector3();
            touchpos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            touchpos = camera.unproject(touchpos);

            touchpos.z = touchpos.y;
            touchpos.y = 0; //swizzle

            //turn this into grid positions and set a pathfind
            Vector3 pv3 = chefs[currentlySelectedChef].controller.getGameObject().transform.position;
            List<Vector2> paths = KitchenPartition.pathfindFromWorldCoord(pv3.x,pv3.z,touchpos.x,touchpos.z,pathfindingConfig);

            chefs[currentlySelectedChef].controller.updatePath(paths);
        }

        if(Gdx.input.isKeyPressed(InputsDefaults.interact)){ //there should be an interaction masterclass instead
            //try and interact
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
}
