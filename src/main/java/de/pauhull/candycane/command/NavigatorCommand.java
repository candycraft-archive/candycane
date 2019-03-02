package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NavigatorCommand implements CommandExecutor {

    private CandyCane candyCane;

    public NavigatorCommand(CandyCane candyCane) {
        this.candyCane = candyCane;
        candyCane.getCommand("navigator").setExecutor(this);
        candyCane.getCommand("nav").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            candyCane.getNavigatorInventory().show((Player) commandSender);
        }
        return true;
    }
}
