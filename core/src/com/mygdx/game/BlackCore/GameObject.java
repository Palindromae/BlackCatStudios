package com.mygdx.game.BlackCore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;
import com.mygdx.game.BlackCore.Pathfinding.occupationID;
import com.mygdx.game.BlackScripts.CollisionDetection;
import com.mygdx.game.MyGdxGame;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import com.badlogic.gdx.math.Vector3;


public class GameObject implements Comparator<GameObject> {
    public Shape2D shape;
    BTexture texture;
    public Transform transform;
    public boolean colliderState;

   public List<BlackScripts> blackScripts;
    private Integer _UID;
    public boolean InvisPressAllowed = false;

    Boolean isDestroyed = false;
    public Boolean IsActiveAndVisible;
    Integer textureWidth;
    Integer textureHeight;


    private Vector3 MaintainedOffset;


    public void setMaintainedOffset(int x, int z){
     MaintainedOffset = new Vector3(x,0,z);
    }

    /**
     * Create the GameObject with only shape and texture
     * @param shape what shape the hit box
     * @param texture What image represents the GameObject
     */
    public GameObject(Shape2D shape, BTexture texture){

        this.shape = shape;


        setMaintainedOffset(0,0);
        this.texture = texture;
        transform = new Transform();
        blackScripts = new LinkedList<>();
        // set to true by default so that objects are correctly displayed
        IsActiveAndVisible = true;
        textureWidth = texture.getWidth();
        textureHeight = texture.getHeight();
        GameObjectHandler.instantiator.Instantiate(this);
    }

    /**
     * Create gameObject but with a specific width and height
     * @param shape
     * @param texture
     * @param width
     * @param height
     */
    public GameObject(Shape2D shape, BTexture texture, int width, int height){

        setMaintainedOffset(0,0);
        this.shape = shape;
        this.texture = texture;
        transform = new Transform();
        blackScripts = new LinkedList<>();
        // set to true by default so that objects are correctly displayed
        IsActiveAndVisible = true;
        textureWidth = width;
        textureHeight = height;
        GameObjectHandler.instantiator.Instantiate(this);
    }

    /**
     * Returns the UID
     * @return UID as int
     */
    public Integer getUID() {
        return _UID;

    }

    /**
     * adds a dynamic collider allowing for physics
     */
    public void addDynamicCollider(){
        setColliderState(true);
        CollisionDetection.collisionMaster.addToDynamicQueue(this);
    }

    /**
     * Adds a static collider doesnt use physics right now just adds pathfinding
     * @param gridPartition pathfinding grid
     * @param id what to set cells
     */
    public void addStaticCollider(GridPartition gridPartition, occupationID id){
        this.transform.gridPartition = gridPartition;
        gridPartition.place_static_object_on_grid_from_world(transform.position.x,transform.position.z,getTextureWidth()*transform.scale.x, getTextureHeight()*transform.scale.z, occupationID.Blocked);
       // setColliderState(true);
        //CollisionDetection.collisionMaster.addToStaticQueue(this);

    }

    /**
     * Sets the state of the collider
     * @param state
     */
    public void setColliderState(boolean state){
        colliderState = state;
    }

    public boolean getColliderState(){
        return colliderState;
    }

    /**
     * Sets to something, once and once only DO NOT CALL THIS FUNCTION
     * @param UID
     */
    public void SetUID(int UID){
        if(_UID != null){
            //THIS SHOULDN'T HAPPEN
            throw new IllegalArgumentException("You cant set the UID to be something else, its set for a reason... hopefully");
        }
        _UID = UID;
    }

    /**
     * Runs the update script
     */
    protected void runScriptsUpdate(){
        for (BlackScripts script:
                blackScripts) {
            script.Update(Gdx.graphics.getDeltaTime());
        }
    }

    /**
     * Destroys the gameObject and removes it from the handler
     */
    public void Destroy(){
        isDestroyed = true;
        GameObjectHandler.instantiator.GameObjectsHeld.remove(_UID);
    }

    /**
     * Runs fixed update
     * @param fixedDelta delta time of fixed update
     */
    protected void runScriptsFixedUpdate(float fixedDelta){
        for (BlackScripts script:
                blackScripts) {
            script.FixedUpdate(fixedDelta);
        }    }

    /**
     * Appends script to GameObject to Execute
     * @param script
     */
    public void AppendScript(BlackScripts script){
        blackScripts.add(script);

        script.gameObject = this;
        script.StartUpMethodSequence();
    }

    /**
     * This method gets the width of the GameObject's texture
     * @return the width of the texture in pixels
     */
    public Integer getTextureWidth(){
        return textureWidth;
    }

    /**
     * This method gets the height of the GameObject's texture
     * @return the height of the texture in pixels
     */
    public Integer getTextureHeight(){
        return textureHeight;
    }

    /**
     * This method sets the texture of the GameObject
     * @param texture the texture to be set
     */
    public void setTexture(BTexture texture){
        this.texture = texture;
    }

    public void dispose(){
        texture.dispose();

    }


    /**
     * This method is used to check if the object is clicked by the user.
     * It does this by checking if the mouse is clicked and if the mouse is within the bounds of the object
     * @return true if the object is clicked, false otherwise
     */
    public Boolean isObjectTouched() { // Method for use of checking if spaces in menus are touched for initiating different buttons/sequences
        if (Gdx.input.isButtonJustPressed(0)) {
            Vector3 touchpos = new Vector3();
            touchpos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            MyGdxGame.camera.unproject(touchpos);
            return ((touchpos.x >= this.transform.position.x) && (touchpos.x <= (this.transform.position.x + this.getTextureWidth())) &&
                    (touchpos.y >= this.transform.position.z) && (touchpos.y <= (this.transform.position.z + this.getTextureHeight()))) && (IsActiveAndVisible || InvisPressAllowed);
        }
        return false;
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

    /**
     * updates the texture from path, this is slow
     * @param path
     */
    public void UpdateTexture(String path){

        texture.loadTexture(path,null,null,1,1);
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

    /**
     * Flips the visibility of the object
     */
    public void negateVisibility(){
        IsActiveAndVisible = !IsActiveAndVisible;
    }

    /**
     * Gets the visibility of the object
     * @return true if the object is visible, false otherwise
     */
    public Boolean getVisibility(){
        return  IsActiveAndVisible;
    }

    public Vector3 getMaintainedOffset() {
        return MaintainedOffset;
    }
}
