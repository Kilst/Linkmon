package com.linkmon.helpers;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.eventmanager.view.ViewListener;
import com.linkmon.view.screens.widgets.messages.MessageType;

public class HelpMessages implements ViewListener {
	
	private boolean introMessage = false;
	private boolean sleepyMessage = false;
	private boolean poopMessage = false;
	private boolean evolveMessage = false;
	private boolean trainMessage = false;
	private boolean mistakeMessage = false;
	private boolean deathMessage = false;
	private boolean onlineMessage = false;
	private boolean buyMessage = false;
	private boolean feedMessage = false;
	private boolean hungryMessage = false;
	private boolean statsMessage = false;
	
	private EventManager eManager;
	
	public HelpMessages(EventManager eManager) {
		this.eManager = eManager;
		this.eManager.addViewListener(this);
	}
	
	public void showIntroMessage() {
		if(introMessage == false) {
			String[] strings = new String[5];
    		strings[0] = "Welcome stranger! I'm Tinmon. I'll be here to help you out along the way. I'll try not to be too annoying, I promise. All of my help can be viewed again using the help button found in the top-right corner of your screen.";
    		strings[1] = "For now, I'll explain the basics. You've been given an egg. It has hatched, and you are now looking after that little guy for me.";
    		strings[2] = "I want you to make sure it is feed, its poops are cleaned, put to bed, and that you also train it.";
    		strings[3] = "As you train, your Linkmon will evolve. You should aim to train if you want to compete online. The rewards are worth it!";
    		strings[4] = "Good luck, and have fun!";
        	eManager.notify(new MessageEvent(MessageEvents.SHOW_CHAT, MessageType.GAME_MESSAGE, 1, strings));
		}
		introMessage = true;
	}
	
	private void showPoopMessage() {
		if(poopMessage == false) {
			String[] messages = new String[1];
			messages[0] = "Hey!\nYour Linkmon has pooped. To clean the poop you have to tap on it. If you leave it too long, you will receive a care mistake. So get to it!";
			eManager.notify(new MessageEvent(MessageEvents.SHOW_CHAT, MessageType.GAME_MESSAGE, 1, messages));
		}
		poopMessage = true;
	}
	
	private void showEvolveMessage() {
		if(evolveMessage == false) {
			String[] messages = new String[2];
			messages[0] = "Your Linkmon has evolved! Congratulations!";
			messages[1] = "Since you Linkmon has evolved, it's stats have increased, it will take longer to get hungry, and it will poop less often.";
			eManager.notify(new MessageEvent(MessageEvents.SHOW_CHAT, MessageType.GAME_MESSAGE, 1, messages));
		}
		evolveMessage = true;
	}
	
	private void showHungryMessage() {
		if(hungryMessage == false) {
			String[] messages = new String[1];
			messages[0] = "Your Linkmon is hungry. You will need to open the \"Feed\" menu and choose something to feed them.";
			eManager.notify(new MessageEvent(MessageEvents.SHOW_CHAT, MessageType.GAME_MESSAGE, 1, messages));
		}
		hungryMessage = true;
	}

	private void showSleepyMessage() {
		// TODO Auto-generated method stub
		if(sleepyMessage == false) {
			String[] messages = new String[1];
			messages[0] = "Your Linkmon is sleepy. If you don't put it to bed quick enough, or if you turn the light back on after putting it to bed, you will recieve a care mistake. The light switch is in the top left corner of your screen.";
			eManager.notify(new MessageEvent(MessageEvents.SHOW_CHAT, MessageType.GAME_MESSAGE, 1, messages));
		}
		sleepyMessage = true;
	}

	@Override
	public void onNotify(ViewEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ViewEvents.UPDATE_HUNGRY): {
				showHungryMessage();
				break;
			}
			case(ViewEvents.UPDATE_POOP): {
				showPoopMessage();
				break;
			}
			case(ViewEvents.UPDATE_SLEEPY): {
				showSleepyMessage();
				break;
			}
			case(ViewEvents.UPDATE_LINKMON_SPRITE): {
				showEvolveMessage();
				break;
			}
		}
	}

}
