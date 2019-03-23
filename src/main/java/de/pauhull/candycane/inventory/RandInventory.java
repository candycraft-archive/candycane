package de.pauhull.candycane.inventory;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.config.Configuration;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotBlock;
import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import de.pauhull.candycane.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Paul
 * on 18.02.2019
 *
 * @author pauhull
 */
public class RandInventory implements Listener {

    private static final String TITLE = "§cRand auswählen";


    public RandInventory(CandyCane candyCane) {
        Bukkit.getPluginManager().registerEvents(this, candyCane);
    }

    public void show(Player player) {
        if (Bukkit.getServerName().equalsIgnoreCase("Gingerbread")) {
            player.sendMessage(Messages.PREFIX + "§c§lDer /rand Befehl ist in der 1.13 noch nicht vorhanden!");
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 36, TITLE);
        inv.addItem(new ItemBuilder(Material.STONE).setDisplayName("§6Stein").build());
        inv.addItem(new ItemBuilder(Material.GLASS).setDisplayName("§6Glass").build());
        inv.addItem(new ItemBuilder(Material.SPONGE).setDisplayName("§6Schwamm").build());
        inv.addItem(new ItemBuilder(Material.BEDROCK).setDisplayName("§6Bedrock").build());
        inv.addItem(new ItemBuilder(Material.BARRIER).setDisplayName("§6Barrier").build());
        inv.addItem(new ItemBuilder(Material.WEB).setDisplayName("§6Spinnennetz").build());
        inv.addItem(new ItemBuilder(Material.FENCE).setDisplayName("§6Zaun").build());
        inv.addItem(new ItemBuilder(Material.IRON_FENCE).setDisplayName("§6Eisengitter").build());
        inv.addItem(new ItemBuilder(Material.GLOWSTONE).setDisplayName("§6Glowstone").build());
        inv.addItem(new ItemBuilder(Material.COBBLE_WALL).setDisplayName("§6Bruchsteinmauer").build());
        inv.addItem(new ItemBuilder(Material.DOUBLE_STONE_SLAB2).setDisplayName("§6Steinstufe").build());
        inv.addItem(new ItemBuilder(Material.ENDER_PORTAL_FRAME).setDisplayName("§6Endportalrahmen").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(44)).setDisplayName("§6Steinstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(44), 1, (short)1).setDisplayName("§6Sandsteinstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(44), 1, (short)3).setDisplayName("§6Bruchsteinstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(44), 1, (short)5).setDisplayName("§6Steinziegelstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(44), 1, (short)6).setDisplayName("§6Netherziegelstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(44), 1, (short)7).setDisplayName("§6Quarzstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(126)).setDisplayName("§6Eichenholzstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(126), 1, (short)4).setDisplayName("§6Akazienholzstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(44), 1, (short)4).setDisplayName("§6Ziegelsteinstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(182)).setDisplayName("§6Rote Sandsteinstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(126), 1, (short)5).setDisplayName("§6Schwarzeichenholzstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(126), 1, (short)2).setDisplayName("§6Birkenholzstufe").build());
        inv.addItem(new ItemBuilder(Material.getMaterial(126), 1, (short)3).setDisplayName("§6Tropenholzstufe").build());
        inv.setItem(35, new ItemBuilder(Material.INK_SACK, 1).setDisplayName("§6Rand entfernen").build());

        //player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        player.openInventory(inv);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack stack = event.getCurrentItem();

        if (inventory == null || inventory.getTitle() == null || !inventory.getTitle().equals(TITLE)) {
            return;
        } else {
            event.setCancelled(true);
        }

        if (stack == null)
            return;
        String clearName = event.getCurrentItem().getItemMeta().getDisplayName().substring(2);
        ItemStack item = event.getCurrentItem();
        if (isOwner(player)) {
            if (clearName.equals("Rand entfernen")) {
                setRand(player, "0:0", true);
                player.closeInventory();
                return;
            }
            if (clearName.equals("Redstonelampe")) {
                setRand(player, "124:0", true);
                player.closeInventory();
                return;
            }
            setRand(player, item.getType().getId() + ":" + item.getDurability(), true);
            player.closeInventory();
        } else {
            player.sendMessage("§d§lCandyCraft §8| §cKeine Rechte!");
        }

        //player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
    }

    private boolean isOwner(Player player) {
        PlotAPI api = new PlotAPI();
        Plot plot = api.getPlot(player.getLocation());
        if (plot == null) {
            return false;
        }
        if (!plot.hasOwner()) {
            return false;
        }
        if (player.isOp()) {
            return true;
        }
        return plot.isOwner(player.getUniqueId());
    }
    private void setRand(Player p, String id, boolean msg) {
        PlotAPI api = new PlotAPI();
        Plot plot = api.getPlot(p.getLocation());
        PlotBlock[] pb = Configuration.BLOCKLIST.parseString(id);
        if (plot.getConnectedPlots().size() > 1) {
            for (Plot plots : plot.getConnectedPlots()) {
                api.getPlotManager(p.getWorld()).setComponent(plots.getArea(), plots.getId(), "border", pb);
            }
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.0f);
            if (msg) {
                p.sendMessage("§d§lCandyCraft §8| §aDu hast den §eRand §adeines Grundstücks geändert.");
            }
        } else {
            api.getPlotManager(p.getWorld()).setComponent(plot.getArea(), plot.getId(), "border", pb);
            //p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.0f);
            if (msg) {
                p.sendMessage("§d§lCandyCraft §8| §aDu hast den §eRand §adeines Grundstücks geändert.");
            }
        }
    }

}
