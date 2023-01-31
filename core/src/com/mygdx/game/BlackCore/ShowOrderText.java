package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.CoreData.Items.Items;
import com.mygdx.game.MyGdxGame;

import java.util.*;

public class ShowOrderText extends BlackScripts{
    private static SpriteBatch sb = null;
    private static BitmapFont font = null;
    private static FreeTypeFontGenerator.FreeTypeFontParameter param= null;
    private static FreeTypeFontGenerator gen = null;


    /**
     * Initialises all required variables
     */
    public ShowOrderText(){
        sb = new SpriteBatch(); //Creates a new sprite batch for drawing
        gen = new FreeTypeFontGenerator(
                Gdx.files.internal("PixelArt.ttf")
        ); //Creates a new font generator, using the pixel art font
        param = new FreeTypeFontGenerator.FreeTypeFontParameter(); //Creates a new parameter for the font generator
        param.size = 15;
        param.color = Color.BLACK;
        font = gen.generateFont(param);
        this.sb = sb;
        this.font = font;
        this.param = param;
        this.gen = gen;
    }

    /**
     * Displays text to the screen based on current orders
     */
    public static void displayText(){
        String totalOrderString = new String(); //This string is the string that gets displayed

        //Removes empty lists from the hashmap
        List<Integer> valuesToRemoved = new LinkedList<>();
        for (Map.Entry<Integer, List<Items>> entry : DisplayOrders.displayOrders.orderDict.entrySet())
        {
            if(entry.getValue() == null || entry.getValue().size() == 0 )
            {
                valuesToRemoved.add(entry.getKey());
            }
        }

        for (Integer a: valuesToRemoved
             ) {
            DisplayOrders.displayOrders.orderDict.remove(a);
        }
            for (Map.Entry<Integer, List<Items>> entry : DisplayOrders.displayOrders.orderDict.entrySet()) {//Iterates through all the orders

            List<Items> value = entry.getValue(); //Gets the order
            String orderString;
            orderString = "- "; //Adds the "-" at the start of each order
            String toAddOnEnd = "\n  "; //Defines what to add on the end of each item
            Set<Items> mySet = new HashSet<Items>(value);
            for (Items s : mySet) {
                List splitItems = Arrays.asList(s.toString().split("(?=\\p{Lu})")); // Split each item in the order, by capital letters
                for(int j = 0; j<splitItems.size(); j++){
                    //The orderpage can store 20 characters on each line
                    if(splitItems.get(j).toString().length() > 20){ //If a word has more than 20 characters, add a new line character followed by a "-" after 15 characters
                        String updatedItem;
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < splitItems.get(j).toString().length(); i++) {
                            if (i > 0 && (i % 15 == 0)) {
                                sb.append("\n     -");
                            }

                            sb.append(splitItems.get(j).toString().charAt(i));
                        }

                        updatedItem = sb.toString();
                        splitItems.set(j,updatedItem);
                    }


                }

                String separatedItem = String.join(" ", splitItems);//Join the items for an order
                String itemString = Collections.frequency(value, s) + " x " + separatedItem;
                if(itemString.length()>20 && !itemString.contains("\n")){ // If the order is longer than 20 characters, and hasnt already been processed
                    Integer previousSpace = itemString.length(); //previousSpace records where the last space was, starts as the length, so if there is no spaces, then it just returns the string
                    String newItemString = itemString; // Temporary placeholder string
                    Integer numOfSpaces = 0; //Counts how many spaces have been added to live track indexes
                    for(int i = 0; i<itemString.length();i++){
                        if(itemString.charAt(i)==" ".charAt(0)){ //Checks if current char is a space
                            previousSpace = i; //Updates the previous space pointer
                        }
                        if(i%20 == 0 && i!=0 ){
                            newItemString = newItemString.substring(0,previousSpace + numOfSpaces) + "\n   " + itemString.substring(previousSpace); // When it reaches 20 characters, find the previous space and add a new line
                            numOfSpaces += 4; // Increase the number of spaces used
                        }
                    }
                    itemString = newItemString;
                }
                orderString = orderString + itemString + toAddOnEnd; // Add formatted order

            }
            totalOrderString += orderString + "\n\n"; //Combine all orders to a final string
        }

        sb.begin();
        font.draw(sb, totalOrderString, 20, MyGdxGame.orderPage.transform.position.z+(MyGdxGame.orderPage.textureHeight-60)); //Draw the order to the screen
        sb.end();
    }

    public static void disposeOf(){
        sb.dispose();
        font.dispose();
        param = null;
        gen.dispose();
    }
}
