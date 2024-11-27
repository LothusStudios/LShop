package studio.lothus.api.inventory.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryItem {

    private final ItemStack item;
    private final InventoryItem clickHandler;

    public InventoryItem(ItemStack item, InventoryItem clickHandler) {
        this.item = item;
        this.clickHandler = clickHandler;
    }

    public ItemStack getItem() {
        return item;
    }

    public InventoryItem getClickHandler() {
        return clickHandler;
    }

    public void onClick(Player whoClicked, InventoryClickEvent event) {
    }
}