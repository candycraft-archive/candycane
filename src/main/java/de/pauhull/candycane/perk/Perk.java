package de.pauhull.candycane.perk;

import de.pauhull.candycane.CandyCane;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Paul
 * on 18.02.2019
 *
 * @author pauhull
 */
public enum Perk {

    NO_FIRE_DAMAGE("nofiredamage", "Kein Feuerschaden", Material.LAVA_BUCKET, (short) 0, "", "§7Du bekommst keinen Feuerschaden"),
    NO_WATER_DAMAGE("nowaterdamage", "Kein Wasserschaden", Material.WATER_BUCKET, (short) 0, "", "§7Du kannst nicht etrinken"),
    DOUBLE_JUMP("doublejump", "Doppelsprung", Material.FEATHER, (short) 0, "", "§7Du kannst in der Luft", "§7einen weiteren Sprung ausführen"),
    POTION_CLEAR("potionclear", "Anti-Potion", Material.POTION, (short) 0, "", "§7Du kannst einem Spieler mit Shift-Rechtsklick", "§7alle 15 Minuten die Potioneffekte entfernen"),
    KEEP_XP("keepxp", "XP behalten", Material.EXP_BOTTLE, (short) 0, "", "§7Du behältst deine XP-Punkte nach dem Tod"),
    RUNNER("runner", "Läufer", Material.DIAMOND_BOOTS, (short) 0, "", "§7Du hast dauerhaft Speed II"),
    DOUBLE_XP("doublexp", "Doppelt XP", Material.EXP_BOTTLE, (short) 0, "", "§7Du bekommst immer doppelt so viel XP"),
    NO_HUNGER("nohunger", "Kein Hunger", Material.COOKED_BEEF, (short) 0, "", "§7Du hast keinen Hunger"),
    ITEM_NAME("itemname", "Item Name", Material.NAME_TAG, (short) 0, "", "§7Alle Items, die du aufsammelst,", "§7werden nach dir benannt"),
    DROPPER("dropper", "Dropper", Material.DROPPER, (short) 0, "", "§7Du bekommst die Items deines Gegners, ", "§7den du getötet hast, in dein Inventar"),
    ARROW_POTION("arrowpotion", "Giftpfeil", Material.ARROW, (short) 0, "", "§7Wenn du jemanden mit einem Pfeil triffst, ", "§7bekommt dieser für 10 Sekunden Langsamkeit"),
    NIGHT_VISION("nightvision", "Nachtsicht", Material.EYE_OF_ENDER, (short) 0, "", "§7Du hast dauerthaft Nachtsicht"),
    ANTI_POISON("antipoison", "Anti-Gift", Material.SPIDER_EYE, (short) 0, "", "§7Du bekommst nie wieder Gift-Schaden"),
    ANTI_FALL_DAMAGE("antifalldamage", "Federfall", Material.DIAMOND_LEGGINGS, (short) 0, "", "§7Du bekommst nie wieder Fallschaden"),
    FAST_BREAK("fastbreak", "Schnelles Abbauen", Material.DIAMOND_PICKAXE, (short) 0, "", "§7Du kannst schneller Blöcke abbauen"),
    MOB_SPAWNER("mobspawner", "Mob Spawner", Material.MOB_SPAWNER, (short) 0, "", "§7Wenn du einen Mob-Spawner abbaust,", "§7bekommst du ihn ins Inventar");

    @Getter
    private String name;

    @Getter
    private String displayName;

    @Getter
    private Material material;

    @Getter
    private short data;

    @Getter
    private List<String> lore = new ArrayList<>();

    Perk(String name, String displayName, Material material, short data, String... lore) {
        this.name = name;
        this.displayName = displayName;
        this.material = material;
        this.data = data;
        this.lore.addAll(Arrays.asList(lore));
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission("perks." + name);
    }

    public boolean isEnabled(Player player) {
        return this.hasPermission(player) && CandyCane.getInstance().getPerkManager().isEnabled(player.getUniqueId(), this);
    }

}

