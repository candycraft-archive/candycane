package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandCommand implements CommandExecutor {

    private CandyCane candyCane;

    public RandCommand(CandyCane candyCane) {
        this.candyCane = candyCane;
        candyCane.getCommand("rand").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            candyCane.getRandInventory().show((Player) commandSender);
        }
        return true;
    }
}
