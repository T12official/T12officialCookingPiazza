package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dish extends Ingredient {
	
	private List<String> ingredients = new ArrayList<String>();
	
	public Dish() {
		
	}
	
	public void addIngredients(String ingredientName) {
		ingredients.add(ingredientName);
	}
	
	public List<String> getIngredients() {
        // returns an array list of ingredients
        // placeholder because broken customer?
        return new ArrayList<String>();
    }
}
