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
import me.limeglass.ticker.objects.events.TpsChangeEvent;

public class EvtTpsChange extends SkriptEvent {
	
	@Nullable
	private Literal<Number> tpsArgument;
	
	static {
		EventValues.registerEventValue(TpsChangeEvent.class, Number.class, new Getter<Number, TpsChangeEvent>() {
			@Override
			public Number get(TpsChangeEvent event) {
				return event.getTps();
			}
		}, 0);
		EventValues.registerEventValue(TpsChangeEvent.class, Number.class, new Getter<Number, TpsChangeEvent>() {
			@Override
			public Number get(TpsChangeEvent event) {
				return event.getTps();
			}
		}, 1);
		EventValues.registerEventValue(TpsChangeEvent.class, Number.class, new Getter<Number, TpsChangeEvent>() {
			@Override
			public Number get(TpsChangeEvent event) {
				return event.getPastTps();
			}
		}, -1);
		Events.registerEvent(EvtTpsChange.class, TpsChangeEvent.class, "tps change [to %-number%]");
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
				return ((TpsChangeEvent)event).getTps() == tps.doubleValue();
			}
		});
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "EvtTpsChange with argument: " + tpsArgument.toString(event, debug);
	}
}