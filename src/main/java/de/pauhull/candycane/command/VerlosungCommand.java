package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerlosungCommand implements CommandExecutor {

    private CandyCane cane;

    public VerlosungCommand(CandyCane candyCane) {
        this.cane = candyCane;
        candyCane.getCommand("verlosung").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("rang.verlosung")) {
                if (args.length == 0) {
                    final int[] k = {-1, -1};
                    BukkitRunnable runnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            k[0]++;
                            int l = 30 - k[0];
                            if (k[0] == 0) {
                                Bukkit.broadcastMessage(Messages.PREFIX + "§cDie Ziehung startet in §e" + l + "§c Sekunden");
                            }
                            if (k[0] == 5) {
                                Bukkit.broadcastMessage(Messages.PREFIX + "§cDie Ziehung startet in §e" + l + "§c Sekunden");
                            }
                            if (k[0] == 10) {
                                Bukkit.broadcastMessage(Messages.PREFIX + "§cDie Ziehung startet in §e" + l + "§c Sekunden");
                            }
                            if (k[0] == 15) {
                                Bukkit.broadcastMessage(Messages.PREFIX + "§cDie Ziehung startet in §e" + l + "§c Sekunden");
                            }
                            if (k[0] == 29) {
                                Bukkit.broadcastMessage(Messages.PREFIX + "§cDie Ziehung startet in §eeiner§c Sekunde");
                            }
                            if (k[0] > 19 && k[0] != 30 && k[0] != 31 && k[0] != 29) {
                                Bukkit.broadcastMessage(Messages.PREFIX + "§cDie Ziehung startet in §e" + l + "§c Sekunden");
                            }
                            if (k[0] == 30) {
                                List<Player> players = new ArrayList<>();
                                players.addAll(Bukkit.getOnlinePlayers());
                                Player rndm = players.get(new Random().nextInt(players.size()));
                                Bukkit.broadcastMessage(Messages.PREFIX + "§4"+ rndm.getName() + " §chat§a gewonnen");
                                //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + rndm.getName() + " group set " + args[0]);
                            }
                            if (k[0] > 30) {
                                Bukkit.getScheduler().cancelTask(k[1]);
                            }
                        }
                    };
                    runnable.runTaskTimer(cane, 1, 20);
                    k[1] = runnable.getTaskId();
                } else {
                  player.sendMessage(Messages.PREFIX + "§c/verlosung");
                }
            }else {
                player.sendMessage(Messages.PREFIX + Messages.NO_PERMISSIONS);
            }
        }
        return true;
    }
}
