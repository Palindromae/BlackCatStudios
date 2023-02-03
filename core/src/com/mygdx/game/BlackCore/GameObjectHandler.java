package com.mygdx.game.BlackCore;


import java.util.HashMap;
import java.util.Random;

public class GameObjectHandler {

   Random random;

   public static GameObjectHandler instantiator;

   HashMap<Integer,GameObject> GameObjectsHeld;

   BatchDrawer drawer;

   public GameObjectHandler(BatchDrawer _drawer){
      instantiator = this;
      drawer=  _drawer;
      GameObjectsHeld = new HashMap<>();
      random  = new Random();
   }

   /**
    * gets a random UID that hasnt been picked
    * @return
    */
   private int pickUID(){
      int n = 0;

      while (GameObjectsHeld.containsKey(n)){
         n = random.nextInt();
      }
      return n;
   }

   /**
    * Instatniate the gameObject
    * @param object
    */
   public void Instantiate(GameObject object){
      object.SetUID(pickUID());
     GameObjectsHeld.put(object.getUID(),object);
     drawer.ZOrderedObjects.add(object);

   }

   /**
    * Get rid of all GameObjects
    */
   public void dispose(){
      for (GameObject obj: GameObjectsHeld.values()) {

         obj.dispose();

      }
   }


}
