package com.mygdx.game.BlackCore;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class BatchDrawer {

    public SpriteBatch batch;

    public BatchDrawer(){

        batch = new SpriteBatch();

    }




    public void RenderTextures(Matrix4 combinedMatrix){

        batch.setProjectionMatrix(combinedMatrix);
        batch.begin();

        for (GameObject object:GameObjectHandler.instantiator.GameObjectsHeld.values()){

            //make gamescale with window width - look at sams code

            batch.draw(object.texture.texture, object.tranform.position.x, object.tranform.position.z,
                    object.tranform.position.x + object.texture.textureOrigin.x, object.tranform.position.z + object.texture.textureOrigin.z,
                    object.texture.width, object.texture.height,
                    object.tranform.scale.x, object.tranform.scale.z, object.tranform.rotation.getYaw());

            System.out.println(object.tranform.rotation);

        }

        batch.end();

    }

    public void dispose(){
        batch.dispose();


    }
}
