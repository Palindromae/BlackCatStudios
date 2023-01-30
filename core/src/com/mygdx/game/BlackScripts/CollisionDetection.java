package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.math.*;
import com.mygdx.game.BlackCore.GameObject;

import java.lang.reflect.Array;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CollisionDetection {
    public static CollisionDetection collisionMaster;
    List<GameObject> dynamicColliders = new LinkedList<GameObject>();
    List<GameObject> staticColliders = new LinkedList<GameObject>();


    public CollisionDetection(){
        if(collisionMaster == null){
            collisionMaster = this;
        }
    }

    public void collisionTest(){
        System.out.println("Hello");
    }


    public void addToDynamicQueue(GameObject object){
        if (dynamicColliders.contains(object))
            return;
        dynamicColliders.add(object);
    }

    public void addToStaticQueue(GameObject object){
        if (staticColliders.contains(object))
            return;
        staticColliders.add(object);
    }

    /**
     * Sees if there is sa static collision with the current gameobject
     * @param MovedObject
     * @param staticObjects
     * @return
     */
    List<GameObject> staticCollision(GameObject MovedObject, List<GameObject> staticObjects){
        List<GameObject> collisions = new LinkedList<GameObject>();
            Shape2D dynamicShape = MovedObject.shape;
        if (MovedObject.getColliderState() == false) return collisions;
            for (int j = 0; j<staticObjects.size(); j++){
                Shape2D staticShape = staticObjects.get(j).shape;
                if (staticObjects.get(j).getColliderState() == false) continue;
                if(collider(dynamicShape, staticShape)){
                    collisions.add(staticObjects.get(j));
            }
        }
        return collisions;
    }

    /**
     * Sees if there is a collision with a dyniamic object
     * @param dynamicObjects
     * @param toCheck
     * @return list of collided GameObjects
     */

    List<GameObject> dynamicCollision(List<GameObject> dynamicObjects, GameObject toCheck) {
        List<GameObject> collisions = new LinkedList<>();
        for (int i = 0; i < dynamicObjects.size(); i++) {
            if (dynamicObjects.get(i) != toCheck){
                Shape2D mainShape = toCheck.shape;
                Shape2D secondShape = dynamicObjects.get(i).shape;
                if (toCheck.getColliderState() == false) continue;
           //     System.out.println(collider(mainShape, secondShape));
                if(collider(mainShape, secondShape)){
                    collisions.add(dynamicObjects.get(i));

                }
            }
        }
        return collisions;
    }

    /**
     * Runs the collisions allowing for 2d Circle 2d Rectangle
     * @param obj1
     * @param obj2
     * @return
     */

    public static boolean collider(Shape2D obj1,Shape2D obj2){
        if (obj1 instanceof Rectangle && obj2 instanceof Rectangle) return Intersector.overlaps((Rectangle) obj1, (Rectangle)obj2);
        if (obj1 instanceof Rectangle && obj2 instanceof Circle) return Intersector.overlaps((Circle)obj2, (Rectangle) obj1);
        if (obj1 instanceof Circle && obj2 instanceof Rectangle) return Intersector.overlaps((Circle) obj1, (Rectangle)obj2);
        if (obj1 instanceof Circle && obj2 instanceof Circle) return Intersector.overlaps((Circle) obj1, (Circle) obj2);
        return false;
    }
}


