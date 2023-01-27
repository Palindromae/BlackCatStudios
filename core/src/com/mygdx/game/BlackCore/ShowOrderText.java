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
        LinkedList<Items> toAddToOrder = new LinkedList<Items>();
        String totalOrderString = new String();
        toAddToOrder.add(Items.CheeseBurger);
        toAddToOrder.add(Items.CheeseBurger);
        toAddToOrder.add(Items.Burger);
        toAddToOrder.add(Items.TomatoOnionLettuceSalad);
        toAddToOrder.add(Items.CookedPatty);
        DisplayOrders.displayOrders.orderDict.put(23,toAddToOrder);

        for (Map.Entry<Integer, List<Items>> entry : DisplayOrders.displayOrders.orderDict.entrySet()) {
            List<Items> value = entry.getValue();
            String orderString = new String();
            orderString = "- ";
            Set<Items> mySet = new HashSet<Items>(value);
            for(Items s: mySet){
                String separatedItem = String.join(" ", s.toString().split("(?=\\p{Lu})"));
                orderString = orderString + Collections.frequency(value,s) + " x " + separatedItem + ",";

            }
            orderString = orderString.substring(0,orderString.length()-1);

            String newToAddToOrderString = orderString;
            Integer previousComma = null;
            for(int j =0; j<orderString.length(); j++){
                if(Character.compare(orderString.charAt(j), ",".charAt(0)) == 0){
                    previousComma = j;
                }
                if (j % 20==0 && j!=0){
                    newToAddToOrderString = newToAddToOrderString.substring(0, previousComma) + "\n" + newToAddToOrderString.substring(previousComma+1, newToAddToOrderString.length());
                }
            }
            newToAddToOrderString = newToAddToOrderString.replace("\n", "\n  ");

            totalOrderString = totalOrderString + newToAddToOrderString + "\n\n";
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
