package de.pauhull.candycane.display;

import de.pauhull.candycane.CandyCane;
import de.pauhull.scoreboard.CustomScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
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

    public void updateTeam(Player player) {
        String prefix, suffix, rank;
        if (player.getDisplayName().equals(player.getName())) {
            PermissionGroup group = getHighestPermissionGroup(player);
            rank = group.getRank() + "";
            prefix = group.getPrefix();
            suffix = group.getSuffix();
        } else {
            rank = "65";
            prefix = "§7";
            suffix = "§7";
        }

        String teamName = rank + player.getName();

        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }

        if (scoreboard.getTeam(teamName) != null) {
            scoreboard.getTeam(teamName).unregister();
        }

        Team team = scoreboard.registerNewTeam(teamName);
        team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
        team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
        team.setColor(getLastChatColor(ChatColor.translateAlternateColorCodes('&', prefix)));

        team.addEntry(player.getName());
    }

    private String getPlayerGroup(Player player) {
        PermissionGroup group = super.getHighestPermissionGroup(player);
        if (group != null) {
            return " "+ ChatColor.translateAlternateColorCodes('&', group.getSuffix() + group.getName());
        } else {
            return "§4Nicht gefunden";
        }
    }

    private ChatColor getLastChatColor(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i - 1) == '§') {
                return ChatColor.getByChar(s.charAt(i));
            }
        }
        return null;
    }

}
