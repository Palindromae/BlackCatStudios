package com.mygdx.game.BlackCore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Shape2D;
import com.mygdx.game.BlackScripts.CollisionDetection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;


public class GameObject implements Comparator<GameObject> {
    public Shape2D shape;
    BTexture texture;
    public Transform transform;
    public boolean colliderState;

    List<BlackScripts> blackScripts;
    private Integer _UID;

    Boolean isDestroyed = false;
    Boolean IsActiveAndVisible;

    Integer textureWidth;
    Integer textureHeight;

    public GameObject(Shape2D shape, BTexture texture){

        this.shape = shape;
        this.texture = texture;
        transform = new Transform();
        blackScripts = new LinkedList<>();
        IsActiveAndVisible = true;
        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();


        GameObjectHandler.instantiator.Instantiate(this);
    }

    public Integer getUID() {
        return _UID;

    }

    public void addDynamicCollider(){
        setColliderState(true);
        CollisionDetection.collisionMaster.addToDynamicQueue(this);
    }

    public void addStaticCollider(){
        setColliderState(true);
        CollisionDetection.collisionMaster.addToStaticQueue(this);
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

    public Integer getTextureWidth(){
        return textureWidth;
    }

    public Integer getTextureHeight(){
        return textureHeight;
    }


    public void dispose(){
        texture.dispose();

    }

    public Boolean isObjectTouched() { // Method for use of checking if spaces in menus are touched for initiating different buttons/sequences
        if (Gdx.input.isButtonJustPressed(0)) {
            Vector3 touchpos = new Vector3();
            touchpos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            MyGdxGame.camera.unproject(touchpos);
            return ((touchpos.x >= this.transform.position.x) && (touchpos.x <= (this.transform.position.x + this.getTextureWidth())) && (touchpos.y >= this.transform.position.z) && (touchpos.y <= (this.transform.position.z + this.getTextureHeight())));
        }
        return false;
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

    public void negateVisibility() {
        IsActiveAndVisible = !IsActiveAndVisible;
    }
}
