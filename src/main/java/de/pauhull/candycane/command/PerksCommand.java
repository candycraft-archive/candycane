package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Paul
 * on 19.02.2019
 *
 * @author pauhull
 */
public class PerksCommand implements CommandExecutor {

    private CandyCane candyCane;

    public PerksCommand(CandyCane candyCane) {
        this.candyCane = candyCane;
        candyCane.getCommand("perks").setExecutor(this);
        candyCane.getCommand("perk").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        candyCane.getPerksInventory().show(player);

        return true;
    }

}
