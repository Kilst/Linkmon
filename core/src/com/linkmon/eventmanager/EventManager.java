package com.linkmon.eventmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerListener;
import com.linkmon.eventmanager.input.InputEvent;
import com.linkmon.eventmanager.input.InputListener;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageListener;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewListener;

public class EventManager {
	
	private List<ViewListener> viewListeners;
	private List<ScreenListener> screenListeners;
	private List<MessageListener> messageListeners;
	private List<ControllerListener> controllerListeners;
	private List<NetworkListener> networkListeners;
	
	private Stack<InputListener> inputListeners;
	
	public EventManager() {
		viewListeners = new ArrayList<ViewListener>();
		screenListeners = new ArrayList<ScreenListener>();
		messageListeners = new ArrayList<MessageListener>();
		controllerListeners = new ArrayList<ControllerListener>();
		networkListeners = new ArrayList<NetworkListener>();
		inputListeners = new Stack<InputListener>();
	}
	
	public synchronized void addScreenListener(ScreenListener listener) {
		if (!screenListeners.contains(listener))
			screenListeners.add(listener);
	}
	
	public synchronized void addControllerListener(ControllerListener listener) {
		if (!controllerListeners.contains(listener))
			controllerListeners.add(listener);
	}
	
	public synchronized void addInputListener(InputListener listener) {
		if (!inputListeners.contains(listener))
			inputListeners.add(listener);
	}
	
	public synchronized void addMessageListener(MessageListener listener) {
		if (!messageListeners.contains(listener))
			messageListeners.add(listener);
	}
	
	public synchronized void addViewListener(ViewListener listener) {
		if (!viewListeners.contains(listener))
			viewListeners.add(listener);
	}
	
	public synchronized void addNetworkListener(NetworkListener listener) {
		if (!networkListeners.contains(listener))
			networkListeners.add(listener);
	}
	
	public synchronized void notify(GameEvent event) {
		
		if (event.getClass() == MessageEvent.class) {
			for (MessageListener listener : messageListeners) {
				
				listener.onNotify((MessageEvent)event);
				
			}
		}
		
		else if (event.getClass() == InputEvent.class) {
			
			// Seemed the easiest way to get FILO
			
			for (int i = 0; i < inputListeners.size(); i++) {
				
				InputListener listener = inputListeners.get(inputListeners.size()-1 - i);
				if(listener.onNotify((InputEvent)event) == true)
					break;
			}
		}
		
		else if (event.getClass() == ScreenEvent.class) {
			for (ScreenListener listener : screenListeners) {
				
				listener.onNotify((ScreenEvent)event);
				
			}
		}
		
		else if (event.getClass() == ViewEvent.class) {
			for (ViewListener listener : viewListeners) {
				
				listener.onNotify((ViewEvent)event);
				
			}
		}
		
		else if (event.getClass() == ControllerEvent.class) {
			for (ControllerListener listener : controllerListeners) {
				
				if(listener.onNotify((ControllerEvent)event) == true)
					break;
				
			}
		}
		
		else if (event.getClass() == NetworkEvent.class) {
			for (NetworkListener listener : networkListeners) {
				
				if(listener.onNotify((NetworkEvent)event) == true)
					break;
				
			}
		}
	}

	public synchronized void removeNetworkListener(NetworkListener listener) {
		// TODO Auto-generated method stub
		networkListeners.remove(listener);
	}

	public synchronized void removeViewListener(ViewListener listener) {
		// TODO Auto-generated method stub
		viewListeners.remove(listener);
	}

	public synchronized void removeInputListener(InputListener listener) {
		// TODO Auto-generated method stub
		inputListeners.remove(listener);
	}
}
