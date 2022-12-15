package de.pauhull.candycane.listener;

import com.hazebyte.crate.api.CrateAPI;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import de.pauhull.candycane.CandyCane;
import de.pauhull.candycane.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Paul
 * on 26.03.2019
 *
 * @author pauhull
 */
public class VotifierListener implements Listener {

    private final CandyCane candyCane;

    public VotifierListener(CandyCane candyCane) {
        this.candyCane = candyCane;
        Bukkit.getPluginManager().registerEvents(this, candyCane);
    }

    @EventHandler
    public void onVotifier(VotifierEvent event) {
        Vote vote = event.getVote();

        Player player = Bukkit.getPlayer(vote.getUsername());

        if (player == null) {
            return;
        }

        Bukkit.broadcastMessage(Messages.PREFIX + "§f§l" + player.getName() + "§d§l hat bei §f§l" + vote.getServiceName() + "§d§l gevotet und eine §oBelohnung §d§lerhalten! §5§o/vote");
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD), new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.IRON_AXE), new ItemStack(Material.IRON_SPADE), new ItemStack(Material.COOKED_BEEF, 16), new ItemStack(Material.EXP_BOTTLE, 16));
        CrateAPI.getCrateRegistrar().getCrate("VoteKey").giveTo(player, 1);
    }

}
