package me.limeglass.ticker.elements.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import me.limeglass.ticker.lang.TickerExpression;
import me.limeglass.ticker.tasks.TpsTask;
import me.limeglass.ticker.utils.annotations.ExpressionProperty;
import me.limeglass.ticker.utils.annotations.Patterns;
import me.limeglass.ticker.utils.annotations.Single;

@Name("Last Tps")
@Description("Returns the server's last tps from 20 ticks ago.")
@Patterns("[the] [server[[']s]] last tps")
@ExpressionProperty(ExpressionType.SIMPLE)
@Single
public class ExprLastTps extends TickerExpression<Number> {
	
	@Override
	protected Number[] get(Event event) {
		return new Number[] {TpsTask.getLastTps()};
	}
}