package de.pauhull.candycane.inventory;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by Paul
 * on 18.02.2019
 *
 * @author pauhull
 */
public class NavigatorInventory implements Listener {

    private static final String TITLE = "§cNavigator";

    private CandyCane candyCane;

    public NavigatorInventory(CandyCane candyCane) {
        this.candyCane = candyCane;

        Bukkit.getPluginManager().registerEvents(this, candyCane);
    }

    public void show(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, TITLE);
        Arrays.asList(0, 2, 4, 6, 8, 18, 20, 22, 24, 26).forEach(slot -> {
            inventory.setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 3).setDisplayName(" ").build());
        });
        Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25).forEach(slot -> {
            inventory.setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setDisplayName(" ").build());
        });
        inventory.setItem(10, new ItemBuilder(Material.BED).setDisplayName("§6§lSPAWN").setLore("§7Klicke um dich zu teleportieren!").build());
        inventory.setItem(12, new ItemBuilder(Material.ENDER_PORTAL_FRAME).setDisplayName("§3§lFARMWELT").setLore("§7Klicke um dich zu teleportieren!").build());
        inventory.setItem(14, new ItemBuilder(Material.NETHERRACK).setDisplayName("§4§lNETHER").setLore("§7Klicke um dich zu teleportieren!").build());
        inventory.setItem(16, new ItemBuilder(Material.BOW).setDisplayName("§c§lARENA").setLore("§7Klicke um dich zu teleportieren!").build());

        //player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.openInventory(inventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack stack = event.getCurrentItem();

        if (inventory == null || inventory.getTitle() == null || !inventory.getTitle().equals(TITLE)) {
            return;
        } else {
            event.setCancelled(true);
        }

        if (stack == null)
            return;

        if(event.getSlot() == 10) {
            ((Player)event.getWhoClicked()).performCommand("spawn");
        } else if(event.getSlot() == 12) {
            ((Player)event.getWhoClicked()).performCommand("warp farmwelt");
        } else if(event.getSlot() == 14) {
            ((Player)event.getWhoClicked()).performCommand("warp nether");
        } else if(event.getSlot() == 16) {
            ((Player)event.getWhoClicked()).performCommand("warp arena");
        }

        //player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
    }

}
