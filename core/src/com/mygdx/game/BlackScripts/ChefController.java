package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.BlackCore.BlackScripts;

import java.util.List;

public class ChefController extends BlackScripts {

    private List<Vector2> currentPath;


    public void updatePath(List<Vector2> path){
        currentPath = path;
    }
    @Override
    public void Update(float dt) {
        super.Update(dt);
    }

    @Override
    public void FixedUpdate(float dt) {
        super.FixedUpdate(dt);

        move();
    }

    void move(){

    }
}
