package com.linkmon.game.android;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class NotificationService extends Service {

	private NotificationManager mManager;
	
	public NotificationService() {
		super();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

   @Override
   public void onCreate() {
	   super.onCreate();
	   mManager=(NotificationManager)this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
   }

   @SuppressLint("NewApi")
   	@Override
   	public void onStart(Intent intent, int startId) {
	   super.onStart(intent, startId);
		  // Getting Notification Service
	   mManager=(NotificationManager)this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		
	   Bundle bundle = intent.getExtras();
	   int id = bundle.getInt("id");
	   String title = bundle.getString("title");
	   String body = bundle.getString("body");
	   // TODO Auto-generated method stub
	   Notification.Builder mBuilder =
			   new Notification.Builder(this.getApplicationContext())
			   .setSmallIcon(R.drawable.baby_robot)
			   .setContentTitle(title)
			   .setContentText(body)
			   .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
			   .setAutoCancel(true)
			   .setWhen(System.currentTimeMillis());
	   
	   // Creates an explicit intent for an Activity in your app
	   //PendingIntent pending=PendingIntent.getActivity(activity.getApplicationContext(), 0, new Intent(),0);
		
	   mManager.notify(id, mBuilder.build());
   }

   @Override
   public void onDestroy() {
	   // TODO Auto-generated method stub
	   super.onDestroy();
   }
}