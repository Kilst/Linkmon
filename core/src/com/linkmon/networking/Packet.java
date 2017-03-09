package com.linkmon.networking;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.aonevonebattle.moves.MoveFactory;
import com.linkmon.model.battles.BattleLinkmon;
import com.linkmon.model.linkmon.Move;

public class Packet {
	
	private byte[] packet;
	public byte size;
	
	public Packet(BattleLinkmon linkmon) {
		
		packet = new BattleSetupPacket(linkmon.getId(), linkmon.getAttack(), linkmon.getDefense(), linkmon.getSpeed(), linkmon.getMove1(), linkmon.getMove2(), linkmon.getHealth(), "Kilst").packet;
	}
	
	public Packet(int moveId, boolean b) {
		packet = new AttackPacket(moveId).packet;
	}
	
	public Packet(int id) {
		switch(id) {
			case PacketType.CONNECT : {
				packet = new ConnectPacket().packet;
				break;
			}
			case PacketType.DISCONNECT : {
				packet = new DisconnectPacket().packet;
				break;
			}
			case PacketType.BATTLE_SETUP : {
				//packet = new BattleSetupPacket(linkmonId, attack, defense, speed, move1Id, move2Id, health, playerName).packet;
				break;
			}
			case PacketType.SEND_MOVE : {
				packet = new AttackPacket(id).packet;
				break;
			}
			case PacketType.SEARCH_OPPONENTS : {
				packet = new SearchOpponentsPacket().packet;
				break;
			}
			case PacketType.HEARTBEAT : {
				packet = new HeartBeatPacket().packet;
				break;
			}
			case PacketType.MYSTERY_GIFT : {
				packet = new MysteryGiftPacket().packet;
				break;
			}
			case PacketType.CANCEL_SEARCH : {
				packet = new CancelSearchPacket().packet;
				break;
			}
		}
	}
	
	public byte[] getPacketBytes() {
		return packet;
	}
	
	public static class HeartBeatPacket {
		
		public byte[] packet;
		
		public byte packetId = 99; // 0
		public byte size = 2; // 1
		
		public HeartBeatPacket() {
			packet = new byte[2];
			packet[0] = packetId;
			packet[1] = size;
		}
	}
	
	public static class ConnectPacket {
		
		public byte[] packet;
		
		public byte packetId = 1; // 0
		public byte size = 2; // 1
		
		public ConnectPacket() {
			packet = new byte[2];
			packet[0] = packetId;
			packet[1] = size;
		}
	}
	
	public static class CancelSearchPacket {
		
		public byte[] packet;
		
		public byte packetId = 9; // 0
		public byte size = 2; // 1
		
		public CancelSearchPacket() {
			packet = new byte[2];
			packet[0] = packetId;
			packet[1] = size;
		}
	}
	
	public static class DisconnectPacket {
		
		public byte[] packet;
		
		public byte packetId = 0; // 0
		public byte size = 2; // 1
		
		public DisconnectPacket() {
			packet = new byte[2];
			packet[0] = packetId;
			packet[1] = size;
		}
	}
	
	public static class MysteryGiftPacket {
		
		public byte[] packet;
		
		public byte packetId = 6; // 0
		public byte size = 2; // 1
		
		public MysteryGiftPacket() {
			packet = new byte[2];
			packet[0] = packetId;
			packet[1] = size;
		}
	}
	
	public static class SearchOpponentsPacket {
		
		public byte[] packet;
		
		public byte packetId = 5; // 0
		public byte size = 2; // 1
		
		public SearchOpponentsPacket() {
			packet = new byte[2];
			packet[0] = packetId;
			packet[1] = size;
		}
	}
	
	public static class BattleSetupPacket {
		
		public byte[] packet;
		
		public byte packetId = 2; // 0
		public byte size = 31;     // 1
		public int linkmonId;	  // 2
		public int attack;		  // 6
		public int defense;		  // 10
		public int speed;		  // 14
		public int move1Id;		  // 18
		public int move2Id;       // 22
		public int health;        // 26
		public String playerName; // 30
		
