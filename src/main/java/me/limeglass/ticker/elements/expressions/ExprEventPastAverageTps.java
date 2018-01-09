package me.limeglass.ticker.elements.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import me.limeglass.ticker.lang.TickerExpression;
import me.limeglass.ticker.objects.events.AverageTpsChangeEvent;
import me.limeglass.ticker.utils.annotations.Events;
import me.limeglass.ticker.utils.annotations.ExpressionProperty;
import me.limeglass.ticker.utils.annotations.Patterns;
import me.limeglass.ticker.utils.annotations.Single;

@Name("Past Average Tps")
@Description("Returns the server's past average tps in the average tps change event.")
@Patterns("[the] [server[[']s]] (past|previous) average tps")
@ExpressionProperty(ExpressionType.SIMPLE)
@Events(AverageTpsChangeEvent.class)
@Single
public class ExprEventPastAverageTps extends TickerExpression<Number> {
	
	@Override
	protected Number[] get(Event event) {
		return new Number[] {((AverageTpsChangeEvent)event).getPastTps()};
	}
}