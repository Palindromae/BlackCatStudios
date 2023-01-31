package com.mygdx.game.BlackCore;

import java.io.Serializable;
import java.util.*;

public class GameData implements Serializable {

    private static final long serialVersionUID = 1;

    private final int MAX_SCORES = 5;

    private long[] highScores;
    private String[] names;

    private long tentativeScore;
    private double timing;
    private String name;

    private Map highScoresMap = new HashMap<String, Integer>();
    LinkedHashMap<String, Integer> SortedHighScoreMap = new LinkedHashMap<String, Integer>();

    public GameData(){
        highScores = new long[MAX_SCORES];
        names = new String[MAX_SCORES];
    }

    //sets up an empty high scores table
    public void init(){
        for (int i = 0; i < MAX_SCORES; i++){
            highScores[i] = 0;
            names[i] = "---";
        }
    }

    public long[] getHighScores(){ return highScores;}
    public String[] getNames(){ return names;}

    public void PutScoreToList(String name, Integer score){highScoresMap.put(name, score);};

    public long getTentativeScore(){ return tentativeScore;}

    /**
     * Gets the time taken for the user to complete the game
     * @return timing - time taken
     */
    public double getTiming(){ return timing;}

    /**
     * Gets the name given for the highscore
     * @return name - Name given
     */
    public String getName(){return name;};

    /**
     * sets the name to the given name
     * @param nameToSet
     */
    public void setName(String nameToSet){name = nameToSet;}
    public void setTentativeScore(long i){ tentativeScore = i;}

    /**
     * Sets the timer to a new value
     * @param i
     */
    public void setTiming(double i){ timing = i;}

    public boolean isHighScore(long score){

        return score > highScores[MAX_SCORES - 1];

    }

    /**
     * Sorts the highscore map based on the score, and takes the first 5 entries
     */
    public void sortHighScoreMap(){
        Save.gd.SortedHighScoreMap = new LinkedHashMap<String, Integer>();
        List<Integer> highScoreByScore = new ArrayList<>(Save.gd.highScoresMap.values());
        Collections.sort(highScoreByScore);
        Collections.reverse(highScoreByScore);
        for(int i = 0; i < highScoreByScore.size(); i++){
            Integer toCheck = highScoreByScore.get(i);
            //Create map to store, check if its 5 or more etc
            if(SortedHighScoreMap.size() == 5){
                continue;
            }
            for(Object x : Save.gd.highScoresMap.entrySet()){
                String key = x.toString().split("=")[0];
                Integer value = Integer.valueOf(x.toString().split("=")[1]);
                if((int) value == toCheck){
                    SortedHighScoreMap.put(key, value);
                }
            }
        }
    }

}
