package thirtyvirus.tmt.events.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import thirtyvirus.tmt.TooManyTrees;
import thirtyvirus.tmt.helpers.Utilities;

import static thirtyvirus.tmt.helpers.ActionSound.CLICK;

public class InventoryClick implements Listener {

    private TooManyTrees main = null;
    public InventoryClick(TooManyTrees main) {
        this.main = main;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        //Utilities.playSound(CLICK, (Player)event.getWhoClicked());
    }

}