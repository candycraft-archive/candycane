package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AlchemistCommand implements CommandExecutor {

    public static final ItemStack GLASS_PANE = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName("§8*§fKlick§8*").setLore("§7(Die Sachen werden umgewandelt)").build();
    private static final ItemStack GOLD_NUGGET = new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§8*§fInformation§8*").setLore("§7Man bekommt das umgewandelte Buch automatisch,", "§7wenn man auf eine Glassscheibe klickt.").build();
    private static final ItemStack INFO = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName("§b§lTHE SERVER ALCHEMIST").setLore("§d§lDieser NPC tauscht...", " ", "§b§l× §f2X Enchantment Bücher", "§7(vom gleichen Typ und Level)", "§d§l= §f1X Enchantment Bücher", "§7(vom höherem)", " ", "§b§l× §f2X Magic Dust", "§7(von der gleichen Seltenheit)", "§d§l= §f1X Magic Dust", "§7(von höherer Seltenheit)").build();

    private CandyCane candyCane;

    public AlchemistCommand(CandyCane candyCane) {
        this.candyCane = candyCane;
        candyCane.getCommand("alchemist").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, "Alchemist");
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i != 3 && i != 5) {
                inventory.setItem(i, GLASS_PANE);
            }
        }

        inventory.setItem(13, GOLD_NUGGET);
        inventory.setItem(22, INFO);
        player.openInventory(inventory);
        return true;
    }
}

