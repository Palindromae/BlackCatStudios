package com.mygdx.game.BlackCore;

public interface InteractInterface {

    /**
     * Can this script GetItems
     * @return
     */
    public abstract boolean TestGetItem(); // Primary Interact button

    /**
     * Can this script give
     * @return
     */
    public abstract boolean TestGiveItem();// Secondary interact button


    /**
     * Get the Item
     * @return returns the Item
     */
    public ItemAbs GetItem(); //

    /**
     * Give the item
     * @param give item to give
     * @return success state
     */
    public boolean GiveItem(ItemAbs give);// Secondary interact button
}
