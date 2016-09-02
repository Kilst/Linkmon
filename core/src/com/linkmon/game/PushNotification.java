package com.linkmon.game;

public class PushNotification {
	
	public int id;
	public String title;
	public String body;
	
	public long milliTime;
	
	public PushNotification(int id, String title, String body, long timerLength) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.milliTime = timerLength;
	}

}
