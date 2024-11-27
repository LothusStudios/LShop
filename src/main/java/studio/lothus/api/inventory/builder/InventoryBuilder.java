package studio.lothus.api.inventory.builder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import studio.lothus.api.inventory.item.InventoryClickHandler;
import studio.lothus.api.inventory.item.InventoryItem;

import java.util.HashMap;
import java.util.Map;

public class InventoryBuilder implements Listener {

    private final JavaPlugin plugin;
    private final Inventory inventory;
    private final Map<Integer, InventoryItem> items = new HashMap<>();

    public InventoryBuilder(JavaPlugin plugin, String title, int size) {
        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(null, size, title);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public InventoryBuilder addItem(int slot, ItemStack item, InventoryClickHandler clickHandler) {
        items.put(slot, new InventoryItem(item, (InventoryItem) clickHandler));
        inventory.setItem(slot, item);
        return this;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(inventory)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            InventoryItem inventoryItem = items.get(slot);
            if (inventoryItem != null) {
                inventoryItem.getClickHandler().onClick((Player) event.getWhoClicked(), event);
            }
        }
    }
}