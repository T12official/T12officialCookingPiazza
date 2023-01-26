package com.mygdx.game;
import java.util.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Customer {
	
	Dish order = new Dish();
	long waitTime = -1; // the customer does not use a wait time
	
	public Customer(Dish order) {
		this.order = order;
	}
	
	public boolean recieveOrder(Dish dishReceived) {
		
		if ( dishesEqual(order, dishReceived) ) {
			return true; // returns true when correct order is passed through
		}
		
		return false; // returns false if the dish received is not what the customer ordered
	}
	

	
	private boolean dishesEqual(Dish dish1, Dish dish2) {
		
		// dish 1 is the dish the customer ordered
		List<String> ingredients1 = dish1.getIngredients();
		// dish 2 is the dish the chef has handed in
		List<String> ingredients2 = dish2.getIngredients();
		
		if (ingredients1.size() != ingredients2.size() ) {
			return false;
		}

		// need to search through all the ingredients to make sure each dish has the same ingredients
		int size = ingredients2.size();
		
		
		for ( int i = 0; i < size; i++) {	
			if ( !ingredients2.contains(ingredients1.get(i)) ) {
				return false;
			}
		}
		// return true if all ingredients are the same
		return true;
	}
}