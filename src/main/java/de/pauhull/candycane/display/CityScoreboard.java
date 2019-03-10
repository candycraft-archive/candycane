package de.pauhull.candycane.display;

import de.pauhull.candycane.CandyCane;
import de.pauhull.scoreboard.CustomScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionGroup;

public class CityScoreboard extends CustomScoreboard {

    private CustomScoreboard.DisplayScore rank, coins;

    public CityScoreboard(Player player) {
        super(player, player.getName(), "  §d§lCandyCraft  ");
        this.descending = false;
    }

    @Override
    public void show() {


        new DisplayScore( " §fshop.candycraft.de");
        new DisplayScore("§cShop:");
        new DisplayScore();
        this.coins = new DisplayScore("§d Lädt...");
        new DisplayScore("§cGeld:");
        new DisplayScore();
        this.rank = new DisplayScore("§b Lädt...");
        new DisplayScore("§cRang: ");
        new DisplayScore();
        if (Bukkit.getServerName().equals("Gingerbread")) {
            new DisplayScore(" §fGinger§cBread");
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', getHighestPermissionGroup(player).getPrefix() + player.getName()));
        }else {
            new DisplayScore(" §fCandy§cCane");
        }
        new DisplayScore("§cServer: ");
        new DisplayScore();
        super.show();

    }

    @Override
    public void update() {
        String rankScore = getPlayerGroup(player);
        if (!rank.getScore().getEntry().equals(rankScore)) {
            rank.setName(rankScore);
        }

        double is = CandyCane.getInstance().getEconomy().getBalance(player);
        double roundeds = Math.round(is * 100.0D) / 100.0D;
        String coinsScore = "§f " + roundeds + "$";
        if (!this.coins.getScore().getEntry().equals(coinsScore)) {
            this.coins.setName(coinsScore);
        }
    }

    private String getPlayerGroup(Player player) {
        PermissionGroup group = super.getHighestPermissionGroup(player);
        if (group != null) {
            return " "+ ChatColor.translateAlternateColorCodes('&', group.getSuffix() + group.getName());
        } else {
            return "§4Nicht gefunden";
        }
    }
}
