package com.mygdx.game;

public class recipyText {
    public static final String recipe1  = "test_recipe 1";
    public static final String recipe2  = "test_recipe 2";
    public static final String recipe3  = "test_recipe 3";
    public static final String recipe4  = "test_recipe 4";

    public String getRecipy(int recNum){
        switch (recNum){
            case 1:
                return recipe1;
                //break;
            case 2:
                return recipe2;
                //break;
            case 3:
                return recipe3;
                //break;
            case 4:
                return recipe4;
                //break;
            default:
                return recipe1;
        }
    }
}
