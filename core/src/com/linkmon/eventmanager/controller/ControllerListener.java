package com.linkmon.eventmanager.controller;

public interface ControllerListener {
	// Return boolean so we can halt loop if we want to (things like taps not propagating down through sprites)
	public boolean onNotify(ControllerEvent event);
}
