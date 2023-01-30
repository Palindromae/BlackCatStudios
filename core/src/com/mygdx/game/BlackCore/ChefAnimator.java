package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.dongbat.jbump.Item;
import com.mygdx.game.BlackScripts.ChefController;
import com.mygdx.game.BlackScripts.FrameIDs;
import com.mygdx.game.BlackScripts.PathfindingAgent;
import com.mygdx.game.CoreData.Items.Classes.ItemBuns;

import java.util.LinkedList;
import java.util.List;

public class ChefAnimator extends CharacterAnimator
{
    ChefController chef;
    List<GameObject> obj = new LinkedList<>();

    /**
     * Creates an animator for a chef, specialsing in its ability to hold objects, inherits all below
     * @param timeToNextFrame
     * @param path
     * @param width
     * @param height
     * @param ImagesPerHeight
     * @param ImagesPerWidth
     * @param agent
     */
    public ChefAnimator(float timeToNextFrame, String path, int width, int height, int ImagesPerHeight, int ImagesPerWidth, ChefController agent) {
        super(timeToNextFrame, path, width, height, ImagesPerHeight, ImagesPerWidth, agent);
        chef = agent;

        for (int i = 0; i < agent.GetMaxStackSize(); i++) {
            BTexture tex = new BTexture(new ItemBuns().getImagePath(), null,null);
            obj.add(new GameObject(new Rectangle(), tex,20,20));
        }
    }


    /**
     * Updates the stack images, and updates them dependin on the stack contents
     */
    public void UpdateStackImages(){
        List<ItemAbs> stack = chef.GetStack();

        for (GameObject s:obj
             ) {
            s.IsActiveAndVisible = false;
        }

        for (int i = 0; i < stack.size(); i++)
        {
            obj.get(i).IsActiveAndVisible = true;
            obj.get(i).UpdateTexture(stack.get(i).getImagePath());

        }





    }

    /**
     * Moves the stack images infront of the player
     */
    public void MoveObjs(){

        float width = gameObject.textureWidth;
        float height = gameObject.textureHeight;

        Vector3 dir = new Vector3(0,0,0);
        switch (Direction){
            case 0:
                dir.z = height/8;

                break;
            case 1:
                dir.x = -width/2;

                break;
            case 2:
                dir.z = -height/4;

                break;
            case 3:
                dir.x = width/8;
                break;
            default:
                break;
        }

        for (int i = 0; i < obj.size(); i++) {
            obj.get(i).transform.position.set(gameObject.transform.position).sub(gameObject.getMaintainedOffset()).add(width/2,0,0).add(dir);
        }


    }
    @Override
    public void Update(float dt)
    {
        super.Update(dt);

        UpdateStackImages();
        MoveObjs();
    }



}
