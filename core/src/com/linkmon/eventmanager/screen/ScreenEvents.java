package com.linkmon.eventmanager.screen;

public class ScreenEvents {
	
	// Controllers
	public static final int SWAP_SCREEN = 0;
	public static final int GET_PLAYER_STATS = 1;
	public static final int GET_LINKMON_STATS = 2;
	public static final int GET_PLAYER_ITEMS = 3;
	public static final int GET_SHOP_ITEMS = 4;
	public static final int FEED_LINKMON = 5;
	public static final int BUY_ITEM = 6;
	public static final int TRAIN_LINKMON = 7;
	public static final int GET_LINKMON_ADDED_STATS = 8;
	public static final int SWAP_SCREEN_PREVIOUS = 9;
	public static final int USE_ITEM = 10;
	public static final int GET_LINKMON_MOVES = 11;
	public static final int GET_CHOOSABLE_MOVES = 12;
	public static final int SWAP_MOVE = 13;
	public static final int GET_SAVED_LINKMON_STATS = 14;
	public static final int SAVE_BATTLE_LINKMON = 15;
	public static final int ADD_PLAYER_GOLD = 16;
	
	
	public static final int LIGHT_SWAP = 99;
	
	public static final int LOCAL_PLAYER_MOVE = 41;
	public static final int LOCAL_PLAYER_TARGET = 42;
	public static final int LOCAL_PLAY_TURN = 43;
	public static final int LOCAL_BATTLE_CREATE = 44;
	public static final int LOCAL_BATTLE_UPDATE_HEALTHS = 45;
	public static final int START_LOCAL_BATTLE = 46;
	public static final int RETURN_TO_MAIN_GAME = 47;
	public static final int LOCAL_BATTLE_GET_MOVES = 48;

	
	// Networking
	public static final int CONNECT_TO_SERVER = 100;
	public static final int SEARCH_FOR_OPPONENT = 101;
	public static final int SEND_MOVE = 102;
	public static final int CLOSE_CONNECTION = 103;
	public static final int GET_MYSTERY_GIFT = 104;
	public static final int CANCEL_SEARCH = 105;
	public static final int GET_SERVER_WELCOME = 106;
	public static final int GET_ONLINE_SPRITES = 107;
	public static final int UPDATE_ONLINE_BALLTE = 108;
	public static final int GET_ONLINE_STATS = 109;
	
	// MiniGames
	public static final int MOVE_PLAYER = 200;
	public static final int START_MINIGAME = 201;
	public static final int RESTART_MINI_GAME = 202;
	public static final int OPEN_MINIGAME = 203;
	
	
	// Debugging
	public static final int DEBUGGING = 1001;
	public static final int DEBUGGING_SPAWNER = 1002;
	
	
	// Particle Effects
	public static final int ADD_PARTICLE_EFFECT = 2000;
	
	
	// Sound
	public static final int PLAY_MAIN_GAME_MUSIC = 3000;
	public static final int PLAY_MENU_MUSIC = 3001;
	public static final int PLAY_BATTLE_MUSIC = 3002;
	public static final int PLAY_SHOP_MUSIC = 3003;
	
	
	
	public static final int PLAY_BUTTON_DECLINE = 3004;
	public static final int PLAY_BUTTON_ACCEPT = 3005;
	public static final int PLAY_THEME_MUSIC = 3006;
	public static final int PLAY_BATTLE_TOWER_MUSIC = 3007;
	public static final int PLAY_DING = 3008;

}
