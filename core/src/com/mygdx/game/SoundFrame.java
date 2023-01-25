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
    }

    public long playSound(String name){

        if(!sounds.containsKey(name)){
            return 0;
        }
        long id = sounds.get(name).play(volume);


        return id;

    }

    public void stopSound(String name) {
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).stop();
    }

    public void stopSound(String name, long id) {
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).stop(id);
    }



    public void setLooping(long id, String name){
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).setLooping(id, true);

    }
    public void resumeSound(String name,long id){
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).resume(id);
    }

    public void pauseSound(String name){
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).pause();
    }

    public void pauseSound(String name, long id){
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).pause();
    }


    public void muteSound()
    {
        volume = 0.0f;
    }

    public void unMuteSound() { volume = 1.0f; }

    public void setVolume(float VolToSet)
    {
        volume = VolToSet;
    }
}
