package com.mygdx.game;
import java.util.*;
public class level {
    ArrayList<customer> customerList = new ArrayList<>();
    ArrayList<chef> chefList = new ArrayList<>();
    long timer;
    long idleTime;
    int[][] gridArray;
    String gameMode;

    private void updateTimer(long timePassed){
        timer += timePassed;
        idleTime += timePassed;
        if (timer > 1000 /* 1000 needs to be replaced by constant for how long between customers*/){
            newCustomer();
            timer = 0;
        }
        if (idleTime > 1000){
            gameMode = "idle";
        }

    }
    private void initialiseLevel(){
        timer = 0;
        gridArray = new int[1][1];
    }
    private void newCustomer(){
        if (customerList.size() > 0){
            customerList.get(customerList.size() - 1);
            customerList.remove(customerList.size() - 1);
        }

    }




}
