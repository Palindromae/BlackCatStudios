package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.*;

public class SoundFrame {
    public static SoundFrame SoundEngine;
    HashMap<String, Sound> sounds = new HashMap<String, Sound>();
    HashMap<String, List<Long>> ids = new HashMap<>();

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

        ids.put(name, new LinkedList<Long>());
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
        ids.get(name).add(id);

        return id;

    }

    public void stopSound(String name, long id) {
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).stop(id);
        ids.get(name).remove(id);
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

    public void pauseSound(String name, long id){
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.get(name).pause(id);
    }


    void setVolume(float volumeToSet){
        for (Map.Entry<String, List<Long>> entry : ids.entrySet()) {
            String key = entry.getKey();
            List<Long> value = entry.getValue();
            for (int k = 0; k<value.size(); k++){
                sounds.get(key).setVolume(value.get(k),volumeToSet);
            }
        }

    }
    public void muteSound()
    {
        setVolume(0.0f);
    }

    public void unMuteSound() {
        setVolume(1.0f);
    }
}
