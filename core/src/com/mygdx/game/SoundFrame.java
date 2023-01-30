package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.*;

public class SoundFrame {
    public static SoundFrame SoundEngine;
    HashMap<String, Sound> sounds = new HashMap<String, Sound>();
    HashMap<String, List<Long>> ids = new HashMap<>();
     public boolean muteState = false;

    float volume = 1.0f;

    /**
     * Create a new sound frame
     */
    public SoundFrame(){
        if(SoundEngine != null){

            // throw new ValueException("Cannot have two sound frames");
                return;
        }
        SoundEngine = this;
    }

    /**
     * add a new sound to this
     * @param name name of sound
     * @param filepath path of sound
     */
    public void addSound(String name,String filepath){
        if(sounds.containsKey(name)){
            return;
        }
        Sound soundEffect = Gdx.audio.newSound(Gdx.files.internal(filepath));
        sounds.put(name, soundEffect);

        ids.put(name, new LinkedList<Long>());
    }

    /**
     * Remove a sound from dico
     * @param name
     */
    public void removeSound(String name){
        if (!sounds.containsKey(name)){
            return;
        }
        sounds.remove(name);
    }

    /**
     * Play a sound
     * @param name
     * @return returns a long ID for the sound being played
     */

    public long playSound(String name){

        if(!sounds.containsKey(name) || muteState){
            return -1;
        }
        long id = sounds.get(name).play(volume);
        ids.get(name).add(id);

        return id;

    }

    /**
     * Stop a sound given its name and id
     * @param name
     * @param id
     */
    public void stopSound(String name, long id) {
        if (!sounds.containsKey(name) || id == -1){
            return;
        }
        sounds.get(name).stop(id);
        ids.get(name).remove(id);
    }


    /**
     * sets the looping state
     * @param id
     * @param name
     */
    public void setLooping(long id, String name){
        if (!sounds.containsKey(name)|| id == -1){
            return;
        }
        sounds.get(name).setLooping(id, true);

    }

    /**
     * resume a sound given name and id
     * @param name
     * @param id
     */
    public void resumeSound(String name,long id){
        if (!sounds.containsKey(name)|| muteState){
            return;
        }
        sounds.get(name).resume(id);
    }

    /**
     * Pause a sound
     * @param name
     * @param id
     */
    public void pauseSound(String name, long id){
        if (!sounds.containsKey(name)|| id == -1){
            return;
        }
        sounds.get(name).pause();
    }


    /**
     * set the current volume
     * @param volumeToSet
     */
    void setVolume(float volumeToSet){
        for (Map.Entry<String, List<Long>> entry : ids.entrySet()) {
            String key = entry.getKey();
            List<Long> value = entry.getValue();
            for (int k = 0; k<value.size(); k++){
                sounds.get(key).setVolume(value.get(k),volumeToSet);
            }
        }

    }

    /**
     * Mute all sound
     */
    public void muteSound()
    {
        setVolume(0.0f);
    }

    /**
     * UnMute all sounds
     */

    public void unMuteSound() {
        setVolume(1.0f);
    }
}
