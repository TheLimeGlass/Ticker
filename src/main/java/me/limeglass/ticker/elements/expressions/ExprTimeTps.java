package me.limeglass.ticker.elements.expressions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.util.Date;
import me.limeglass.ticker.lang.TickerPropertyExpression;
import me.limeglass.ticker.tasks.TpsHandler;
import me.limeglass.ticker.utils.annotations.Properties;
import me.limeglass.ticker.utils.annotations.PropertiesAddition;

@Name("Time Tps")
@Description("Returns the server's tps at a time.")
@Properties({"date", "[the] [server[[']s]] tps[s]", "{1}[(all [[of] the]|the)]"})
@PropertiesAddition("[date[s]]")
public class ExprTimeTps extends TickerPropertyExpression<Date, Number> {

	@Override
	protected Number[] get(Event event, Date[] dates) {
		if (isNull(event)) return null;
		Set<Number> tps = new HashSet<Number>();
		for (Date date : dates) {
			tps.add(TpsHandler.getTpsNear(date.getTimestamp()));
		}
		return tps.toArray(new Number[tps.size()]);
	}
}