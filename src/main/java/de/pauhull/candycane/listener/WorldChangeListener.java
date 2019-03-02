package de.pauhull.candycane.listener;

import de.pauhull.candycane.CandyCane;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {

    public WorldChangeListener(CandyCane candyCane) {
        candyCane.getServer().getPluginManager().registerEvents(this, candyCane);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        if (event.getPlayer().getLocation().getWorld().getName().equals("farmwelt")) {
            if (event.getPlayer().getAllowFlight()) {
                event.getPlayer().setAllowFlight(false);
            }
        }
    }

}
