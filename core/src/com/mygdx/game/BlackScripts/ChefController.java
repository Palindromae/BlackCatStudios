package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.ItemAbs;
import com.mygdx.game.BlackCore.RayPoint;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class ChefController extends PathfindingAgent {



    private Stack<ItemAbs> ItemStack = new Stack<>();
    private int maxStackSize = 3;


    public void reset(Vector3 p)
    {
        gameObject.transform.UpdatePosition(p);
        ItemStack.clear();
    }

    public ChefController(){
        UpdateMap = true;
    }

    public List<ItemAbs> GetStack(){
        return new LinkedList<>(ItemStack);
    }


    @Override
    public void Update(float dt) {

    }


    public void AfterFixedUpdate(float dt) {
        move(dt);


    }

    public int GetMaxStackSize(){
        return maxStackSize;
    }

    public boolean canGiveChef(){
        return ItemStack.size()< maxStackSize;
    }

    public boolean canChefGet(){
        return ItemStack.size() > 0;
    }
    public boolean GiveItem(ItemAbs item){
        if (!canGiveChef())
            return false;

        ItemStack.push(item);
        return true;
    }

    public Optional<ItemAbs> GetItem(){

        if(!canChefGet())
            return null;

        return Optional.ofNullable(ItemStack.pop());
    }

    /**
     * Disposes the item last put into the chef's hand
     * @return the item that was disposed
     */
    public Optional<ItemAbs> DisposeItem(){
        if (canGiveChef()){
            return Optional.ofNullable(ItemStack.pop());
        }else{
            return null;
        }
    }




}
