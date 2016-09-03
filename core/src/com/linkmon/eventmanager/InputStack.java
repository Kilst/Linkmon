package com.linkmon.eventmanager;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.eventmanager.input.InputListener;

public class InputStack<object> {
	
	private List<InputListener> items = new ArrayList<InputListener>();
	
	public int size() {
		return items.size();
	}
	
	public void push(InputListener item)
    {
        items.add(item);
    }

	public InputListener pop()
    {
        if (items.size() > 0)
        {
        	InputListener temp = items.get(items.size() - 1);
            items.remove(items.size() - 1);
            return temp;
        }
        return null;
    }
	
	public InputListener search(InputListener search) {
		for(InputListener listener : items) {
			
		}
		return null;
	}
	
	public void remove(int index)
    {
        items.remove(index);
    }
}
