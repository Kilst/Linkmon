package com.linkmon.helpers;

public class CompleteMessageFactory {
	
	public static CompleteMessage getMessageFromId(int id) {
		switch(id) {
			case(CompleteMessageId.LINKMON_FULL_ERROR) : {
				return new CompleteMessage(false, "Full Error", "Your Linkmon is still full!");
			}
			case(CompleteMessageId.ONLY_IN_BATTLE_ERROR) : {
				return new CompleteMessage(false, "Item Error", "can only be used in battle!");
			}
			case(CompleteMessageId.NOT_SICK_ERROR) : {
				return new CompleteMessage(false, "Item Error", "You Linkmon is not sick!");
			}
			case(CompleteMessageId.NOT_DEAD_EROR) : {
				return new CompleteMessage(false, "Item Error", "Your Linkmon isn't dead!");
			}
			
		}
		
		return null;
	}

}
