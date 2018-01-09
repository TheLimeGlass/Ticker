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

@Name("Bukkit Tps String")
@Description("Returns the server's tps from Bukkit.")
@Patterns("[the] bukkit[[']s] tps [string]")
@ExpressionProperty(ExpressionType.SIMPLE)
@Single
public class ExprBukkitTpsString extends TickerExpression<String> {
	
	@Override
	protected String[] get(Event event) {
		return new String[] {TpsTask.getBukkitTps()};
	}
}