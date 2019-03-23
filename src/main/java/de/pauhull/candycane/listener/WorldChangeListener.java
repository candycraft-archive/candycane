package de.pauhull.candycane.listener;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {

    public WorldChangeListener(CandyCane candyCane) {
        Bukkit.getPluginManager().registerEvents(this, candyCane);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("fly.bypass")) {
            return;
        }

        if (player.getWorld().getName().equalsIgnoreCase("farmwelt") || player.getWorld().getEnvironment() != World.Environment.NORMAL) {
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(Messages.PREFIX + "§cDein Flugmodus wurde deaktiviert, da du in einer Farmwelt bist.");
            }
        }
    }

}
