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
            for (Map.Entry<Integer, List<Items>> entry : DisplayOrders.displayOrders.orderDict.entrySet()) {

            List<Items> value = entry.getValue();
            String orderString;
            orderString = "- ";
            String toAddOnEnd = "\n  ";
            Set<Items> mySet = new HashSet<Items>(value);
            for (Items s : mySet) {
                List splitItems = Arrays.asList(s.toString().split("(?=\\p{Lu})"));
                for(int j = 0; j<splitItems.size(); j++){
                    if(splitItems.get(j).toString().length() > 20){
                        String updatedItem = splitItems.get(j).toString().substring(0,15) + "\n" + splitItems.get(j).toString().substring(15);
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

                String separatedItem = String.join(" ", splitItems);
                String itemString = Collections.frequency(value, s) + " x " + separatedItem;
                if(itemString.length()>20 && !itemString.contains("\n")){
                    Integer previousSpace = itemString.length();
                    String newItemString = itemString;
                    Integer numOfSpaces = 0;
                    for(int i = 0; i<itemString.length();i++){
                        if(itemString.charAt(i)==" ".charAt(0)){
                            previousSpace = i;
                        }
                        if(i%20 == 0 && i!=0 ){
                            newItemString = newItemString.substring(0,previousSpace + numOfSpaces) + "\n   " + itemString.substring(previousSpace);
                            numOfSpaces += 4;
                        }
                    }
                    itemString = newItemString;
                }
                orderString = orderString + itemString + toAddOnEnd;

            }
            totalOrderString += orderString + "\n\n";
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
