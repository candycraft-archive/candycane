package de.pauhull.candycane.listener;

import de.pauhull.candycane.CandyCane;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Paul
 * on 22.02.2019
 *
 * @author pauhull
 */
public class InventoryCloseListener implements Listener {

    private CandyCane candyCane;

    public InventoryCloseListener(CandyCane candyCane) {
        this.candyCane = candyCane;
        Bukkit.getPluginManager().registerEvents(this, candyCane);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        if (inventory == null || inventory.getTitle() == null || !inventory.getTitle().equals("Alchemist")) {
            return;
        }

        Arrays.asList(3, 5).forEach(i -> {
            if (inventory.getItem(i) != null) {
                Map<Integer, ItemStack> overflow = player.getInventory().addItem(inventory.getItem(i));
                for (ItemStack drop : overflow.values()) {
                    player.getWorld().dropItem(player.getLocation(), drop);
                }
            }
        });
    }

}
