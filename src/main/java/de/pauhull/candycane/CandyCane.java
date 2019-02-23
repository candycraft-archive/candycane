package de.pauhull.candycane;

import de.pauhull.candycane.command.*;
import de.pauhull.candycane.inventory.PerksInventory;
import de.pauhull.candycane.listener.InventoryClickListener;
import de.pauhull.candycane.listener.InventoryCloseListener;
import de.pauhull.candycane.listener.PerkListener;
import de.pauhull.candycane.manager.PerkManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Paul
 * on 18.02.2019
 *
 * @author pauhull
 */
public class CandyCane extends JavaPlugin {

    @Getter
    private static CandyCane instance;

    @Getter
    private PerkManager perkManager;

    @Getter
    private PerksInventory perksInventory;

    @Override
    public void onEnable() {
        instance = this;

        this.perkManager = new PerkManager(this);
        this.perksInventory = new PerksInventory(this);

        new AlchemistCommand(this);
        new InventoryCloseListener(this);
        new InventoryClickListener(this);
        new PerkListener(this);
        new BodySeeCommand(this);
        new GiveAllCommand(this);
        new VoteCommand(this);
        new PerksCommand(this);
        new TrashCommand(this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

}
