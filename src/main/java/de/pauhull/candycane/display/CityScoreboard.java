package de.pauhull.candycane.display;

import de.pauhull.candycane.CandyCane;
import de.pauhull.scoreboard.NovusScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionGroup;

public class CityScoreboard extends NovusScoreboard {

    private NovusScore rank, coins;

    public CityScoreboard(Player player) {
        super(player, player.getName(), "  §d§lCandyCraft  ");
        this.descending = false;
    }

    @Override
    public void show() {


        new NovusScore( " §fshop.candycraft.de");
        new NovusScore("§cShop:");
        new NovusScore();
        this.coins = new NovusScore("§d Lädt...");
        new NovusScore("§cGeld:");
        new NovusScore();
        this.rank = new NovusScore("§b Lädt...");
        new NovusScore("§cRang: ");
        new NovusScore();
        new NovusScore(" §fCandy§cCane");
        new NovusScore("§cServer: ");
        new NovusScore();

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
