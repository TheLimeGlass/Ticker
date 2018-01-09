package me.limeglass.ticker.elements.events;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.util.Checker;
import me.limeglass.ticker.elements.Events;
import me.limeglass.ticker.objects.events.AverageTpsChangeEvent;

public class EvtAverageTpsChange extends SkriptEvent {
	
	@Nullable
	private Literal<Number> tpsArgument;
	
	static {
		EventValues.registerEventValue(AverageTpsChangeEvent.class, Number.class, new Getter<Number, AverageTpsChangeEvent>() {
			@Override
			public Number get(AverageTpsChangeEvent event) {
				return event.getTps();
			}
		}, 0);
		EventValues.registerEventValue(AverageTpsChangeEvent.class, Number.class, new Getter<Number, AverageTpsChangeEvent>() {
			@Override
			public Number get(AverageTpsChangeEvent event) {
				return event.getTps();
			}
		}, 1);
		EventValues.registerEventValue(AverageTpsChangeEvent.class, Number.class, new Getter<Number, AverageTpsChangeEvent>() {
			@Override
			public Number get(AverageTpsChangeEvent event) {
				return event.getPastTps();
			}
		}, -1);
		Events.registerEvent(EvtAverageTpsChange.class, AverageTpsChangeEvent.class, "average tps change [to %-number%]");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		tpsArgument = (Literal<Number>) args[0];
		return true;
	}
	
	@Override
	public boolean check(Event event) {
		if (tpsArgument == null) return true;
		return tpsArgument.check(event, new Checker<Number>() {
			@Override
			public boolean check(final Number tps) {
				return ((AverageTpsChangeEvent)event).getTps() == tps.doubleValue();
			}
		});
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "EvtAverageTpsChange with argument: " + tpsArgument.toString(event, debug);
	}
}