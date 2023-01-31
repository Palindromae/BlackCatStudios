package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Gdx;
import jdk.nashorn.internal.parser.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Save {

    static String filePath = Gdx.files.internal("assets/highscores.json").path();

    public static GameData gd;

    public static void save() {
        HashMap<String, Integer> myMap = new HashMap<String, Integer>();
        try {
            FileReader fr = new FileReader(filePath);
            int j;
            String data = "";
            while ((j = fr.read()) != -1) {
                data = data + (char) j;
            }
            fr.close();
            if(!data.isEmpty()){
                data = data.substring(1, data.length() -1);
                String[] pairs = data.split(",");
                for (int k = 0; k < pairs.length; k++) {
                    System.out.println("x: " + pairs[k]);
                    String currentPair = pairs[k].substring(1,pairs[k].length()-1);
                    myMap.put(pairs[k].split(":")[0], Integer.valueOf(pairs[k].split(":")[1]));
                }
            }
            myMap.put(Save.gd.getName(),(int) Save.gd.getTentativeScore());

            String jsonString = "{";
            for(String key : myMap.keySet()){
                Integer value = myMap.get(key);
                if(!key.contains("\"")){
                    key = "\"" + key + "\"";
                }
                jsonString = jsonString + key + ":" + value.toString()+ ",";
            }
            jsonString = jsonString.substring(0, jsonString.length()-1) + "}";

            System.out.println(jsonString);
            FileWriter writer = new FileWriter(filePath);
            writer.write(jsonString);
            writer.close();
            Save.load();
        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void load() {
        try {
            FileReader fr = new FileReader(filePath);
            int j;
            String data = "";
            while ((j = fr.read()) != -1) {
                data = data + (char) j;
            }
            fr.close();
            if(!data.isEmpty()){
                data = data.substring(1, data.length()-1);
                String[] pairs = data.split(",");
                for (int k = 0; k < pairs.length; k++) {
                    String[] currentPair = pairs[k].split(":");
                    currentPair[0] = currentPair[0].replace("\"", "");
                    currentPair[1] = currentPair[1].replace("\"", "");
                    Save.gd.PutScoreToList(currentPair[0], Integer.valueOf(currentPair[1]));
                }
                Save.gd.sortHighScoreMap();
            }



        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists() {
        File f = new File("highscores.sav");
        return f.exists();
    }

    public static void init() {
        if(gd != null)
            return;

        gd = new GameData();
        gd.init();
    }


}
