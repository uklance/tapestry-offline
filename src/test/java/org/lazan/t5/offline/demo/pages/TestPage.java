package org.lazan.t5.offline.demo.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;


public class TestPage {
	@PageActivationContext
	@Property
	private Integer pageContext;
	
	@Property
	private Integer eventContext;
	
	@Property
	private Integer currentValue;
	
	@Inject
	private Block testBlock;
	
	public List<Integer> getPageItems() {
		return getItems(pageContext);
	}
	
	public List<Integer> getEventItems() {
		return getItems(eventContext);
	}
	
	private List<Integer> getItems(int count) {
		List<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < count; ++i) {
			items.add(i + 1);
		}
		return items;
	}

	public Block onTestEvent(Integer eventContext) {
		this.eventContext = eventContext;
		return testBlock;
	}
}
