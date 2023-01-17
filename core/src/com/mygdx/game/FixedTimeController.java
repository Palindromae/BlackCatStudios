package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Date;

public class FixedTimeController {

    double t = 0;
    public final double dt = 1/120.0d; //this is the frame rate of physics could be 60 not sure what it should be rn

    double currentTime;
   public double accumulator;

//from https://gafferongames.com/post/fix_your_timestep/
    public FixedTimeController(){
        currentTime = System.currentTimeMillis();
        accumulator = 0;
    }

    Boolean doTimeStep(){

        double newTime = System.currentTimeMillis();
        double frameTime = newTime - currentTime;
        frameTime/=1000;
        if(frameTime>.25)
            frameTime = .25;
        currentTime = newTime;

        accumulator += Gdx.graphics.getDeltaTime();

        return accumulator>=dt;

    }
}
