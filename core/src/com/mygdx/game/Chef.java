package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.BlackCore.GameObject;
import com.mygdx.game.BlackScripts.ChefController;

public class Chef {

    public ChefController controller;
    private int assignedIndex;



    public Chef(int assignedIndex, ChefController obj){
        this.assignedIndex = assignedIndex;
        controller = obj;

    }
}
