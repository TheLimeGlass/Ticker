package me.limeglass.ticker.objects.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AverageTpsChangeEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private final double tps, pastTps;
	
	public AverageTpsChangeEvent(double pastTps, double tps) {
		this.pastTps = pastTps;
		this.tps = tps;
	}
	
	public double getTps() {
		return tps;
	}
	
	public double getPastTps() {
		return pastTps;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}