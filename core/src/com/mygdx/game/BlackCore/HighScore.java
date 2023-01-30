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

public class HighScore extends MyGdxGame {

    public SpriteBatch getSb() {
        return sb;
    }

    public BitmapFont getFont() {
        return font;
    }

    public long[] getHighScores() {
        return highScores;
    }

    public String[] getNames() {
        return names;
    }

    private static SpriteBatch sb;

    private static BitmapFont font;

    private static long[] highScores;
    private static String[] names;


    public static FreeTypeFontGenerator.FreeTypeFontParameter params;

    public static FreeTypeFontGenerator gen;

    public HighScore(){

        sb = new SpriteBatch();

        gen = new FreeTypeFontGenerator(
                Gdx.files.internal("PixelArt.ttf")
        );
        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 50;
        params.color = Color.BLACK;
        font = gen.generateFont(params);

        Save.load();
        highScores = Save.gd.getHighScores();
        names = Save.gd.getNames();

    }



    public void drawText(){

        sb.begin();

        String s;

        s = "High Scores";

        font.draw(sb,s, 250, MyGdxGame.menuHighscores.transform.position.z+(MyGdxGame.menuHighscores.textureHeight-40));
        for (int i = 0; i< highScores.length; i++){
           s = String.format(
                   "%2d. %7s %s",
                   i + 1,
                   highScores[i],
                   names[i]
           );

            font.draw(sb,s, 250, 300-50 * i);

        }

        sb.end();
    }



}
