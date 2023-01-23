package com.mygdx.game;
public class LoadSounds {
    public void loadAllSounds(SoundFrame soundFrame){
        soundFrame.addSound("Main Screen", "Sound/MainScreen.wav");
        soundFrame.addSound("Knife Chop", "Sound/knife_chop.wav");
        soundFrame.addSound("Cooker", "Sound/gas_cooker.wav");
        soundFrame.addSound("Fryer", "Sound/frying.wav");
        soundFrame.addSound("Food Ready Bell", "Sound/food_ready_bell.wav");
        soundFrame.addSound("Step Achieved", "Sound/step_achieved.wav");
        soundFrame.addSound("Customer Arrived Bell", "Sound/customer_arrived_bell.wav");
    }
}