		public BattleSetupPacket(int linkmonId, int attack, int defense, int speed, int move1Id, int move2Id, int health, String playerName) {

			byte[] bytes;
//			this.packetId = 2; 			  // 0
			this.size = 31;         	  // 1
			this.linkmonId = linkmonId;	  // 2
			this.attack = attack;		  // 6
			this.defense = defense;		  // 10
			this.speed = speed;		  	  // 14
			this.move1Id = move1Id;		  // 18
			this.move2Id = move2Id;       // 22
			this.health = health;         // 26
			this.playerName = playerName; // 30
			
			packet = new byte[31];
			packet[0] = packetId;
			
			packet[1] = 31;
			
			bytes = intToByteArray(this.linkmonId);
			packet[2] = bytes[3];
			packet[3] = bytes[2];
			packet[4] = bytes[1];
			packet[5] = bytes[0];
			
			bytes = intToByteArray(this.attack);
			packet[6] = bytes[3];
			packet[7] = bytes[2];
			packet[8] = bytes[1];
			packet[9] = bytes[0];
			
			bytes = intToByteArray(this.defense);
			packet[10] = bytes[3];
			packet[11] = bytes[2];
			packet[12] = bytes[1];
			packet[13] = bytes[0];
			
			bytes = intToByteArray(this.speed);
			packet[14] = bytes[3];
			packet[15] = bytes[2];
			packet[16] = bytes[1];
			packet[17] = bytes[0];
			
			bytes = intToByteArray(this.move1Id);
			packet[18] = bytes[3];
			packet[19] = bytes[2];
			packet[23] = bytes[1];
			packet[21] = bytes[0];
			
			bytes = intToByteArray(this.move2Id);
			packet[22] = bytes[3];
			packet[23] = bytes[2];
			packet[24] = bytes[1];
			packet[25] = bytes[0];
			
			bytes = intToByteArray(this.health);
			packet[26] = bytes[3];
			packet[27] = bytes[2];
			packet[28] = bytes[1];
			packet[29] = bytes[0];
			
			packet[30] = 0; // PlayerName
		}
	}
	
	public class AttackPacket {
		
		public byte[] packet;
		
		private byte packetId = 3; // 0
		public byte size = 6;     // 1
		private int moveId;		  // 2
		
		public AttackPacket(int moveId) {

			byte[] bytes;
			this.moveId = moveId;
			
			packet = new byte[6];
			packet[0] = packetId;
			packet[1] = size;
			
			bytes = intToByteArray(this.moveId);
			packet[2] = bytes[3];
			packet[3] = bytes[2];
			packet[4] = bytes[1];
			packet[5] = bytes[0];			
		}
	}
	
	public static int[] getRewardsFromPacket(byte[] packet) {
		
		int[] rewards = new int[5]; 
		
		byte[] bytes = new byte[4];
		
		bytes[0] = packet[2];
		bytes[1] = packet[3]; 
		bytes[2] = packet[4];
		bytes[3] = packet[5];
		rewards[0] = byteArrayToInt(bytes);
		
		bytes[0] = packet[6];
		bytes[1] = packet[7];
		bytes[2] = packet[8];
		bytes[3] = packet[9];
		rewards[1] = byteArrayToInt(bytes);
		
		bytes[0] = packet[10];
		bytes[1] = packet[11]; 
		bytes[2] = packet[12];
		bytes[3] = packet[13];
		rewards[2] = byteArrayToInt(bytes);
		
		bytes[0] = packet[14];
		bytes[1] = packet[15];
		bytes[2] = packet[16];
		bytes[3] = packet[17];
		rewards[3] = byteArrayToInt(bytes);
		
		bytes[0] = packet[18];
		bytes[1] = packet[19];
		bytes[2] = packet[20];
		bytes[3] = packet[21];
		rewards[4] = byteArrayToInt(bytes);
		
		return rewards;
	}
	
	public static int[] healthFromPacket(byte[] packet) {
		
		int[] health = new int[2];
		
		byte[] bytes = new byte[4];
		
		bytes[3] = packet[2];
		bytes[2] = packet[3]; 
		bytes[1] = packet[4];
		bytes[0] = packet[5];
		health[0] = byteArrayToInt(bytes);
		
		bytes[3] = packet[6];
		bytes[2] = packet[7];
		bytes[1] = packet[8];
		bytes[0] = packet[9];
		health[1] = byteArrayToInt(bytes);
		return health;
	}
	
