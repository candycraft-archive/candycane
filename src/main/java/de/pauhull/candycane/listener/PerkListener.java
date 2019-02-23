package de.pauhull.candycane.listener;

import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import de.pauhull.candycane.perk.Perk;
import de.pauhull.candycane.util.ItemBuilder;
import de.pauhull.candycane.util.TimedHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Paul
 * on 19.02.2019
 *
 * @author pauhull
 */
public class PerkListener implements Listener {

    private TimedHashMap<UUID, Object> lastPotionClear;

    private CandyCane candyCane;

    public PerkListener(CandyCane candyCane) {
        this.candyCane = candyCane;
        this.lastPotionClear = new TimedHashMap<>(TimeUnit.SECONDS, 90);
        Bukkit.getPluginManager().registerEvents(this, candyCane);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player &&
                    Perk.ARROW_POTION.isEnabled((Player) ((Arrow) event.getDamager()).getShooter())) {

                ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer != null) {
            if (Perk.DROPPER.isEnabled(killer)) {
                ItemStack[] items = event.getDrops().toArray(new ItemStack[0]);
                Map<Integer, ItemStack> overflow = killer.getInventory().addItem(items);
                overflow.values().forEach(stack -> player.getWorld().dropItem(player.getLocation(), stack));
                event.getDrops().clear();
            }
        }

        if (Perk.KEEP_XP.isEnabled(player)) {
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();

        if ((event.getCause() == DamageCause.FALL && Perk.ANTI_FALL_DAMAGE.isEnabled(player))
                || (Perk.NO_FIRE_DAMAGE.isEnabled(player) && (event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.FIRE_TICK || event.getCause() == DamageCause.LAVA))
                || (event.getCause() == DamageCause.DROWNING && Perk.NO_WATER_DAMAGE.isEnabled(player))
                || (event.getCause() == DamageCause.POISON && Perk.ANTI_POISON.isEnabled(player))) {

            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (Perk.NO_HUNGER.isEnabled((Player) event.getEntity())) {
            event.setFoodLevel(20);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        if (Perk.DOUBLE_XP.isEnabled(event.getPlayer()) && event.getAmount() > 0) {
            event.setAmount(event.getAmount() * 2);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (Perk.DOUBLE_JUMP.isEnabled(player)) {
            if (player.isFlying()) {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setVelocity(player.getLocation().getDirection().normalize().setY(0.9f).multiply(0.6f));
            }
            if (player.isOnGround()) {
                player.setAllowFlight(true);
            }
        }

        if (Perk.RUNNER.isEnabled(player) && !player.hasPotionEffect(PotionEffectType.SPEED)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        }

        if (Perk.FAST_BREAK.isEnabled(player) && !player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2));
        }

        if (Perk.NIGHT_VISION.isEnabled(player) && !event.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }

        Player clicked = (Player) event.getRightClicked();
        Player player = event.getPlayer();

        if (Perk.POTION_CLEAR.isEnabled(player) && player.isSneaking()) {
            if (clicked.getActivePotionEffects().isEmpty()) {
                player.sendMessage(Messages.PREFIX + "§cDieser Spieler hat gerade keine Effekte.");
            } else if (!lastPotionClear.containsKey(player.getUniqueId())) {
                for (PotionEffect potionEffect : clicked.getActivePotionEffects()) {
                    clicked.removePotionEffect(potionEffect.getType());
                }
                player.sendMessage(Messages.PREFIX + "§aDu hast den Potion-Clear verwendet!");
                lastPotionClear.put(player.getUniqueId(), null);
            } else {
                player.sendMessage(Messages.PREFIX + "§cBitte warte, bis du den Potion-Clear erneut verwenden kannst.");
            }
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = event.getItem().getItemStack();

        if (stack.getType() == Material.EMERALD || stack.getType() == Material.TRIPWIRE_HOOK)
            return;

        if (Perk.ITEM_NAME.isEnabled(player)) {
            event.setCancelled(true);
            ItemStack newStack = new ItemBuilder(stack).setDisplayName("§4§km§c " + player.getName() + " §4§km").build();
            HashMap overflow = player.getInventory().addItem(newStack);

            if (overflow.isEmpty()) {
                event.getItem().remove();
            } else {
                event.getItem().setItemStack((ItemStack) overflow.values().toArray()[0]);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (event.isCancelled())
            return;

        if (Perk.MOB_SPAWNER.isEnabled(player) && block.getType() == Material.MOB_SPAWNER) {
            event.setExpToDrop(0);
            block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.MOB_SPAWNER, 1, (short) block.getData()));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory != null && inventory.getTitle() != null && inventory.getTitle().startsWith("Inventar: ")) {
            event.setCancelled(true);
        }
    }
}

