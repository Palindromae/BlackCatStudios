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
   private int pickUID(){
      int n = 0;

      while (GameObjectsHeld.containsKey(n)){
         n = random.nextInt();
      }
      return n;
   }
   public void Instantiate(GameObject object){
      object.SetUID(pickUID());
     GameObjectsHeld.put(object.getUID(),object);

   }

   public void dispose(){
      for (GameObject obj: GameObjectsHeld.values()) {

         obj.dispose();

      }
   }


}
