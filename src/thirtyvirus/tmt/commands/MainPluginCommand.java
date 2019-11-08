package thirtyvirus.tmt.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import thirtyvirus.tmt.TooManyTrees;
import thirtyvirus.tmt.helpers.MenuUtils;
import thirtyvirus.tmt.helpers.Utilities;

import java.util.Arrays;

public class MainPluginCommand implements CommandExecutor{

    private TooManyTrees main = null;
    public MainPluginCommand(TooManyTrees main) { this.main = main; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // verify that the user has proper permissions
        if (!sender.hasPermission("tmt.user")) {
            Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
            return true;
        }

        try {

            switch (args[0].toLowerCase()) {
                case "help":
                    help(sender);
                    break;
                case "info":
                    info(sender);
                    break;

                // put plugin specific commands here
                case "toggle":
                    if (sender.hasPermission("tmt.admin")) {
                        TooManyTrees.enabled = !TooManyTrees.enabled;
                        sender.sendMessage("enabled: " + TooManyTrees.enabled);
                    }
                    else Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
                    break;
                case "scaffold":
                    if (sender.hasPermission("tmt.admin")) {
                        TooManyTrees.scaffold = !TooManyTrees.scaffold;
                        sender.sendMessage("scaffold enabled: " + TooManyTrees.enabled);
                    }
                    else Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
                    break;
                case "showcounter":
                    if (sender.hasPermission("tmt.admin")) {
                        TooManyTrees.scaffold = !TooManyTrees.showCounter;
                        sender.sendMessage("counter enabled: " + TooManyTrees.showCounter);
                    }
                    else Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
                    break;
                case "resetcounter":
                    if (sender.hasPermission("tmt.admin")) {
                        TooManyTrees.treeCounter = 0;
                        sender.sendMessage("counter set to zero");
                    }
                    else Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
                    break;
                case "random":
                    if (sender.hasPermission("tmt.admin")) {
                        TooManyTrees.randomSaplings = !TooManyTrees.randomSaplings;
                        sender.sendMessage("random saplings enabled: " + TooManyTrees.enabled);
                    }
                    else Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
                    break;
                case "reload":
                    if (sender.hasPermission("tmt.admin")) reload(sender);
                    else Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
                    break;
                default:
                    Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("not-a-command-message")));
                    help(sender);
                    break;
            }

        } catch(Exception e) {
            Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("formatting-error-message")));
        }

        return true;
    }

    private void info(CommandSender sender) {
        sender.sendMessage(TooManyTrees.prefix + ChatColor.GRAY + "Plugin Info");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GREEN + "Version " + main.getVersion() + " - By ThirtyVirus");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GREEN + "#TeamTrees");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.RESET + ChatColor.RED + "" + ChatColor.BOLD + "You" + ChatColor.WHITE + ChatColor.BOLD + "Tube" + ChatColor.GREEN + " - https://youtube.com/thirtyvirus");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.RESET + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Twitter" + ChatColor.GREEN + " - https://twitter.com/thirtyvirus");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.RESET + ChatColor.GOLD + "" + ChatColor.BOLD + "SpigotMC" + ChatColor.GREEN + " - https://spigotmc.org/members/179587/");
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
    }

    private void help(CommandSender sender) {
        sender.sendMessage(TooManyTrees.prefix + ChatColor.GRAY + "Commands");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/tmt help");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/tmt info");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/tmt reload");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/tmt toggle");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/tmt scaffold");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/tmt random");
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
    }

    private void reload(CommandSender sender) {
        main.reloadConfig();
        main.loadConfiguration();

        main.loadLangFile();

        Utilities.informPlayer(sender, Arrays.asList("configuration, values, and language settings reloaded"));
    }

}
