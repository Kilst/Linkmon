package com.linkmon.helpers;

public class CompleteMessage {
	
	public boolean completed;
	
	public String heading;
	public String text;
	
	public CompleteMessage(boolean completed) {
		this.completed = completed;
	}
	
	public CompleteMessage(boolean completed, String heading, String text) {
		this.completed = completed;
		this.heading = heading;
		this.text = text;
	}
}
