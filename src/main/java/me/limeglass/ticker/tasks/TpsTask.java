package me.limeglass.ticker.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

public class TpsTask implements Runnable {

	private long millseconds = 0L;
	private static Map<Long, Double> tpsMap = new HashMap<Long, Double>();
	
	public static double getTps() {
		double tpsSum = 0.0D;
		for (Entry<Long, Double> pastTps : tpsMap.entrySet()) {
			tpsSum += pastTps.getValue();
		}
		return Math.round(tpsSum / 10.0D * 100.0D) / 100.0D;
	}

	public static String getStringTPS() {
		ChatColor colour = ChatColor.GREEN;
	    if (getTps() > 13.0D && getTps() < 17.0D) colour = ChatColor.GOLD;
	    if (getTps() < 13.0D) colour = ChatColor.RED;
		return colour + String.valueOf(getTps());
	}
	
	public void run() {
		if (millseconds > 0L) {
			double diff = System.currentTimeMillis() - millseconds - 1000.0D;
			if (diff < 0.0D) {
				diff = Math.abs(diff);
			}
			double tps;
			if (diff == 0.0D) {
				tps = 20.0D;
			} else {
				tps = 20.0D - diff / 50.0D;
			}
			if (tps < 0.0D) {
				tps = 0.0D;
			}
			tpsMap.put(System.currentTimeMillis(), tps);
			//call an event after running 10 or so times.
		}
		millseconds = System.currentTimeMillis();
	}
}