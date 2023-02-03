package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;
import com.mygdx.game.MyGdxGame;

/*
The HighScore class draws the text and displays it on the menuHighscores
 */
public class HighScore {

    /**
     * Gets SpriteBatch
     * @return sb
     */
    public SpriteBatch getSb() {
        return sb;
    }

    /**
     * Gets Bitmapfont font
     * @return font
     */
    public BitmapFont getFont() {
        return font;
    }

    /**
     * Gets highScores
     * @return highscores
     */
    public long[] getHighScores() {
        return highScores;
    }

    /**
     * Gets names
     * @return names
     */
    public String[] getNames() {
        return names;
    }

    private static SpriteBatch sb;

    private static BitmapFont font;

    private static long[] highScores;
    private static String[] names;


    public static FreeTypeFontGenerator.FreeTypeFontParameter params;

    public static FreeTypeFontGenerator gen;



    public HighScore(){   // sets the parameter for the text rendering

        sb = new SpriteBatch();

        gen = new FreeTypeFontGenerator(
                Gdx.files.internal("PixelArt.ttf")
        );
        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 50;
        params.color = Color.BLACK;
        font = gen.generateFont(params);
        Save.init(); // saves to the Save class

        Save.load(); // loads up scores from the highscores.json file
        highScores = Save.gd.getHighScores();
        names = Save.gd.getNames();

    }



    public void drawText(){ // function to draw text that displays the highscores in the menuHighscores

        sb.begin();

        String s;

        s = "High Scores";

        font.draw(sb,s, 250, MyGdxGame.menuHighscores.transform.position.z+(MyGdxGame.menuHighscores.textureHeight-40));
        int i =0;
        for(String key: Save.gd.SortedHighScoreMap.keySet()){
            s = String.format("%2d. %7s %s",
                    i+1, Save.gd.SortedHighScoreMap.get(key), key
            );
            font.draw(sb,s, 250, 300-50 * i);
            i += 1;
        }
        if(i < 5){  // prints only the top 5 highscores
            for(i = i; i < 5; i++){
                s = String.format("%2d. %7s %s",
                        i+1, 0, "---"
                        );
                font.draw(sb,s, 250, 300-50 * i);
            }
        }
        /*for (int i = 0; i< 5; i++){

           s = String.format(
                   "%2d. %7s %s",
                   i + 1,
                   highScores[i],
                   names[i]
           );

            font.draw(sb,s, 250, 300-50 * i);

        }*/

        sb.end();
    }



}