	public static int[] damageFromPacket(byte[] packet) {
		
		int[] damages = new int[2];
		
		byte[] bytes = new byte[4];
		
		bytes[3] = packet[10];
		bytes[2] = packet[11]; 
		bytes[1] = packet[12];
		bytes[0] = packet[13];
		damages[0] = byteArrayToInt(bytes);
		
		bytes[3] = packet[14];
		bytes[2] = packet[15];
		bytes[1] = packet[16];
		bytes[0] = packet[17];
		damages[1] = byteArrayToInt(bytes);
		return damages;
	}
	
	public static int[] dodgeFromPacket(byte[] packet) {
		
		int[] dodges = new int[2];
		
		byte[] bytes = new byte[4];
		
		bytes[3] = packet[18];
		bytes[2] = packet[19]; 
		bytes[1] = packet[20];
		bytes[0] = packet[21];
		dodges[0] = byteArrayToInt(bytes);
		
		bytes[3] = packet[22];
		bytes[2] = packet[23];
		bytes[1] = packet[24];
		bytes[0] = packet[25];
		dodges[1] = byteArrayToInt(bytes);
		return dodges;
	}
	
	public static byte firstFromPacket(byte[] packet) {
		
		byte first = packet[34];
		return first;
	}
	
	public static int[] movesFromPacket(byte[] packet) {
		
		int[] moves = new int[2];
		moves[0] = packet[35] & 0xff; // Conversion to unsigned byte. Should've probably just send an int instead..
		Gdx.app.log("Packet", "Move1 ID: " + moves[0]);
		moves[1] = packet[36] & 0xff;
		Gdx.app.log("Packet", "Move2 ID: " + moves[1]);
		return moves;
	}
	
	public static int[] energiesFromPacket(byte[] packet) {
		
		int[] energies = new int[2];
		
		byte[] bytes = new byte[4];
		
		bytes[3] = packet[26];
		bytes[2] = packet[27]; 
		bytes[1] = packet[28];
		bytes[0] = packet[29];
		energies[0] = byteArrayToInt(bytes);
		
		bytes[3] = packet[30];
		bytes[2] = packet[31];
		bytes[1] = packet[32];
		bytes[0] = packet[33];
		energies[1] = byteArrayToInt(bytes);
		return energies;
	}
	
	public static BattleLinkmon linkmonFromPacket(byte[] packet) {
		
		byte[] bytes = new byte[4];
		
		bytes[3] = packet[2];
		bytes[2] = packet[3]; 
		bytes[1] = packet[4];
		bytes[0] = packet[5];
		int linkmonId = byteArrayToInt(bytes);
		
		bytes[3] = packet[6];
		bytes[2] = packet[7]; 
		bytes[1] = packet[8]; 
		bytes[0] = packet[9]; 
		int attack = byteArrayToInt(bytes);
		
		bytes[3] = packet[10];
		bytes[2] = packet[11]; 
		bytes[1] = packet[12]; 
		bytes[0] = packet[13]; 
		int defense = byteArrayToInt(bytes);
		
		bytes[3] = packet[14];
		bytes[2] = packet[15]; 
		bytes[1] = packet[16]; 
		bytes[0] = packet[17]; 
		int speed = byteArrayToInt(bytes);
		
		bytes[3] = packet[18];
		bytes[2] = packet[19]; 
		bytes[1] = packet[20]; 
		bytes[0] = packet[21]; 
		int move1Id = byteArrayToInt(bytes);
		
		bytes[3] = packet[22];
		bytes[2] = packet[23]; 
		bytes[1] = packet[24]; 
		bytes[0] = packet[25]; 
		int move2Id = byteArrayToInt(bytes);
		
		bytes[3] = packet[26];
		bytes[2] = packet[27]; 
		bytes[1] = packet[28]; 
		bytes[0] = packet[29];
		int health = byteArrayToInt(bytes);
		String playerName = "Packet.java"; // 30
		
		
		return new BattleLinkmon(linkmonId, attack, defense, speed, move1Id, move2Id, health, playerName);
	}
	
	public static byte[] intToByteArray( final int i ) {
		return ByteBuffer.allocate(4).putInt(i).array();
	}
	
	public static int byteArrayToInt(byte[] bytes) {
	     return ByteBuffer.wrap(bytes).getInt();
	}
}
