package com.mygdx.game.BlackCore;

public class BlackScriptTest extends BlackScripts {
    String name;
    String lastName;
    //this is demonstrating how to use these functions and implement scripts

    //this runs every time the script awakes, guarateed to run before Start in the case they happen at the same time
    @Override
    public void Awake() {
        super.Awake();
        name = "Clark";
    }

    @Override
    public void Start() {
        super.Start();

        lastName = name +"ington";
    }

    @Override
    public void Update(float dt) {
        super.Update(dt);
    }


    //this
    @Override
    public void FixedUpdate(float dt) {
        super.FixedUpdate(dt);
    }
}
