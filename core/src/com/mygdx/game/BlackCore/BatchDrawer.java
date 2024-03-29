package com.mygdx.game.BlackCore;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import java.util.*;

public class BatchDrawer {

    public static SpriteBatch batch;

    List<GameObject> ZOrderedObjects;


    //Moves objects as if they were moving "up"
    public static float yAxisToZConversionRation = .02f;

    public BatchDrawer(){

        batch = new SpriteBatch();
        ZOrderedObjects = new LinkedList<>();

    }




    public void RenderTextures(Matrix4 combinedMatrix){

        //Begin Draw
        batch.setProjectionMatrix(combinedMatrix);
        batch.begin();
        batch.enableBlending();


        List<GameObject> GameObjectsToRemove = new LinkedList<>();

        //Reorder GameObjects so they are smashed on the screen correcly
        Collections.sort(ZOrderedObjects, new Comparator<GameObject>() {
                    @Override
                    public int compare(GameObject o1, GameObject o2) {
                        return o1.compare(o1,o2);
                    }
                });


        for (GameObject object:ZOrderedObjects){

            //Remove objects that are destroyed from render sequence, allows gc to function
            if(object.isDestroyed) {
                GameObjectsToRemove.add(object);
                continue;
            }
            //make gamescale with window width - look at sams code

            if (!object.IsActiveAndVisible) {
                continue; // Will not draw an object if it is set to be invisible
            }
            //This applies the sprite to the screen given some data
            //Y axis is transformed to z axis TODO think about adding a drop shadow to objects
            batch.draw(object.texture.texture,
                    object.transform.position.x - object.getMaintainedOffset().x, object.transform.position.z - object.getMaintainedOffset().z + object.transform.position.y * yAxisToZConversionRation,
                     object.texture.textureOrigin.x,  object.texture.textureOrigin.z,
                    object.textureWidth, object.textureHeight,
                    object.transform.scale.x, object.transform.scale.z+ object.transform.scale.y * yAxisToZConversionRation,
                    object.transform.rotation.getYaw());


        }
        batch.disableBlending();
        batch.end();

        for (GameObject obj:GameObjectsToRemove
             ) {
            ZOrderedObjects.remove(obj);
        }

    }

    public void AddGameObjectToRenderQueue(GameObject obj){
        ZOrderedObjects.add(obj);
    }


    public void dispose(){
        batch.dispose();


    }
}
