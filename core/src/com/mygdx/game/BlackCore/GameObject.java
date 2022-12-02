package com.mygdx.game.BlackCore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Shape2D;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class GameObject implements Comparator<GameObject> {
    Shape2D shape;
    BTexture texture;
    public Transform tranform;

    List<BlackScripts> blackScripts;
    private Integer _UID;

    Boolean isDestroyed = false;

    Boolean IsActiveAndVisible;


    public GameObject(Shape2D shape, BTexture texture){

    this.shape = shape;
    this.texture = texture;
    tranform = new Transform();
    blackScripts = new LinkedList<>();

    GameObjectHandler.instantiator.Instantiate(this);

    }

    public Integer getUID() {
        return _UID;

    }
/*
    public void assignToPhysics(some data, enum dynamic or static)
    {
        this.physicssettings = somedata;
        physicsmaster.addthis(this,data);

    }
    octree


    */
    public void SetUID(int UID){
        if(_UID != null){
            //THIS SHOULDNT HAPPEN
            throw new IllegalArgumentException("You cant set the UID to be something else, its set for a reason... hopefully");
        }
        _UID = UID;
    }

    protected void runScriptsUpdate(){
        for (BlackScripts script:
             blackScripts) {
            script.Update(Gdx.graphics.getDeltaTime());
        }
    }

    protected void runScriptsFixedUpdate(float fixedDelta){
        for (BlackScripts script:
                blackScripts) {
            script.FixedUpdate(fixedDelta);
        }    }

    public void AppendScript(BlackScripts script){
        blackScripts.add(script);

        script.gameObject = this;
        script.StartUpMethodSequence();
    }


    public void dispose(){
        texture.dispose();

    }



    @Override
    public int compare(GameObject o1, GameObject o2) {
        if(o1.tranform.position.z == o2.tranform.position.z)
            return 0;

        return (o1.tranform.position.z > o2.tranform.position.z) ? -1 : 1;
    }
}
