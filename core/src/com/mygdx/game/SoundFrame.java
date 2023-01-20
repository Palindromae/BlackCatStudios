package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundFrame {
    public static SoundFrame SoundEngine;
    HashMap<String, Sound> sounds = new HashMap<String, Sound>();

    float volume = 1.0f;


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
        sounds.get(name).play(volume);


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


    public void muteSound()
    {
        volume = 0.0f;
    }

    public void unMuteSound()
    {

        volume = 1.0f;
    }

    public void setVolume(float VolToSet)
    {
        volume = VolToSet;
    }
}
