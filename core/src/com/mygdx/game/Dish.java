package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dish {
	private final List<String> ingredients = new ArrayList<String>();
	private final List<Ingredient> currentIngredients = new ArrayList<>();
	String myDishName;
	Chef myChef;

	/*
	programmers note:
	I realise we never even use this class based on the way we have written our code in the other classes
	 */
	public Dish(String dishName, Chef chef) {
		myDishName = dishName;
		myChef = chef;
		// Burger and Salad are pre-made dishes for the customers to order
		if (dishName == "Burger") {
			this.addIngredients("cooked burger");
			this.addIngredients("chopped tomato");
			this.addIngredients("chopped lettuce");
			this.addIngredients("chopped bun");
			this.addIngredients("plate");
		}

		else if (dishName == "Salad") {
			this.addIngredients("chopped tomato");
			this.addIngredients("chopped lettuce");
			this.addIngredients("plate");
		}

		else if (dishName == "DAVE") {
			// do nothing here - yet
			//this means that the dish is in the possession of Chef Dave
		}

		// Ingredients being put together in a dish will have to be put together automatically
		else if (dishName == "new dish") {
			// do nothing here
			// this is for when any combination of ingredient are put together by the chef
		}

		else {
			// pretend an error message pops up
		}

	}

	public void addIngredients(String ingredientName) {
		ingredients.add(ingredientName);
	}

	public List<Ingredient> getCurrentIngredients() {
		return currentIngredients;
	}

	public void addIngredientClass(Ingredient myIn){
		currentIngredients.add(myIn);
	}



	public List<String> getIngredients() {
        return ingredients;
    }
}
