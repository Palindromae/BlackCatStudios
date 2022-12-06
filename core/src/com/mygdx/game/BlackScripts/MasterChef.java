package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.BlackCore.Pathfinding.PathfindingConfig;
import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;
import com.mygdx.game.Chef;

import java.util.List;

public class MasterChef extends BlackScripts {

    Chef[] chefs;
    public int numberOfChefs = 2;

    private int currentlySelectedChef;
    public GridPartition KitchenPartition;
    public Camera camera;


    private Boolean AllowTouch; //There will be a mouseMaster class that controls what the mouse clicks,
    // to allow for muting ect and prevent the mouse from forcing movement in all cases
    PathfindingConfig pathfindingConfig;
    @Override
    public void Start() {
        super.Start();
        
        chefs = new Chef[numberOfChefs];

        for (int i = 0; i < numberOfChefs; i++) {
            chefs[i] = new Chef(i);
        }

        PathfindingConfig config = new PathfindingConfig();
        config.DiagonalCost = 1;
        config.StepCost = 1;
        config.DiagonalCost = 1;
        config.PathMutliplier = 1;
        config.maxIterations = 200;
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
            camera.unproject(touchpos);

            //turn this into grid positions and set a pathfind
            Vector3 pv3 = chefs[currentlySelectedChef].controller.getGameObject().tranform.position;
            List<Vector2> paths = KitchenPartition.pathfindFromWorldCoord(pv3.x,pv3.z,touchpos.x,touchpos.z,pathfindingConfig);

            chefs[currentlySelectedChef].controller.updatePath(paths);
        }
        AllowTouch = false;

        if(Gdx.input.isKeyPressed(InputsDefaults.interact)){ //there should be an interaction masterclass instead
            //try and interact
        }


    }

    public void setTouch(){
        AllowTouch = true;
    }
}
