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

    public ShowOrderText(){
        sb = new SpriteBatch();
        gen = new FreeTypeFontGenerator(
                Gdx.files.internal("PixelArt.ttf")
        );
        param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 15;
        param.color = Color.BLACK;
        font = gen.generateFont(param);
        this.sb = sb;
        this.font = font;
        this.param = param;
        this.gen = gen;
    }

    public static void displayText(){
        String totalOrderString = new String();
        for (Map.Entry<Integer, List<Items>> entry : DisplayOrders.displayOrders.orderDict.entrySet()) {
            List<Items> value = entry.getValue();
            String orderString = new String();
            orderString = "- ";
            Set<Items> mySet = new HashSet<Items>(value);
            for(Items s: mySet) {
                String separatedItem = String.join(" ", s.toString().split("(?=\\p{Lu})"));
                String FreqOfItems = Collections.frequency(value, s) + " x " + separatedItem;
                String newStringToAdd = new String();
                if(FreqOfItems.length() >= 20){
                    Integer previousSpace = null;
                    for(int j =0; j<orderString.length(); j++) {
                        if (Character.compare(orderString.charAt(j), " ".charAt(0)) == 0) {
                            previousSpace = j;
                        }
                        if (j % 20==0 && j!=0){
                            newStringToAdd = newStringToAdd.substring(0, previousSpace) + "\n" + newStringToAdd.substring(previousSpace+1, newStringToAdd.length());
                        }
                    }
                    FreqOfItems = newStringToAdd;
                }
                orderString = orderString + FreqOfItems + "\n";
            }
            totalOrderString = totalOrderString + orderString;
        }

        sb.begin();
        font.draw(sb, totalOrderString, 20, MyGdxGame.orderPage.transform.position.z+(MyGdxGame.orderPage.textureHeight-60));
        sb.end();
    }

    public static void disposeOf(){
        sb.dispose();
        font.dispose();
        param = null;
        gen.dispose();
    }
}
