package thirtyvirus.tmt.helpers;

import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import thirtyvirus.multiversion.Sound;
import thirtyvirus.multiversion.Version;
import thirtyvirus.multiversion.XMaterial;
import thirtyvirus.tmt.TooManyTrees;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public final class Utilities {

    // list of transparent blocks to be ignored when a player looks at a block
    private static final Set<Material> TRANSPARENT = EnumSet.of(XMaterial.AIR.parseMaterial(), XMaterial.BLACK_CARPET.parseMaterial(), XMaterial.BLUE_CARPET.parseMaterial(),
            XMaterial.BROWN_CARPET.parseMaterial(), XMaterial.CYAN_CARPET.parseMaterial(), XMaterial.GRAY_CARPET.parseMaterial(), XMaterial.GREEN_CARPET.parseMaterial(), XMaterial.LIGHT_BLUE_CARPET.parseMaterial(),
            XMaterial.LIME_CARPET.parseMaterial(), XMaterial.MAGENTA_CARPET.parseMaterial(), XMaterial.ORANGE_CARPET.parseMaterial(), XMaterial.PINK_CARPET.parseMaterial(), XMaterial.PURPLE_CARPET.parseMaterial(),
            XMaterial.RED_CARPET.parseMaterial(), XMaterial.WHITE_CARPET.parseMaterial(), XMaterial.YELLOW_CARPET.parseMaterial());

    // list of all supported inventory blocks in the plugin
    public static final List<Material> INVENTORY_BLOCKS = Arrays.asList(XMaterial.CHEST.parseMaterial(),XMaterial.TRAPPED_CHEST.parseMaterial(), XMaterial.ENDER_CHEST.parseMaterial(), XMaterial.SHULKER_BOX.parseMaterial(), XMaterial.BLACK_SHULKER_BOX.parseMaterial(),
            XMaterial.BLUE_SHULKER_BOX.parseMaterial(), XMaterial.BROWN_SHULKER_BOX.parseMaterial(), XMaterial.CYAN_SHULKER_BOX.parseMaterial(), XMaterial.GRAY_SHULKER_BOX.parseMaterial(),
            XMaterial.GREEN_SHULKER_BOX.parseMaterial(), XMaterial.LIGHT_BLUE_SHULKER_BOX.parseMaterial(), XMaterial.LIGHT_GRAY_SHULKER_BOX.parseMaterial(), XMaterial.LIME_SHULKER_BOX.parseMaterial(),
            XMaterial.MAGENTA_SHULKER_BOX.parseMaterial(), XMaterial.ORANGE_SHULKER_BOX.parseMaterial(), XMaterial.PINK_SHULKER_BOX.parseMaterial(), XMaterial.PURPLE_SHULKER_BOX.parseMaterial(),
            XMaterial.RED_SHULKER_BOX.parseMaterial(), XMaterial.WHITE_SHULKER_BOX.parseMaterial(), XMaterial.YELLOW_SHULKER_BOX.parseMaterial());

    private static Map<Player, Long> mostRecentSelect = new HashMap<>();
    private static Random rand = new Random();
    private static final Set<Material> DONTPLACE = EnumSet.of(Material.AIR, Material.CAVE_AIR, Material.OAK_SAPLING, Material.WATER, Material.GRASS,
            Material.SPRUCE_SAPLING, Material.ACACIA_SAPLING, Material.BIRCH_SAPLING, Material.JUNGLE_SAPLING, Material.POPPY,
            Material.DANDELION);

            // load file from JAR with comments
    public static File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResource(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }

    // convert a location to formatted string (world,x,y,z)
    public static String toLocString(Location location) {
        if (location.equals(null)) return "";
        return location.getWorld().getName() + "," + (int)location.getX() + "," + (int)location.getY() + "," + (int)location.getZ();
    }

    // renames item
    public static ItemStack nameItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // creates item that is renamed given material and name
    public static ItemStack nameItem(Material item, String name) {
        return nameItem(new ItemStack(item), name);
    }

    // set the lore of an item
    public static ItemStack loreItem(ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    // makes visible string invisible to player
    public static String convertToInvisibleString(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR + "" + c;
        return hidden;
    }

    // make invisible string visible to player
    public static String convertToVisibleString(String s) {
        String c = "";
        c = c + ChatColor.COLOR_CHAR;
        return s.replaceAll(c, "");
    }

    // send player a collection of error messages and play error noise
    public static void warnPlayer(CommandSender sender, List<String> messages) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            playSound(ActionSound.ERROR, player);
        }

        for (String message : messages) {
            sender.sendMessage(TooManyTrees.prefix + ChatColor.RESET + ChatColor.RED + message);
        }
    }

    // send player a collection of messages
    public static void informPlayer(CommandSender player, List<String> messages) {
        for (String message : messages) {
            player.sendMessage(TooManyTrees.prefix + ChatColor.RESET + ChatColor.GRAY + message);
        }
    }

    // return the block the player is looking at, ignoring transparent blocks
    public static Block getBlockLookingAt(Player player) {
        return player.getTargetBlock(TRANSPARENT, 120);
    }

    // play sound at player (version independent)
    public static void playSound(ActionSound sound, Player player) {

        switch (sound) {
            case OPEN:
                Sound.CHEST_OPEN.playSound(player);
                break;
            case MODIFY:
                Sound.ANVIL_USE.playSound(player);
                break;
            case SELECT:
                Sound.LEVEL_UP.playSound(player);
                break;
            case CLICK:
                Sound.CLICK.playSound(player);
                break;
            case POP:
                Sound.CHICKEN_EGG_POP.playSound(player);
                break;
            case BREAK:
                Sound.ANVIL_LAND.playSound(player);
                break;
            case ERROR:
                Sound.BAT_DEATH.playSound(player);
                break;
        }

    }

    public static void treePlaceTick() {
        if (!TooManyTrees.enabled) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            Location l = player.getLocation();
            Block b = l.getBlock();
            // TODO fix possible null pointer
            Block b2 = l.add(0,-1,0).getBlock();

            // make sure that the block the player's feet is at is air
            if (!Version.getVersion().isBiggerThan(Version.v1_12) && (!(b.getType() == Material.AIR || b.getType() == Material.GRASS))) continue;
            if (Version.getVersion().isBiggerThan(Version.v1_12) && (!(b.getType() == Material.AIR || b.getType() == Material.CAVE_AIR || b.getType() == Material.GRASS))) continue;

            if (b2.getType() == Material.WATER) {
                if (!TooManyTrees.scaffold) continue;
            }
            else {
                if (DONTPLACE.contains(b2.getType()) && !TooManyTrees.scaffold) continue;
            }

            if (!(b2.getType() == Material.GRASS_BLOCK)) {
                if (!(TooManyTrees.scaffold && player.isSneaking())) b2.setType(Material.DIRT);
            }

            if (TooManyTrees.scaffold && player.isSneaking()) continue;

            if (TooManyTrees.randomSaplings) {
                int saplingType = rand.nextInt(4) + 1;
                switch (saplingType) {
                    case 1:
                        b.setType(Material.OAK_SAPLING);
                        break;
                    case 2:
                        b.setType(Material.ACACIA_SAPLING);
                        break;
                    case 3:
                        b.setType(Material.BIRCH_SAPLING);
                        break;
                    case 4:
                        b.setType(Material.JUNGLE_SAPLING);
                        break;
                    case 5:
                        b.setType(Material.SPRUCE_SAPLING);
                        break;
                }
            }
            else {
                b.setType(Material.OAK_SAPLING);
            }
            TooManyTrees.treeCounter++;

        }
        if (TooManyTrees.showCounter) Bukkit.broadcastMessage("Trees Planted: " + TooManyTrees.treeCounter);
    }

}
