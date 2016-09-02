package com.linkmon.helpers;

import com.badlogic.gdx.Gdx;

public class Timer 
{
	private long firstFinish = 0;
	
	private long startTime = 0;
	
	private long endTime = 0;
	
	public long elapsedTime = 0;
	
	public double elapsedSeconds = 0;
	
	public boolean ended = true;
	
	public boolean stopped = true;
	
	private boolean loopForever = false;
	
	private int loopCount = 0;
	
	private String name; // For testing only
	
	public Timer(long finish, boolean loopForever) {
		finish = (long) (finish * 1E9);
		endTime = finish;
		elapsedTime = 0;
		elapsedSeconds = 0;
		this.loopForever = loopForever;
	}
	
	public Timer(long finish, int loopCount, String name)
	{
		finish = (long) (finish * 1E9);
		endTime = finish; // 2 mins
		elapsedTime = 0;
		elapsedSeconds = 0;
		this.loopCount = loopCount;
	}
	
	public Timer(long firstFinish, long finish, boolean loopForever)
	{
		this.firstFinish = (long) (firstFinish * 1E9);
		finish = (long) (finish * 1E9);
		endTime = finish; // 2 mins
		elapsedTime = 0;
		elapsedSeconds = 0;
		this.loopForever = loopForever;
	}
	
	public void start() {
		startTime = System.nanoTime();
		elapsedTime = 0;
		elapsedSeconds = 0;
		ended = false;
		stopped = false;
	}
	
	public void restart() {
		startTime = System.nanoTime();
		elapsedTime = 0;
		elapsedSeconds = 0;
		ended = false;
		stopped = false;
	}
	
	public void stop() {
		stopped = true;
		ended = false;
		startTime = 0;
		endTime = 0;
		elapsedTime = 0;
		elapsedSeconds = 0;
		loopForever = false;
		loopCount = 0;
	}
	
	public boolean checkTimer()
	{
		if(stopped)
			return false;
		elapsedTime = System.nanoTime() - startTime;
		if(firstFinish != 0) {
			if (elapsedTime > firstFinish)
			{
				firstFinish = 0;
				ended = true;
				if(loopForever) {
					startTime = System.nanoTime();
				}
				else if(loopCount > 1) {
					startTime = System.nanoTime();
					loopCount--;
				}
				return ended;
			}
			
		}
		if (elapsedTime > endTime)
		{
			ended = true;
			if(loopForever) {
				startTime = System.nanoTime();
			}
			else if(loopCount > 1) {
				startTime = System.nanoTime();
				loopCount--;
			}
			return ended;
		}
		else {
			ended = false;
			return ended;
		}
	}
	
	public int getTotalElapsedTime()
	{
		elapsedTime = System.nanoTime() - startTime;
		return (int) (elapsedTime / 1E9);
	}
	
}
