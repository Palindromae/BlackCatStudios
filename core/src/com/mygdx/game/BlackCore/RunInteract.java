package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackScripts.CollisionDetection;
import com.mygdx.game.Chef;
import com.mygdx.game.CreateGameWorld;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class RunInteract {


    public static RunInteract interact;
    CreateGameWorld gameWorld;

    public Chef[] chefs;

    public RunInteract(CreateGameWorld gameWorld){
        if(interact != null)
            throw new RuntimeException("There shouldnt be more than one interaction handlers in existance");
        this.gameWorld = gameWorld;
        interact = this;
    }

    /**
     * Get the object to interact with
     * @param obj
     * @param interactionDistance
     * @return
     */
    public Optional<GameObject> GetInteractionObj(GameObject obj, float interactionDistance){
        GameObject closetObj = null;
        float distance = interactionDistance+1000;
        float currentInteractionDistance = 0;
        Circle OverlapCircle = new Circle(obj.transform.position.x,obj.transform.position.z,interactionDistance);

      float d1;
        for (GameObject inter : gameWorld.InteractableObjects
             ) {


            if(!CollisionDetection.collider(OverlapCircle, inter.shape))
                continue;

            currentInteractionDistance = inter.transform.position.dst(obj.transform.position);
            if(currentInteractionDistance<distance)
            {
                distance = currentInteractionDistance;
                closetObj = inter;

            }



        }

        return Optional.ofNullable(closetObj);
    }

    /**
     * Cleans the script by a function
     * @param scripts
     * @param operation operation to clean the list
     */
    private void Clean(List<BlackScripts> scripts, Function<InteractInterface,Boolean> operation)
    {
        for (int i = scripts.size()-1; i >=0; i--)
        {

            if(operation.apply((InteractInterface) scripts.get(i)))
                continue;

            scripts.remove(i);

        }

    }

    /**
     * Is the chef close enough to the obj
     * @param obj
     * @param distance
     * @return
     */
    public boolean isChefClose(GameObject obj, float distance){

        for (Chef chef:chefs
             ) {
            if(chef.controller.gameObject.transform.position.dst(obj.transform.position) <=distance)
                return true;
        }
        return false;

    }

    /**
     * Run an interaction check and return a script
     * @param obj
     * @param interactionDistance
     * @param GiveOrGet false is Give and true is get
     * @return BlackScript that is able to be interacted with
     */
    public List<BlackScripts> Interact(GameObject obj, float interactionDistance, boolean GiveOrGet){

        Optional<GameObject> interactObj = GetInteractionObj(obj,interactionDistance);

        if(!interactObj.isPresent())
            return new LinkedList<>();

        List<BlackScripts> scriptsList =  interactObj.get().FindInterfaceScripts();
        //Cleans the list to Interfaces that can interact primary or secondary
        if(GiveOrGet)
            Clean(scriptsList,(InteractInterface inter) -> (inter.TestGetItem()));
        else
            Clean(scriptsList,(InteractInterface inter) -> (inter.TestGiveItem()));

        return scriptsList;

    }
}
