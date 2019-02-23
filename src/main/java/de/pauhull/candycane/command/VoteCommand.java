package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Paul
 * on 21.02.2019
 *
 * @author pauhull
 */
public class VoteCommand implements CommandExecutor {

    private CandyCane candyCane;

    public VoteCommand(CandyCane candyCane) {
        this.candyCane = candyCane;
        candyCane.getCommand("vote").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(Messages.PREFIX + "§c/vote ist momentan nicht verfügbar.");
        return true;
    }
}
