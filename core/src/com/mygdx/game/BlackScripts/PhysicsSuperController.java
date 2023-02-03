package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.GameObject;

import java.util.List;

public class PhysicsSuperController extends BlackScripts {
    @Override
    public void FixedUpdate(float dt){

        //Updates positions
        for(int i = 0; i < CollisionDetection.collisionMaster.dynamicColliders.size(); i++){
            GameObject CheckShape = CollisionDetection.collisionMaster.dynamicColliders.get(i);
            if (CheckShape.shape instanceof Rectangle){
                ((Rectangle)CheckShape.shape).setPosition(CheckShape.transform.position.x + CheckShape.getMaintainedOffset().x, CheckShape.transform.position.z+ CheckShape.getMaintainedOffset().z);
            }

        }
        //Runs Dynamic collisions check and static

        for (int i=0; i< CollisionDetection.collisionMaster.dynamicColliders.size(); i++){
           if (!CollisionDetection.collisionMaster.dynamicColliders.get(i).transform.hasMoved()) continue; //Finds the object that's moved, continues if it hasn't
            List<GameObject> DynamicCollisionList = CollisionDetection.collisionMaster.dynamicCollision(CollisionDetection.collisionMaster.dynamicColliders, CollisionDetection.collisionMaster.dynamicColliders.get(i)); // Checks if a collision has occurred
            List<GameObject> StaticCollisionList = CollisionDetection.collisionMaster.staticCollision(CollisionDetection.collisionMaster.dynamicColliders.get(i), CollisionDetection.collisionMaster.staticColliders);
            if(DynamicCollisionList.isEmpty() && StaticCollisionList.isEmpty()){
                continue;
            } //If there's no collision, end the function
            CollisionDetection.collisionMaster.dynamicColliders.get(i).transform.moveToPast();// Moves to past position
        }

        //Updates all past positions

        UpdateDynamicPastPositions();

    }

    public void UpdateDynamicPastPositions()
    {
        for (GameObject obj: CollisionDetection.collisionMaster.dynamicColliders
             ) {
            obj.transform.updatePastPos();
        }
    }
}
