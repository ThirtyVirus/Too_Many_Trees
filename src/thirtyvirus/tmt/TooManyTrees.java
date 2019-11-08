package thirtyvirus.tmt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import thirtyvirus.tmt.commands.MainPluginCommand;
import thirtyvirus.tmt.events.block.BlockClick;
import thirtyvirus.tmt.events.chat.TabComplete;
import thirtyvirus.tmt.events.inventory.InventoryClick;
import thirtyvirus.tmt.helpers.Utilities;

import java.io.File;
import java.util.*;

public class TooManyTrees extends JavaPlugin {

    // console and IO
    private File langFile;
    private FileConfiguration langFileConfig;

    // chat messages
    private Map<String, String> phrases = new HashMap<String, String>();

    // core settings
    public static String prefix = "&c&l[&5&lTooManyTrees&c&l] &8&l"; // generally unchanged unless otherwise stated in config
    public static String consolePrefix = "[TooManyTrees] ";

    // customizable settings
    public static boolean customSetting = false;

    public static boolean enabled = false;
    public static boolean randomSaplings = false;
    public static boolean scaffold = false;
    public static int treeCounter = 0;
    public static boolean showCounter = false;

    public void onEnable(){
        // load config.yml (generate one if not there)
        loadConfiguration();

        // load language.yml (generate one if not there)
        loadLangFile();

        // register commands and events
        registerCommands();
        registerEvents();

        // posts confirmation in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been enabled");

        // scheduled task
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                Utilities.treePlaceTick();
            }
        }, 2, 2);

    }

    public void onDisable(){
        // posts exit message in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been disabled");
    }

    private void registerCommands() {
        getCommand("tmt").setExecutor(new MainPluginCommand(this));

        // set up tab completion
        getCommand("tmt").setTabCompleter(new TabComplete(this));
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new BlockClick(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
    }

    // load the config file and apply settings
    public void loadConfiguration() {
        // prepare config.yml (generate one if not there)
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()){
            Utilities.loadResource(this, "config.yml");
        }
        FileConfiguration config = this.getConfig();

        // general settings
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("plugin-prefix"));

        customSetting = config.getBoolean("custom-setting");
        // put more settings here


        Bukkit.getLogger().info(consolePrefix + "Settings Reloaded from config");
    }

    // load the language file and apply settings
    public void loadLangFile() {

        // load language.yml (generate one if not there)
        langFile = new File(getDataFolder(), "language.yml");
        langFileConfig = new YamlConfiguration();
        if (!langFile.exists()){ Utilities.loadResource(this, "language.yml"); }

        try { langFileConfig.load(langFile); }
        catch (Exception e3) { e3.printStackTrace(); }

        for(String priceString : langFileConfig.getKeys(false)) {
            phrases.put(priceString, langFileConfig.getString(priceString));
        }
    }

    // getters
    public String getPhrase(String key) {
        return phrases.get(key);
    }
    public String getVersion() {
        return getDescription().getVersion();
    }

}
