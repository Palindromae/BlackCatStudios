package com.mygdx.game;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

public class LoadSounds {
    public static void loadSounds(){
        SoundFrame soundFrameClass = new SoundFrame();
        addSound("sound", "sound.wav");
    }



    static void addSound(String name, String path){
        SoundFrame.SoundEngine.addSound(name, path);

    }

}

