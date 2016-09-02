package com.linkmon.networking;

public class PacketType {
	public static final byte CONNECT = 1;
	public static final byte DISCONNECT = 0;
	public static final byte BATTLE_SETUP = 2;
	public static final byte SEND_MOVE = 3;
	public static final int UPDATE_HEALTH = 4;
	public static final int SEARCH_OPPONENTS = 5;
	public static final int HEARTBEAT = 99;
	public static final int MYSTERY_GIFT = 6;
	public static final int WIN_LOSS = 7;
	public static final int CANCEL_SEARCH = 9;
}
