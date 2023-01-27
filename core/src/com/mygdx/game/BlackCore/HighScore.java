package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;
import com.mygdx.game.MyGdxGame;

public class HighScore extends MyGdxGame {

    private SpriteBatch sb;

    private BitmapFont font;

    private long[] highScores;
    private String[] names;

    public void init(){

        sb = new SpriteBatch();

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                Gdx.files.internal("Hey Comic.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 20;
        font = gen.generateFont(params);

        Save.load();
        highScores = Save.gd.getHighScores();
        names = Save.gd.getNames();

    }

    public void update(float dt){
        handleInput();
    }

    public void draw(){
        sb.setProjectionMatrix(MyGdxGame.camera.combined);

        sb.begin();

        String s;
        float w;

        s = "High Scores";
        w = font.getScaleX();
        font.draw(sb,s, 800, 300);
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

    }


}
