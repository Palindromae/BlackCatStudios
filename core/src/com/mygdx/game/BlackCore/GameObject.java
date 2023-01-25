package com.mygdx.game.BlackCore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Shape2D;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.BlackCore.Pathfinding.occupationID;
import com.mygdx.game.BlackScripts.CollisionDetection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class GameObject implements Comparator<GameObject> {
    public Shape2D shape;
    BTexture texture;
    public Transform transform;
    public boolean colliderState;

    List<BlackScripts> blackScripts;
    private Integer _UID;

    Boolean isDestroyed = false;
    Boolean IsActiveAndVisible;

    public GameObject(Shape2D shape, BTexture texture){

        this.shape = shape;
        this.texture = texture;
        transform = new Transform();
        blackScripts = new LinkedList<>();

        GameObjectHandler.instantiator.Instantiate(this);
    }

    public Integer getUID() {
        return _UID;

    }

    public void addDynamicCollider(){
        setColliderState(true);
        CollisionDetection.collisionMaster.addToDynamicQueue(this);
    }

    public void addStaticCollider(GridPartition gridPartition, occupationID id){
        this.transform.gridPartition = gridPartition;
        gridPartition.place_static_object_on_grid_from_world(transform.position.x,transform.position.z,texture.width*transform.scale.x, texture.height*transform.scale.z, occupationID.Blocked);
      //  setColliderState(true);
      //  CollisionDetection.collisionMaster.addToStaticQueue(this);

    }

    public void setColliderState(boolean state){
        colliderState = state;
    }

    public boolean getColliderState(){
        return colliderState;
    }

    public void SetUID(int UID){
        if(_UID != null){
            //THIS SHOULDN'T HAPPEN
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

    public void Destroy(){
        isDestroyed = true;
        GameObjectHandler.instantiator.GameObjectsHeld.remove(_UID);
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

    public List<BlackScripts> FindInterfaceScripts(){
        List<BlackScripts> scripts = new LinkedList<>();

        for (BlackScripts script: blackScripts
        ) {
            if(script instanceof InteractInterface)
            {
                scripts.add(script);
            }

        }

        return scripts;

    }



    @Override
    public int compare(GameObject o1, GameObject o2) {

        if(o1.transform.position.y + o1.transform.scale.y == o2.transform.position.y+ o2.transform.scale.y)
        {

            if(o1.transform.position.z == o2.transform.position.z)
                return 0;

            return (o1.transform.position.z > o2.transform.position.z) ? -1 : 1;
        }
        return (o1.transform.position.y + o1.transform.scale.y > o2.transform.position.y+ o2.transform.scale.y) ? 1 : -1;


    }
}
