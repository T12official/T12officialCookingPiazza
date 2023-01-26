package com.mygdx.game;

public class RecipeText {
    public static final String recipe1  = "test_recipe 1";
    public static final String recipe2  = "test_recipe 2";
    public static final String recipe3  = "test_recipe 3";
    public static final String recipe4  = "test_recipe 4";

    public String getRecipe(int recNum){
        switch (recNum){
            case 1:
                return recipe1;
            case 2:
                return recipe2;
            case 3:
                return recipe3;
            case 4:
                return recipe4;
            default:
                return "???";
        }
    }
}
