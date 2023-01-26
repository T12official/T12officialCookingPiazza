package com.mygdx.game;
import java.util.*;

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
		String[] ingredients1 = dish1.getIngredients();
		// dish 2 is the dish the chef has handed in
		String[] ingredients2 = dish2.getIngredients();

		// need to search through all the ingredients to make sure each dish has the same ingredients
		int size = ingredients2.length;
		for ( int i = 0; i < size; i++) {
			// add code
		}

		// will update this function later
		return false;
	}
}