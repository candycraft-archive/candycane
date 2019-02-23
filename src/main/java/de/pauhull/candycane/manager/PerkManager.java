package de.pauhull.candycane.manager;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.perk.Perk;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Paul
 * on 19.02.2019
 *
 * @author pauhull
 */
public class PerkManager {

    private Map<Perk, List<UUID>> enabledPerks;
    private File file;
    private FileConfiguration config;

    public PerkManager(CandyCane candyCane) {
        this.file = new File(candyCane.getDataFolder(), "perks.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        this.enabledPerks = new HashMap<>();

        for (Perk perk : Perk.values()) {
            List<UUID> uuids = new ArrayList<>();
            if (config.isList(perk.name())) {
                for (String string : config.getStringList(perk.name())) {
                    uuids.add(UUID.fromString(string));
                }
            }
            enabledPerks.put(perk, uuids);
        }
    }

    public void save() {
        for (Perk perk : enabledPerks.keySet()) {
            List<String> strings = new ArrayList<>();
            for (UUID uuid : enabledPerks.get(perk)) {
                strings.add(uuid.toString());
            }
            config.set(perk.name(), strings);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEnabled(UUID uuid, Perk perk, boolean enabled) {
        if (enabled) {
            if (!enabledPerks.get(perk).contains(uuid)) {
                enabledPerks.get(perk).add(uuid);

                Player player = Bukkit.getPlayer(uuid);

                if (player != null) {
                    switch (perk) {

                        case FAST_BREAK:
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2));
                            break;

                        case NIGHT_VISION:
                            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
                            break;

                        case RUNNER:
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
                            break;
                    }
                }
            }
        } else {
            enabledPerks.get(perk).remove(uuid);

            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                switch (perk) {
                    case DOUBLE_JUMP:
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        break;

                    case FAST_BREAK:
                        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                        break;

                    case NIGHT_VISION:
                        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        break;

                    case RUNNER:
                        player.removePotionEffect(PotionEffectType.SPEED);
                        break;
                }
            }
        }
        save();
    }

    public boolean isEnabled(UUID uuid, Perk perk) {
        return enabledPerks.get(perk).contains(uuid);
    }

}