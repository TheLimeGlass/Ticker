package me.limeglass.ticker.elements.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import me.limeglass.ticker.lang.TickerExpression;
import me.limeglass.ticker.objects.events.TpsChangeEvent;
import me.limeglass.ticker.utils.annotations.Events;
import me.limeglass.ticker.utils.annotations.ExpressionProperty;
import me.limeglass.ticker.utils.annotations.Patterns;
import me.limeglass.ticker.utils.annotations.Single;

@Name("Past Tps")
@Description("Returns the server's past tps in the tps change event.")
@Patterns("[the] [server[[']s]] (past|previous) tps")
@ExpressionProperty(ExpressionType.SIMPLE)
@Events(TpsChangeEvent.class)
@Single
public class ExprEventPastTps extends TickerExpression<Number> {
	
	@Override
	protected Number[] get(Event event) {
		return new Number[] {((TpsChangeEvent)event).getPastTps()};
	}
}