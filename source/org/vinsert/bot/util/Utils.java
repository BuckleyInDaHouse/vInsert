package org.vinsert.bot.util;

import java.util.Random;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Generic utilities
 * @author tommo
 *
 */
public class Utils {
	
	private static final Random random;

	static {
		random = getMachineSeededRandom();
	}



	private static final java.util.Random getMachineSeededRandom() {
		long seed = 0L;
		try {
			byte[] mac = null;
			Enumeration<NetworkInterface> network = NetworkInterface.getNetworkInterfaces();
			while(network.hasMoreElements()) {
				NetworkInterface n = network.nextElement();
				byte[] addr = n.getHardwareAddress();
				if(addr != null) { mac = addr; break; }
			}
			
			long current = System.currentTimeMillis();
			byte mask_1 = (byte) (current);
			byte mask_2 = (byte) (current << 8);
			
			seed = (mask_1 << 56) + 
					(mac[1] << 48) +
					(mac[5] << 40) +
					(mac[3] << 32) +
					(mac[4] << 24) +
					(mac[2] << 16) +
					(mac[0] << 8) +
					mask_2;
								
		} catch (SocketException e) {
			System.err.println("Couldn't generate machine seeded random!");
			return new java.util.Random();
		}
		return new java.util.Random(seed);		  
	}



	/**
	 * Generates a random number between a min and max.
	 * 
	 * where 0 > max - min
	 * 
	 * @param min The minimum.
	 * @param max The maximum.
	 * @return The random value between the min and max values.
	 */
	public static int random(int min, int max) {
		return random.nextInt(Math.abs(max - min)) + min;
	}

	public static double randomD() {
		return random.nextDouble();
	}
	
	/**
	 * Returns a random element from the given array
	 * @param array
	 * @return
	 */
	public <T> T random(T[] array) {
		return array[random(0, array.length)];
	}
	
	/**
	 * Simulates sleeping for a given time
	 * 
	 * @param time
	 *            The time to sleep for in milliseconds
	 */
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * Simulates sleeping for a given time
	 * 
	 * @param time
	 *            The time to sleep for in milliseconds
	 */
	public static void sleep(int min, int max) {
		try {
			Thread.sleep(random(min, max));
		} catch (InterruptedException e) {
		}
	}

}
