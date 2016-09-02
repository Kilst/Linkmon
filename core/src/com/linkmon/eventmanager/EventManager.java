package com.linkmon.eventmanager;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerListener;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageListener;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewListener;

public class EventManager {
	
	private List<ViewListener> viewListeners;
	private List<MessageListener> messageListeners;
	private List<ControllerListener> controllerListeners;
	private List<NetworkListener> networkListeners;
	
	public EventManager() {
		viewListeners = new ArrayList<ViewListener>();
		messageListeners = new ArrayList<MessageListener>();
		controllerListeners = new ArrayList<ControllerListener>();
		networkListeners = new ArrayList<NetworkListener>();
	}
	
	public synchronized void addControllerListener(ControllerListener listener) {
		if (!controllerListeners.contains(listener))
			controllerListeners.add(listener);
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
}
