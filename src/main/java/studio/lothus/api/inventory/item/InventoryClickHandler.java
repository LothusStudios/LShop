package studio.lothus.api.inventory.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryClickHandler {
    void onClick(Player player, InventoryClickEvent event);
}