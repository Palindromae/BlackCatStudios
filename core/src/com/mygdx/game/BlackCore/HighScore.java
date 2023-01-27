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

public class HighScore extends BlackScripts {

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

    public void update(float dt){
        handleInput();
    }

    public static void drawText(){
//        sb.setProjectionMatrix(MyGdxGame.camera.combined);



        String s;
        float w;

        s = "High Scores";
        w = font.getScaleX();
        sb.begin();
        font.draw(sb, s, 20, 20);

        for (int i = 0; i< highScores.length; i++){
           s = String.format(
                   "%2d. %7s %s",
                   i + 1,
                   highScores[i],
                   names[i]
           );
           w = font.getScaleX();
            font.draw(sb,s, 800, 270-20 * i);

        }

        sb.end();


    }

    private void handleInput() {
            //path to go back to main menu

        //changeMenuVisbility();
        //menuHighscores.negateVisibility();

    }


}
