package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Gdx;

import java.util.HashSet;

public class BlackScriptManager {

    HashSet<BlackScripts> LooseScripts;


    static BlackScriptManager manager;

    public BlackScriptManager() throws Exception {
        if (manager != null)
            throw new Exception("You cannot declare this singleton more than once");
        manager = this;
        LooseScripts = new HashSet<>();

    }

    /**
     * This tries append a script to be run unattached to a gameobject
     * @param script, script to attach
     * @return
     */
    public boolean tryAppendLooseScript(BlackScripts script){
        if (LooseScripts.contains(script))
            return  false;
        LooseScripts.add(script);

        script.StartUpMethodSequence();
        return true;
    }

    /**
     * runs update on all scripts
     */
    public void RunUpdate(){
        for (BlackScripts script:LooseScripts
             ) {
            script.Update(Gdx.graphics.getDeltaTime());
        }

        for (GameObject obj:GameObjectHandler.instantiator.GameObjectsHeld.values()
             ) {
            obj.runScriptsUpdate();

        }
    }

    /**
     * Runs FixedUpdate on all scripts
     * @param FixedDeltaTime
     */
    public void RunFixedUpdate(float FixedDeltaTime){
        for (BlackScripts script:LooseScripts
        ) {
            script.FixedUpdate(FixedDeltaTime);
        }

        for (GameObject obj:GameObjectHandler.instantiator.GameObjectsHeld.values()
        ) {
            obj.runScriptsFixedUpdate(FixedDeltaTime);

        }
    }
}
