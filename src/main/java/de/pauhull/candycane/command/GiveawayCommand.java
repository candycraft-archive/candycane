package de.pauhull.candycane.command;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GiveawayCommand implements CommandExecutor {

    private CandyCane candyCane;
    private Random random;

    public GiveawayCommand(CandyCane candyCane) {
        this.candyCane = candyCane;
        this.random = new Random();
        candyCane.getCommand("verlosung").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rang.verlosung")) {
            sender.sendMessage(Messages.PREFIX + Messages.NO_PERMISSIONS);
            return true;
        }

        AtomicInteger countdown = new AtomicInteger(30);
        AtomicInteger task = new AtomicInteger();
        task.set(Bukkit.getScheduler().scheduleSyncRepeatingTask(candyCane, () -> {

            int i = countdown.getAndDecrement();

            if (i == 0) {
                List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                Player player = players.get(random.nextInt(players.size()));
                Bukkit.broadcastMessage(Messages.PREFIX + "§a" + player.getName() + "§d hat §agewonnen§d!");
                Bukkit.getScheduler().cancelTask(task.get());
                return;
            }

            if (i % 5 == 0 || i <= 10) {
                if (i == 1) {
                    Bukkit.broadcastMessage(Messages.PREFIX + "§cDie Ziehung startet in §eeiner §cSekunde");
                } else {
                    Bukkit.broadcastMessage(Messages.PREFIX + "§cDie Ziehung startet in §e" + i + "§c Sekunden");
                }
            }

        }, 0, 20));

        return true;
    }
}
