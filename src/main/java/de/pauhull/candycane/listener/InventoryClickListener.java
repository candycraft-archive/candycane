package de.pauhull.candycane.listener;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.command.AlchemistCommand;
import de.pauhull.candycane.util.TradeManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Paul
 * on 22.02.2019
 *
 * @author pauhull
 */
public class InventoryClickListener implements Listener {

    private CandyCane candyCane;

    public InventoryClickListener(CandyCane candyCane) {
        this.candyCane = candyCane;
        Bukkit.getPluginManager().registerEvents(this, candyCane);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack stack = event.getCurrentItem();

        if (inventory == null || inventory.getTitle() == null || !inventory.getTitle().equals("Alchemist")) {
            return;
        } else {
            event.setCancelled(true);
        }

        if (stack != null) {
            if (stack.equals(AlchemistCommand.GLASS_PANE)) {
                ItemStack itemStack = TradeManager.getTradeItem(inventory.getItem(3), inventory.getItem(5));

                event.getClickedInventory().setItem(13, itemStack);
                if (event.getClickedInventory().getItem(13).getType() == Material.ENCHANTED_BOOK) {
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 2);
                    player.getInventory().addItem(itemStack);
                }
            }
        }
    }

}
