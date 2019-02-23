package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Paul
 * on 18.02.2019
 *
 * @author pauhull
 */
public class TrashCommand implements CommandExecutor {

    private CandyCane candyCane;

    public TrashCommand(CandyCane candyCane) {
        this.candyCane = candyCane;

        candyCane.getCommand("trash").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(null, 45, "§0§lMülleimer");
        player.openInventory(inventory);

        return true;
    }

}
