package de.pauhull.candycane.listener;

import de.pauhull.candycane.CandyCane;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinAndLeaveListener implements Listener {

    public JoinAndLeaveListener(CandyCane candyCane) {
        candyCane.getServer().getPluginManager().registerEvents(this, candyCane);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

}
