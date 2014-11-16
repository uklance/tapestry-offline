package org.lazan.t5.offlinedemo.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

public class MyPage {
	@Property
	private List<Integer> loopItems;
	
	@Property
	private Integer loopItem;
	
	@Property
	private String fieldValue;
	
	@SessionAttribute("sessionAttribute")
	@Property
	private String sessionAttribute;
	
	public void onActivate(int loopCount) {
		loopItems = new ArrayList<Integer>();
		for (int i = 0; i < loopCount; ++ i) {
			loopItems.add(i);
		}
		fieldValue = "test value";
	}
}
