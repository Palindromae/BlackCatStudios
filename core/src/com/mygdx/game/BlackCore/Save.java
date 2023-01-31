package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Gdx;
import jdk.nashorn.internal.parser.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Save {
    static String filePath = Gdx.files.internal("highscores.json").path(); //Gets the path for the highscores file

    public static GameData gd; //Initializes the gamedata


    public static void doFileCheck() { //Function to check if the file exists and create new one if it doesn't
        File file = new File(filePath);//Opens a file with the filepath, if file doesn't exist the path is ""

        try {
            if (file.createNewFile())//Create the new file, of the file path, if it doesn't exist
            {
            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        HashMap<String, Integer> myMap = new HashMap<String, Integer>(); //Creates a HashMap that will store all the highscore data
        try {
            FileReader fr = new FileReader(filePath); //Opens the highscore file, based on the filepath
            int j;
            String data = "";
            while ((j = fr.read()) != -1) {//Iterates through every character in the file, adding it to data
                data = data + (char) j;
            }
            fr.close();
            if (!data.isEmpty()) {//If data is not empty, format it
                data = data.substring(1, data.length() - 1); //Remove the curly brackets from start and end of string
                String[] pairs = data.split(","); //Split the string by commas, to get every entry
                for (int k = 0; k < pairs.length; k++) {//Iterate through every highscore entry
                    myMap.put(pairs[k].split(":")[0], Integer.valueOf(pairs[k].split(":")[1])); //Split the entries on ":" to get key and value and add them to the map of scores
                }
            }
            myMap.put(Save.gd.getName(), (int) Save.gd.getTentativeScore()); //Add the new entry to the map of scores


            //Create the string from the map to put into the json file
            String jsonString = "{"; //Add the initial curly bracket
            for (String key : myMap.keySet()) { //Iterate through the key set
                Integer value = myMap.get(key);
                if (!key.contains("\"")) { //Check if quotes are not already in the string (quotes are required for the json format)
                    key = "\"" + key + "\""; //Add the quotes to satisfy json format
                }
                jsonString = jsonString + key + ":" + value.toString() + ","; //Add the key anf value to the json string
            }
            jsonString = jsonString.substring(0, jsonString.length() - 1) + "}";//Remove the final comma, and add the end curly bracket
            FileWriter writer = new FileWriter(filePath); //Open a write to the file based on the filepath
            writer.write(jsonString); //Write the json string to the file
            writer.close();
            Save.load();//Call the load function, to update the orderDict
        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void load() {
        try {
            FileReader fr = new FileReader(filePath);//Open a file reader
            int j;
            String data = "";
            while ((j = fr.read()) != -1) { //Iterate through the characters in the file, and add them to the data string
                data = data + (char) j;
            }
            fr.close();
            if (!data.isEmpty()) { //If the string isn't empty, format the string
                data = data.substring(1, data.length() - 1); //Remove the curly brackets at the start and end
                String[] pairs = data.split(","); //Split the string based on "," to get each entry
                for (int k = 0; k < pairs.length; k++) { //Iterate through all entries and format
                    String[] currentPair = pairs[k].split(":"); //Split the entries by ":"
                    currentPair[0] = currentPair[0].replace("\"", ""); //Remove all quotes from key
                    currentPair[1] = currentPair[1].replace("\"", ""); //Remove all quotes from value
                    Save.gd.PutScoreToList(currentPair[0], Integer.valueOf(currentPair[1])); //Add scores to gamedata class
                }
                Save.gd.sortHighScoreMap(); //Sort the scores getting the top 5
            }


        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void init() { //Create the gamedata instance, and initialise it
        if (gd != null)
            return;

        gd = new GameData();
        gd.init();
    }


}
