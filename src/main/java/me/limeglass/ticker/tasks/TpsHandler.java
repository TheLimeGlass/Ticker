package me.limeglass.ticker.tasks;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import me.limeglass.ticker.Ticker;
import me.limeglass.ticker.objects.events.AverageTpsChangeEvent;
import me.limeglass.ticker.objects.events.TpsChangeEvent;
import me.limeglass.ticker.utils.ReflectionUtil;

public class TpsHandler implements Runnable {

	private long millseconds = 0L;
	private static double lastTps, tps, lastAverageTps;
	public static Map<Long, Double> tpsMap = new HashMap<Long, Double>();
	
	public static double getAverageTps() {
		double tpsSum = 0.0D;
		for (Entry<Long, Double> pastTps : tpsMap.entrySet()) {
			tpsSum += pastTps.getValue();
		}
		return Math.round(tpsSum / tpsMap.entrySet().size() * 100.0D) / 100.0D;
	}
	
	public static String getStringAverageTps() {
		ChatColor colour = ChatColor.GREEN;
		if (getAverageTps() > 13.0D && getAverageTps() < 17.0D) colour = ChatColor.GOLD;
		if (getAverageTps() < 13.0D) colour = ChatColor.RED;
		return colour + String.valueOf(getAverageTps());
	}
	
	public static double getLastTps() {
		if (tpsMap.isEmpty()) return 0.0D;
		return lastTps;
	}
	
	public static double getTps() {
		if (tpsMap.isEmpty()) return 0.0D;
		return tps;
	}
	
	public static double getTpsNear(long timestamp) {
		double tps = 0.0D;
		for (Entry<Long, Double> entry : tpsMap.entrySet()) {
			long calculate = timestamp - entry.getKey();
			if (calculate >= 0 && calculate < 2000) {
				tps = entry.getValue();
			}
		}
		return tps;
	}
	
	public static String getBukkitTps() {
		try {
			Class<?> minecraftServer = ReflectionUtil.getNMSClass("MinecraftServer");
			Object server = minecraftServer.getMethod("getServer", new Class[0]).invoke(null, new Object[0]);
			Field field = server.getClass().getField("recentTps");
			DecimalFormat format = new DecimalFormat("##.##");
			double[] tps = null;
			tps = (double[])field.get(server);
			String ftps = "";
			if (Double.compare(tps[0], 20.0D) > 0) {
				ftps = String.valueOf(20.0D);
			} else {
				ftps = format.format(tps[0]);
			}
			return ftps;
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException exception) {
			Ticker.consoleMessage("The recentTps field doesn't exist for your version. Please refrain from using the Bukkit tps expression.");
			exception.printStackTrace();
		}
		return null;
	}

	public static String getStringTps() {
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
			if (diff == 0.0D) {
				tps = 20.0D;
			} else {
				tps = 20.0D - diff / 50.0D;
			}
			if (tps < 0.0D) {
				tps = 0.0D;
			}
			if (!tpsMap.isEmpty()) {
				if (lastTps != tps) {
					Bukkit.getPluginManager().callEvent(new TpsChangeEvent(lastTps, tps));
				}
				if (lastAverageTps != getAverageTps()) {
					Bukkit.getPluginManager().callEvent(new AverageTpsChangeEvent(lastAverageTps, getAverageTps()));
				}
			}
			lastAverageTps = getAverageTps();
			tpsMap.put(System.currentTimeMillis(), tps);
			lastTps = tps;
		}
		millseconds = System.currentTimeMillis();
	}
}