package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MyGdxGame;

public class GameOver extends MyGdxGame {

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

    public GameOver(){

        sb = new SpriteBatch();
        sr = new ShapeRenderer();

        newHighScore = Save.gd.isHighScore(Save.gd.getTentativeScore());
        if(newHighScore){
            newName = new char[] {'A','A','A'};
            currentChar = 0;
        }

        gen = new FreeTypeFontGenerator(Gdx.files.internal("PixelArt.ttf"));
        paramsGameOverFont.size = 50;
        paramsGameOverFont.color = Color.BLACK;
        gameOverFont = gen.generateFont(paramsGameOverFont);

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

        if(!newHighScore){
            sb.end();
            return;
        }

        String q = "New High Score: " + Save.gd.getTentativeScore();

        font.draw(sb, q, 250, 250);

        for(int i = 0; i < newName.length; i++){
            font.draw(sb, Character.toString(newName[i]),250 + 14 * i,200);
        }

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
            Save.save();
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
