package com.marketingpersonal.common;

public class ListasGenericas {
	
	private static ListasGenericas instance;
	
	public static ListasGenericas getInstance() {
		if(instance == null)
			instance = new ListasGenericas();
		return instance;
	}
	
	private ListasGenericas(){
	}


}
