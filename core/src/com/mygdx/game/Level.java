package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.TimeUtils;
import sun.tools.jconsole.JConsole;

import java.util.*;
public class Level {
    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Chef> chefList = new ArrayList<>();
    long timer;
    long idleTime;
    List<List<String>> gridArray;
    String gameMode;
    //TimeUtils.millis();
    long timeToNextCustomer;
    long getTimeToIdleGame;


    public long getTimeToNextCustomer(){
        return timeToNextCustomer;
    }


    public void readAssetFile(String gameFile){

        //This function reads read in a gameMap file and populates the gridArray variable which
        //The gridArray is a 2d array that contains a map of the game world



        FileHandle handle =  Gdx.files.local(gameFile);
        String text = handle.readString();
        //System.out.println(text);
        String[] myStrings = text.split("\n");
        for (int i = 0 ; i < myStrings.length ; i ++){
            for (int x = 0 ; x < myStrings.length ; x ++){
                if (x == 0){gridArray.add(new ArrayList<String>());}
                gridArray.get(i).add(String.valueOf(myStrings[i].charAt(x)));
            }


        }
        System.out.println( gridArray.toString());

    }



    public void initialiseLevel(){

        timeToNextCustomer = 10000;
        getTimeToIdleGame = 15000;

        timer = TimeUtils.millis();
        idleTime = TimeUtils.millis();
        gridArray = new ArrayList<>();
        readAssetFile("gameMaps/gameMap.txt");
    }

    public long getTimeElapsedMilliSeconds(){
        return TimeUtils.timeSinceMillis(timer);
    }
    public long getTimeSinceLastIO(){
        return TimeUtils.timeSinceMillis(idleTime);
    }
    public long getMinIdleTime(){
        return getTimeToIdleGame;

    }
    public void nextCustomer(){
        if (customerList.size() > 0){
            customerList.get(customerList.size() - 1);
            customerList.remove(customerList.size() - 1);
            timer = TimeUtils.millis();
        }
    }

}
