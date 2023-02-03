package com.mygdx.game.BlackCore;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackScripts.Animate;
import com.mygdx.game.BlackScripts.FrameIDs;
import com.mygdx.game.BlackScripts.PathfindingAgent;

import java.awt.*;
import java.util.List;

public class CharacterAnimator extends Animate
    {
        PathfindingAgent agent;

    int Direction = 0;

        /**
         * Creates the animator
         * @param timeToNextFrame, how long each frame holds for
         * @param path, path to image
         * @param width
         * @param height
         * @param ImagesPerHeight images sequence length
         * @param ImagesPerWidth max number of animations
         * @param agent what agent is being used to walk
         */
    public CharacterAnimator(float timeToNextFrame, String path, int width, int height, int ImagesPerHeight, int ImagesPerWidth, PathfindingAgent agent) {
        super(timeToNextFrame, path, width, height, ImagesPerHeight, ImagesPerWidth);
 this.agent = agent;
    }

        /**
         * Calculate the move direction for the animations
         */
    void calculateMoveDirection()
    {
        //Assumes animation pattern of 0 top  1 left 2 down 3 right

        //try and find the best fit

        Vector3 nextPosition = new Vector3(agent.getNextPosition());
        Vector3 up = new Vector3(0,0,1);
        Vector3 right=  new Vector3(1,0,0);

        nextPosition.sub(agent.getCurrentPosition());

        nextPosition.nor();

        //Find which ever axis its walking closets to
        float dotUp = up.dot(nextPosition);
        float dotRight = right.dot(nextPosition);

        CurrentTexturesInTextures = 9;

        if(Math.abs(dotUp)>Math.abs(dotRight))
        {
            //Direction is closer to the vertical axis

            if(dotUp>0){
                FrameID = FrameIDs.WalkUp.ordinal();
                Direction = 0;
            }
            else {
                FrameID = FrameIDs.WalkDown.ordinal();
                Direction = 2;
            }
        } else{
            //Direction is closer to horizontal axis
            if(dotRight>0){
                FrameID = FrameIDs.WalkRight.ordinal();
                Direction = 3;
            }
            else{
                FrameID = FrameIDs.WalkLeft.ordinal();
                Direction = 1;
            }
        }
    }

    void StandingStill(){
        CurrentTexturesInTextures = 6;

        switch (Direction){
            case 0:
                SwapFrameID(FrameIDs.WaveUp);
                break;
            case 1:
                SwapFrameID(FrameIDs.WaveLeft);
                break;
            case 2:
                SwapFrameID(FrameIDs.WaveDown);
                break;
            case 3:
                SwapFrameID(FrameIDs.WaveRight);



        }
        keyFrame = 0;

    }

    @Override
    public void Update(float dt){

        if(agent.pathLength()>0)
            calculateMoveDirection();
        else
           StandingStill();

        super.Update(dt);


    }



}
