package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Paul
 * on 21.02.2019
 *
 * @author pauhull
 */
public class BodySeeCommand implements CommandExecutor {

    private CandyCane candyCane;

    public BodySeeCommand(CandyCane candyCane) {
        this.candyCane = candyCane;
        candyCane.getCommand("bodysee").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("twice.bodysee")) {
            player.sendMessage(Messages.PREFIX + Messages.NO_PERMISSIONS);
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(Messages.PREFIX + "§c/bodysee <Spieler>");
            return true;
        }

        Player look = Bukkit.getPlayer(args[0]);

        if (look == null) {
            player.sendMessage(Messages.PREFIX + "§cDieser Spieler ist nicht online.");
            return true;
        }

        if (look.getName().equals(player.getName())) {
            player.sendMessage(Messages.PREFIX + "§cDu kannst dein eigenes Inventar nicht anschauen!");
            return true;
        }

        Inventory inventory = Bukkit.createInventory(null, 9, "Inventar: " + look.getName());
        ItemStack[] armor = look.getInventory().getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            inventory.setItem(i * 2 + 1, armor[i]);
        }
        player.openInventory(inventory);

        return false;
    }
}
