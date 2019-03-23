package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Paul
 * on 23.03.2019
 *
 * @author pauhull
 */
public class FlyCommand implements CommandExecutor {

    private CandyCane candyCane;

    public FlyCommand(CandyCane candyCane) {
        this.candyCane = candyCane;
        candyCane.getCommand("fly").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("essentials.fly")) {
            sender.sendMessage(Messages.PREFIX + "§cDafür hast du keine Rechte!");
            return true;
        }

        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (player.getAllowFlight()) {
            player.sendMessage(Messages.PREFIX + "Du kannst nun nicht mehr fliegen");
            player.setFlying(false);
            player.setAllowFlight(false);
        } else {
            if (!player.hasPermission("fly.bypass")) {
                if (player.getWorld().getName().equalsIgnoreCase("farmwelt") || player.getWorld().getEnvironment() != World.Environment.NORMAL) {
                    player.sendMessage(Messages.PREFIX + "§cDu darfst hier nicht fliegen!");
                    return true;
                }
            }

            player.sendMessage(Messages.PREFIX + "Du kannst nun fliegen");
            player.setAllowFlight(true);
        }

        return false;
    }
}
