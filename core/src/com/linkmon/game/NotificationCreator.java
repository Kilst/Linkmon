package com.linkmon.game;

import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewListener;

public class NotificationCreator implements ViewListener {
	
	public static PushNotification getPushNotification(int id, long time) {
		switch(id) {
			case(NotificationIds.POOP_NOTIFICATION) : {
				return new PushNotification(NotificationIds.POOP_NOTIFICATION, "Linkmon Poop", "Your Linkmon has pooped!", time);
			}
			case(NotificationIds.HUNGRY_NOTIFICATION) : {
				return new PushNotification(NotificationIds.HUNGRY_NOTIFICATION, "Linkmon Hungry", "Your Linkmon is hungry!", time);
			}
			case(NotificationIds.DEAD_NOTIFICATION) : {
				return new PushNotification(NotificationIds.DEAD_NOTIFICATION, "Linkmon Dead", "Your Linkmon has DIED! :(", time);
			}
			case(NotificationIds.MYSTERY_GIFT__NOTIFICATION) : {
				return new PushNotification(NotificationIds.MYSTERY_GIFT__NOTIFICATION, "Mystery Gift", "Timer up! Go collect that sweet gift!", time);
			}
			default : {
				return null;
			}
		}
	}

	@Override
	public void onNotify(ViewEvent event) {
		// TODO Auto-generated method stub
		
	}

}
