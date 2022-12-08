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
			return true; //returns true when correct order is passed through
		}
		
		return false; //returns false if the dish received is not what the customer ordered
	}
	
	private boolean dishesEqual(Dish dish1, Dish dish2){
		
		//will update this function later
		return false;
	}
}
