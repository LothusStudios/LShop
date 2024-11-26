package studio.lothus.api.inventory.item;

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
}