package thirtyvirus.tmt.events.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import thirtyvirus.tmt.TooManyTrees;
import thirtyvirus.tmt.helpers.ActionSound;
import thirtyvirus.tmt.helpers.Utilities;

public class BlockClick implements Listener {

    private TooManyTrees main = null;
    public BlockClick(TooManyTrees main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        //Utilities.playSound(ActionSound.ERROR, event.getPlayer());

    }

}