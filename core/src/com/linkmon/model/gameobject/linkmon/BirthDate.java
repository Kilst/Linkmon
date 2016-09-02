package com.linkmon.model.gameobject.linkmon;

import java.util.Calendar;

public class BirthDate {
	
	private int day;
	private int month;
	private int year;
	
	private int hour;
	private int minute;
	
	private long nano;
	
	public BirthDate() {
		
	}
	
	public BirthDate(Linkmon linkmon) {
		
		Calendar calendar = Calendar.getInstance();
		
		day = calendar.get(Calendar.DAY_OF_MONTH);
		month = calendar.get(Calendar.MONTH)+1; // Jan starts at 0 for some reason..
		year = calendar.get(Calendar.YEAR);
		
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		
		nano = System.nanoTime();
		
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public long getNano() {
		return nano;
	}

}
