package com.mygdx.game.BlackCore;

public abstract class BlackScripts {

    public Boolean isActive = true;
    protected GameObject gameObject;
    //These are all virtual functions that can be overloaded for convience
    //This should get called every frame
    //There is no telling which Update will be called first but will be called before actual Rendering

    //dt is the frame rate gotten from Gdx.graphics.getDeltaTime()


    protected void StartUpMethodSequence(){
        if(isActive)
            Awake();
        Start();
    }
    //this runs every time the script awakes, guarateed to run before Start in the case they happen at the same time

    public void Awake(){

    }

    //This runs once when the Script is first created
    public void Start(){

    }

    //runs every frame
    public void Update(float dt){

    }

    //This gets called at a guaranteed frame rate (Not sure if this is needed since FPS is capped at 60fps)
    //But Ill probaby implement this for movement to prevent non determantism
    //dt is a fixed time step e.g. 1/60 or 1/30


    public void FixedUpdate(float dt){
        
    };
}
