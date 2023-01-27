package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dish extends Ingredient {
	private List<String> ingredients = new ArrayList<String>();

	public Dish(String dishName) {

		// Burger and Salad are pre-made dishes for the customers to order
		if (dishName == "Burger") {
			this.addIngredients("cooked burger");
			this.addIngredients("chopped tomato");
			this.addIngredients("chopped bun");
			this.addIngredients("plate");
		}

		else if (dishName == "Salad") {
			this.addIngredients("chopped tomato");
			this.addIngredients("chopped lettuce");
			this.addIngredients("plate");
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

	public List<String> getIngredients() {
        return ingredients;
    }
}
