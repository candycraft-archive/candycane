package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AfkCommand implements CommandExecutor {

    private CandyCane candyCane;

    public AfkCommand(CandyCane c) {
        this.candyCane = c;
        candyCane.getCommand("tafk").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("teamchat.use")) {
                if (CandyCane.afk.contains(player.getUniqueId().toString())) {
                    CandyCane.afk.remove(player.getUniqueId().toString());
                    player.sendMessage(Messages.PREFIX + "§cDu bist nun nicht mehr Afk");
                }else {
                    CandyCane.afk.add(player.getUniqueId().toString());
                    player.sendMessage(Messages.PREFIX + "§cDu bist nun Afk");
                }
                candyCane.getScoreboardManager().updateTeam(player);
            }
        }
        return true;
    }
}
