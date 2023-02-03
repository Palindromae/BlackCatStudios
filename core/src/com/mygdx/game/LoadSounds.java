package com.mygdx.game;
public class LoadSounds {
    /**
     * Loads in all sounds
     * @param soundFrame
     */
    public void loadAllSounds(SoundFrame soundFrame){
        soundFrame.addSound("Main Screen", "Sound/MainScreen.wav");
        soundFrame.addSound("Knife Chop", "Sound/knife_chop.wav");
        soundFrame.addSound("Cooker", "Sound/gas_cooker.wav");
        soundFrame.addSound("Fryer", "Sound/frying.wav");
        soundFrame.addSound("Food Ready Bell", "Sound/food_ready_bell.wav");
        soundFrame.addSound("Step Achieved", "Sound/step_achieved.wav");
        soundFrame.addSound("Customer Arrived Bell", "Sound/customer_arrived_bell.wav");
        soundFrame.addSound("Item Equip", "Sound/ItemEquip.mp3");
        soundFrame.addSound("Item Drop", "Sound/ItemDrop.wav");
    }
}
