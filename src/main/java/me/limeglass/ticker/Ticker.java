package me.limeglass.ticker;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.limeglass.ticker.elements.Register;
import me.limeglass.ticker.tasks.TpsTask;
import me.limeglass.ticker.utils.Utils;

public class Ticker extends JavaPlugin {
	
	public FileConfiguration config = getConfig();
	public static File syntaxFile;
	private Metrics metrics;
	private SkriptAddon addonInstance;
	private final int interval = 20;
	private static Ticker instance;
	private static FileConfiguration syntaxData;
	private static String packageName = "me.limeglass.ticker";
	private static String prefix = "&8[&eTicker&8] &6";
	private static String nameplate = "[Ticker] ";
	
	public void onEnable(){
		addonInstance = Skript.registerAddon(this).setLanguageFileDirectory("lang");
		instance = this;
		long time = (config.getBoolean("ServerStartupQueue", true)) ? 130L : 1L;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new TpsTask(), time, interval);
		File file = new File(getDataFolder(), "config.yml");
		syntaxFile = new File(getDataFolder(), "Syntax.yml");
		if (!Objects.equals(getDescription().getVersion(), config.getString("version"))) {
			consoleMessage("New update found! Updating files now...");
			if (file.exists()) file.delete();
		}
		for (File f : Arrays.asList(file, syntaxFile)) {
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				saveResource(f.getName(), false);
			}
		}
		syntaxData = new YamlConfiguration();
		try {
			syntaxData.load(syntaxFile);
			addonInstance.loadClasses(getPackageName(), "elements");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		metrics = new Metrics(this);
		Register.metrics(metrics);
		new Register();
		if (!config.getBoolean("DisableRegisteredInfo", false)) Bukkit.getLogger().info(nameplate + "has been enabled!");
	}
	
	public static Ticker getInstance() {
		return instance;
	}
	
	public SkriptAddon getAddonInstance() {
		return addonInstance;
	}
	
	public Metrics getMetrics() {
		return metrics;
	}
	
	public static String getPackageName() {
		return packageName;
	}
	
	public static String getNameplate() {
		return nameplate;
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static FileConfiguration getSyntaxData() {
		return syntaxData;
	}
	
	public static void debugMessage(String text) {
		if (instance.getConfig().getBoolean("debug")) consoleMessage("&b" + text);
	}

	public static void consoleMessage(String... messages) {
		for (String text : messages) Bukkit.getConsoleSender().sendMessage(Utils.cc(prefix + text));
	}
}
