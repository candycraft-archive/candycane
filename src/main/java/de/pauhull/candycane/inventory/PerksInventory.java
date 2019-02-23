package de.pauhull.candycane.inventory;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import de.pauhull.candycane.perk.Perk;
import de.pauhull.candycane.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul
 * on 18.02.2019
 *
 * @author pauhull
 */
public class PerksInventory implements Listener {

    private static final ItemStack ENABLED = new ItemBuilder(Material.INK_SACK, 1, 10).setDisplayName("§aAktiviert").build();
    private static final ItemStack DISABLED = new ItemBuilder(Material.INK_SACK, 1, 8).setDisplayName("§7Deaktiviert").build();
    private static final String TITLE = "Perks";

    private CandyCane candyCane;

    public PerksInventory(CandyCane candyCane) {
        this.candyCane = candyCane;

        Bukkit.getPluginManager().registerEvents(this, candyCane);
    }

    public void show(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, TITLE);

        placePerk(inventory, 0, Perk.NO_FIRE_DAMAGE, player);
        placePerk(inventory, 9, Perk.NO_WATER_DAMAGE, player);
        placePerk(inventory, 18, Perk.DOUBLE_JUMP, player);
        placePerk(inventory, 27, Perk.POTION_CLEAR, player);
        placePerk(inventory, 36, Perk.KEEP_XP, player);
        placePerk(inventory, 45, Perk.RUNNER, player);

        placePerk(inventory, 3, Perk.DOUBLE_XP, player);
        placePerk(inventory, 12, Perk.NO_HUNGER, player);
        placePerk(inventory, 21, Perk.ITEM_NAME, player);
        placePerk(inventory, 30, Perk.DROPPER, player);
        placePerk(inventory, 39, Perk.ARROW_POTION, player);
        placePerk(inventory, 48, Perk.NIGHT_VISION, player);

        placePerk(inventory, 6, Perk.ANTI_POISON, player);
        placePerk(inventory, 15, Perk.ANTI_FALL_DAMAGE, player);
        placePerk(inventory, 24, Perk.FAST_BREAK, player);
        placePerk(inventory, 33, Perk.MOB_SPAWNER, player);

        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.openInventory(inventory);
    }

    private void placePerk(Inventory inventory, int slot, Perk perk, Player player) {
        List<String> lore = new ArrayList<>(perk.getLore());
        lore.add(" ");
        lore.add(perk.isEnabled(player) ? "§aAktiviert" : "§cDeaktiviert");
        ItemStack stack = new ItemBuilder(perk.getMaterial()).setDisplayName("§a" + perk.getDisplayName()).setLore(lore).build();
        inventory.setItem(slot, stack);
        inventory.setItem(slot + 1, perk.isEnabled(player) ? ENABLED : DISABLED);
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

        if (stack == null || stack.getType() != Material.INK_SACK)
            return;

        ItemStack perkStack = inventory.getItem(event.getSlot() - 1);
        Perk perk = null;
        for (Perk allPerks : Perk.values()) {
            if (allPerks.getMaterial() == perkStack.getType() && allPerks.getData() == perkStack.getDurability()
                    && ("§a" + allPerks.getDisplayName()).equals(perkStack.getItemMeta().getDisplayName())) {

                perk = allPerks;
            }
        }

        if (perk == null)
            return;

        if (!perk.hasPermission(player)) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
            player.sendMessage(Messages.PREFIX + "§aPerks kaufen: §e§lshop.candycraft.de");
            return;
        }

        if (stack.equals(ENABLED)) {
            candyCane.getPerkManager().setEnabled(player.getUniqueId(), perk, false);
        } else {
            candyCane.getPerkManager().setEnabled(player.getUniqueId(), perk, true);
        }
        placePerk(inventory, event.getSlot() - 1, perk, player);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
    }

}
