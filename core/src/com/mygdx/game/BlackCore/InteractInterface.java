package com.mygdx.game.BlackCore;

public interface InteractInterface {

    public abstract boolean TestGetItem(); // Primary Interact button
    public abstract boolean TestGiveItem();// Secondary interact button

    public ItemAbs GetItem(); //


    public boolean GiveItem(ItemAbs give);// Secondary interact button
}
