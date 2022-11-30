package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.Map;

public class SoundFrame {
    public static SoundFrame SoundEngine;
    HashMap<String, Sound> sounds = new HashMap<String, Sound>();


    public SoundFrame(){
        if(SoundEngine != null)
            return;
        SoundEngine = this;
    }

    public void addSound(String name,String filepath){
        if(sounds.containsKey(name)){
            return;
        }
        Sound soundEffect = Gdx.audio.newSound(Gdx.files.internal(filepath));
        sounds.put(name, soundEffect);
    }

    public void removeSound(String name){
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.remove(name);
    };

    public void playSound(String name){

        if(!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).play();


    }

    public void stopSound(String name) {
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).stop();
    };

    public void pauseSound(String name){
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).pause();
    }
}
