package com.mygdx.game;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

public class LoadSounds {
    public void loadAllSounds(SoundFrame soundFrame){
        soundFrame.SoundEngine.addSound("Main Screen", "Sound/MainScreen.wav");
        soundFrame.SoundEngine.addSound("Knife Chop", "Sound/knife_chop.wav");
        soundFrame.SoundEngine.addSound("Cooker", "Sound/gas_cooker.wav");
        soundFrame.SoundEngine.addSound("Fryer", "Sound/frying.wav");
        soundFrame.SoundEngine.addSound("Food Ready Bell", "Sound/food_ready_bell.wav");
        soundFrame.SoundEngine.addSound("Step Achieved", "Sound/step_achieved.wav");
        soundFrame.SoundEngine.addSound("Customer Arrived Bell", "Sound/customer_arrived_bell.wav");
    }
}
