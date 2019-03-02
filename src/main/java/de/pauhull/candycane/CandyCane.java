package de.pauhull.candycane;

import de.pauhull.candycane.command.*;
import de.pauhull.candycane.display.CityScoreboard;
import de.pauhull.candycane.inventory.NavigatorInventory;
import de.pauhull.candycane.inventory.PerksInventory;
import de.pauhull.candycane.inventory.RandInventory;
import de.pauhull.candycane.listener.InventoryCloseListener;
import de.pauhull.candycane.listener.JoinAndLeaveListener;
import de.pauhull.candycane.listener.PerkListener;
import de.pauhull.candycane.listener.WorldChangeListener;
import de.pauhull.candycane.manager.PerkManager;
import de.pauhull.scoreboard.NovusScoreboardManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
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

    @Getter
    private NavigatorInventory navigatorInventory;

    @Getter
    private RandInventory randInventory;

    @Getter
    private NovusScoreboardManager scoreboardManager;

    @Getter
    private Economy economy;

    @Override
    public void onEnable() {
        instance = this;

        this.perkManager = new PerkManager(this);
        this.perksInventory = new PerksInventory(this);
        this.navigatorInventory = new NavigatorInventory(this);
        this.randInventory = new RandInventory(this);
        this.scoreboardManager = new NovusScoreboardManager(this, CityScoreboard.class);

        RegisteredServiceProvider<Economy> registeredServiceProvider =
                this.getServer().getServicesManager().getRegistration(Economy.class);
        this.economy = registeredServiceProvider.getProvider();

        new RandCommand(this);
        new InventoryCloseListener(this);
        new WorldChangeListener(this);
        new JoinAndLeaveListener(this);
        new PerkListener(this);
        new BodySeeCommand(this);
        new GiveAllCommand(this);
        new VoteCommand(this);
        new PerksCommand(this);
        new TrashCommand(this);
        new NavigatorCommand(this);

    }

    @Override
    public void onDisable() {
        instance = null;
    }

}
