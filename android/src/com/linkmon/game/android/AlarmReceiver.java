package com.linkmon.game.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {
	
	public AlarmReceiver() {
		super();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent(context, NotificationService.class);
		
		Bundle bundle = intent.getExtras();
	    int id = bundle.getInt("id");
	    String title = bundle.getString("title");
	    String body = bundle.getString("body");
	    
	    myIntent.putExtra("id", id);
	    myIntent.putExtra("title", title);
	    myIntent.putExtra("body", body);
		   
		context.startService(myIntent);
	}


}
