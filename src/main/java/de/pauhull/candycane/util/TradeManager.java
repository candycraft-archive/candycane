package de.pauhull.candycane.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul
 * on 22.02.2019
 *
 * @author pauhull
 */
public class TradeManager {

    private static final ItemStack NO_ITEM = new ItemBuilder(Material.BARRIER).setDisplayName("§cDer §eAlchemist §cfunktioniert nicht so.").build();

    public static ItemStack getTradeItem(ItemStack stack1, ItemStack stack2) {

        if (stack1 == null || stack2 == null || stack1.getType() != Material.ENCHANTED_BOOK || stack2.getType() != Material.ENCHANTED_BOOK) {
            return NO_ITEM;
        }

        EnchantmentStorageMeta meta1 = (EnchantmentStorageMeta) stack1.getItemMeta();
        EnchantmentStorageMeta meta2 = (EnchantmentStorageMeta) stack2.getItemMeta();
        Map<Enchantment, Integer> oldEnchantments = meta1.getStoredEnchants();
        Map<Enchantment, Integer> newEnchantments = new HashMap<>();

        for (Enchantment enchantment : oldEnchantments.keySet()) {
            int level = oldEnchantments.get(enchantment);

            if (meta2.hasStoredEnchant(enchantment)) {
                if (level == meta2.getStoredEnchantLevel(enchantment) && level != enchantment.getMaxLevel()) {
                    level++;
                } else if (level <= meta2.getStoredEnchantLevel(enchantment)) {
                    level = meta2.getStoredEnchantLevel(enchantment);
                }
            }

            newEnchantments.put(enchantment, level);
        }

        for (Enchantment enchantment : meta2.getStoredEnchants().keySet()) {
            int level = meta2.getStoredEnchants().get(enchantment);
            if (newEnchantments.containsKey(enchantment)) continue;
            newEnchantments.put(enchantment, level);
        }

        ItemStack stack = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) stack.getItemMeta();

        for (Enchantment enchantment : newEnchantments.keySet()) {
            bookMeta.addStoredEnchant(enchantment, newEnchantments.get(enchantment), true);
        }

        stack.setItemMeta(bookMeta);
        return stack;
    }

}
