package racketslime.deadpanda.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import racketslime.deadpanda.utils.color;

public class ItemManager {

    public static ItemStack pole;

    public static void init() {
        createPole();
    }

    private static void createPole() {
        ItemStack item = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.KNOCKBACK, 5, true);
        meta.setDisplayName(color.Set("&cSlime Tool"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        pole = item;
    }


}
