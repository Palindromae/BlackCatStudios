package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MyGdxGame;

import java.util.function.Consumer;

public class GameOver {

    Runnable Restart;

    public static SpriteBatch getSb() {
        return sb;
    }

    public static ShapeRenderer getSr() {
        return sr;
    }

    public static BitmapFont getGameOverFont() {
        return gameOverFont;
    }

    public static BitmapFont getFont() {
        return font;
    }

    public boolean isNewHighScore() {
        return newHighScore;
    }

    public char[] getNewName() {
        return newName;
    }

    public int getCurrentChar() {
        return currentChar;
    }

    private static SpriteBatch sb;
    private static ShapeRenderer sr;

    private static BitmapFont gameOverFont;
    private static BitmapFont font;


    private boolean newHighScore;
    private char[] newName;
    private int currentChar;

    public static FreeTypeFontGenerator gen;

    public static FreeTypeFontGenerator.FreeTypeFontParameter paramsGameOverFont;
    public static FreeTypeFontGenerator.FreeTypeFontParameter paramsFont;

    public GameOver(Runnable restart){

        Restart = restart;
        sb = new SpriteBatch();
        sr = new ShapeRenderer();

        Save.load();

        newHighScore = Save.gd.isHighScore(Save.gd.getTentativeScore());
        if(!newHighScore){
            newName = new char[] {'A','A','A'};
            currentChar = 0;
        }

        gen = new FreeTypeFontGenerator(Gdx.files.internal("PixelArt.ttf"));

        paramsGameOverFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramsGameOverFont.size = 50;
        paramsGameOverFont.color = Color.BLACK;

        gameOverFont = gen.generateFont(paramsGameOverFont);
        paramsFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramsFont.size = 25;
        paramsFont.color = Color.BLACK;
        font = gen.generateFont(paramsFont);


    }

    public void update(float dt){
        handleInput();
    }

    public void drawText(){
        sb.begin();

        String s = "Game Over";

        gameOverFont.draw(sb, s, 250, 300);

        if(newHighScore){
            sb.end();
            return;
        }

        String q = "Your Score: " + Save.gd.getTentativeScore() + " and took: " + Math.round(Save.gd.getTiming()*100.0)/100.0 +"s";
        String x = (newName[0] + " " + newName[1] + " "+ newName[2]);

        font.draw(sb, q, 220, 250);
        font.draw(sb, x, 220,200);
        font.draw(sb, "_ _ _",220,195);
        font.draw(sb, "Use the arrow keys to change the letters", 100, 140);
        font.draw(sb, "Press Enter to submit your score", 150, 120);
        sb.end();

        sr.begin(ShapeRenderer.ShapeType.Line);

        sr.line(250 + 14 * currentChar, 100, 260 + 14 * currentChar, 100);

        sr.end();

    }

    public void handleInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            Save.gd.addHighScore(
                    Save.gd.getTentativeScore(),
                    new String(newName)
            );
            Save.gd.setName(String.valueOf(newName));
            Save.save();
            MyGdxGame.gameEnded = false;
            MyGdxGame.menuHighscores.negateVisibility();
            Restart.run();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(newName[currentChar] == ' '){
                newName[currentChar] = 'Z';
            }
            else{
                newName[currentChar]--;
                if(newName[currentChar] < 'A'){
                    newName[currentChar] = ' ';
                }

            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            if(newName[currentChar] == ' '){
                newName[currentChar] = 'A';
            }
            else{
                newName[currentChar]++;
                if(newName[currentChar] > 'Z'){
                    newName[currentChar] = ' ';
                }

            }

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(currentChar < newName.length - 1){
                currentChar++;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(currentChar > 0){
                currentChar--;
            }

        }



    }

    public void dispose(){}


}
