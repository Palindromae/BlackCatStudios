package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import java.util.*;

public class BatchDrawer {

    public SpriteBatch batch;

    List<GameObject> ZOrderedObjects;

    public BatchDrawer(){

        batch = new SpriteBatch();
        ZOrderedObjects = new LinkedList<>();

    }




    public void RenderTextures(Matrix4 combinedMatrix){

        batch.setProjectionMatrix(combinedMatrix);
        batch.begin();
        List<GameObject> GameObjectsToRemove = new LinkedList<>();


        Collections.sort(ZOrderedObjects, new Comparator<GameObject>() {
                    @Override
                    public int compare(GameObject o1, GameObject o2) {
                        return o1.compare(o1,o2);
                    }
                });


        for (GameObject object:ZOrderedObjects){

            if(object.isDestroyed) {
                GameObjectsToRemove.add((object));
                continue;
            }
            //make gamescale with window width - look at sams code



            batch.draw(object.texture.texture, object.tranform.position.x, object.tranform.position.z,
                    object.tranform.position.x + object.texture.textureOrigin.x, object.tranform.position.z + object.texture.textureOrigin.z,
                    object.texture.width, object.texture.height,
                    object.tranform.scale.x, object.tranform.scale.z, object.tranform.rotation.getYaw());

            System.out.println(object.tranform.rotation);

        }

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
