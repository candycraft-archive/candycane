package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by Paul
 * on 21.02.2019
 *
 * @author pauhull
 */
public class GiveAllCommand implements CommandExecutor {

    private CandyCane candyCane;

    public GiveAllCommand(CandyCane candyCane) {
        this.candyCane = candyCane;
        candyCane.getCommand("giveall").setExecutor(this);
        candyCane.getCommand("ga").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("candycane.giveall")) {
            player.sendMessage(Messages.PREFIX + Messages.NO_PERMISSIONS);
            return true;
        }

        ItemStack stack = player.getItemInHand();

        if (stack == null) {
            player.sendMessage(Messages.PREFIX + "§cDu hast kein Item in der Hand!");
            return true;
        }

        player.sendMessage(Messages.PREFIX + "§aItems verteilt");

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.equals(player)) {
                continue;
            }

            Map<Integer, ItemStack> overflow = all.getInventory().addItem(stack);
            for (ItemStack drop : overflow.values()) {
                all.getWorld().dropItem(all.getLocation(), drop);
            }
            all.sendMessage(Messages.PREFIX + "§e" + stack.getType() + "§a von §e" + player.getName() + "§a erhalten!");
        }

        return true;
    }
}
