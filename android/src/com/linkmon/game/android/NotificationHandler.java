package com.linkmon.game.android;

import java.util.List;

import com.linkmon.game.INotify;
import com.linkmon.game.PushNotification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class NotificationHandler implements INotify {
	
	public static Activity activity;
	
	public static AlarmManager alarmManager;
	
	public Intent alarmintent;
	public PendingIntent sender;
	
	public NotificationHandler() {
		super();
	}
	
	
	public NotificationHandler(Activity activity) {
		
		if(alarmManager == null)
			alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
		NotificationHandler.activity = activity;
		
		Toast.makeText(activity.getApplicationContext(),"Creating Alarm Class", Toast.LENGTH_SHORT).show();
	}
	
	public void clearNotification() {
		alarmintent = new Intent(activity.getApplicationContext(), AlarmReceiver.class);
		sender = PendingIntent.getBroadcast(activity, 1, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		alarmManager.cancel(sender);
	}
	
	@SuppressLint("NewApi")
	public void sendNotification(List<PushNotification> pushList) {
		for(PushNotification push : pushList) {
			Log.d("NOTIFY", "Sending notification  id: "+ push.id);
			alarmintent = new Intent(activity.getApplicationContext(), AlarmReceiver.class);
			alarmintent.putExtra("id", push.id);
			alarmintent.putExtra("title", push.title);
			alarmintent.putExtra("body", push.body);
			sender = PendingIntent.getBroadcast(activity, push.id, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			Log.d("NOTIFY", "Trying Alarm");
			
			alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + push.milliTime, sender);
			
			Log.d("NOTIFY", "Sent  id: "+ push.id);
		}

	}

}
