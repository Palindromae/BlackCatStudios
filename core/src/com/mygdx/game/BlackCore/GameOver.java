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

    /**
     * Gets spritebatch
     * @return sb, the spritebatch
     */
    public static SpriteBatch getSb() {
        return sb;
    }

    /**
     * Gets shaperenderer
     * @return sr, the shaperenderer
     */
    public static ShapeRenderer getSr() {
        return sr;
    }

    /**
     * Gets the font format
     * @return gameOverFont, the font format
     */

    public static BitmapFont getGameOverFont() {
        return gameOverFont;
    }

    /**
     * Gets the font writer
     * @return gameOverFont, the font writer
     */
    public static BitmapFont getFont() {
        return font;
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


    /**
     * Intialises all the required variables
     * @param restart
     */
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

    /**
     * Runs the update function, causing handleInput to run on each update, allowing the name to be chosen at game over
     * @param dt
     */
    public void update(float dt){
        handleInput();
    }

    /**
     * Draws the game over text, the score
     * If its a high score allow the user to choose a name and add their high score
     */
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


    /**
     * Processes the key presses to change the characters for the name setting when high score achieved
     */
    public void handleInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
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
