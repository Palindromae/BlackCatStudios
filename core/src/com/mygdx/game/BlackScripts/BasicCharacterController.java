package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.BlackScripts;

public class BasicCharacterController extends BlackScripts {


    public Camera camera;

    @Override
    public void Awake() {
        super.Awake();
        System.out.println("AAAA");
    }

    @Override
    public void Update(float dt) {
        super.Update(dt);


    }

    public void FixedUpdate(float dt){
        System.out.println("aaha");

        if(Gdx.input.isTouched()){
            Vector3 touchpos = new Vector3();
            touchpos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchpos);
            gameObject.tranform.position.x = touchpos.x - 64/2;


        }



        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) gameObject.tranform.position.x -= 200 * dt;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) gameObject.tranform.position.z += 200 * dt;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) gameObject.tranform.position.z -= 200 * dt;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) gameObject.tranform.position.x += 200 * dt;
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) ;
          gameObject.tranform.rotateAroundAxis(new Vector3(0,1,0),10 * dt);
        if (gameObject.tranform.position.x < 0) gameObject.tranform.position.x = 0;
        if (gameObject.tranform.position.x > 800 -64) gameObject.tranform.position.x = 800 - 64;
    }
}
