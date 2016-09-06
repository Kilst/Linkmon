package com.linkmon.componentmodel.linkmon;

import java.util.Calendar;

public class BirthDate {
	
	private int day;
	private int month;
	private int year;
	
	private int hour;
	private int minute;
	
	private long nano;
	
	public BirthDate() {
		
		Calendar calendar = Calendar.getInstance();
		
		day = calendar.get(Calendar.DAY_OF_MONTH);
		month = calendar.get(Calendar.MONTH)+1; // Jan starts at 0 for some reason..
		year = calendar.get(Calendar.YEAR);
		
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		
		nano = System.nanoTime();
		
	}

	public BirthDate(int hour2, int minute2, int day2, int month2, int year2, long nano2) {
		// TODO Auto-generated constructor stub
		day = day2;
		month = month2; // Jan starts at 0 for some reason..
		year = year2;
		
		hour = hour2;
		minute = minute2;
		
		nano = nano2;
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
