package studio.lothus.delivery.inventories.register.products;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.inventories.register.StoreInventory;
import studio.lothus.delivery.model.Product;
import studio.lothus.delivery.utils.ItemCreator;

public class ProductsInventory extends View {

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title("Produtos - Lothus Shop");
        config.size(9 * 5);
        config.cancelOnClick();
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {
        int i = 9;

        for (Product element : LothusDelivery.getService().getProducts()) {
            i++;

            if (i > 16 && i < 19) {
                i = 19;
            }

            if (i > 25 && i < 28) {
                i = 28;
            }

            if (i > 34) {
                continue;
            }

            render.slot(i, new ItemCreator(Material.PAPER, "§a" + element.getName())
                    .setLore(
                            "§fID: §e" + element.getId(),
                            "§fPreço: §aR$ " + element.getPrice(),
                            "",
                            "§fEstoque: §e" + (element.getConfig().getStock() != -1 ? element.getConfig().getStock() : "Infinito"),
                            "§fVisibilidade: " + (element.getConfig().isShow() ? "§aVisível" : "§cOculto"),
                            "",
                            "§eClique para editar"
                    ).build()).onClick((event) -> {
                Player player = (Player) event.getPlayer();
                player.sendMessage("§c§lEm breve §cFique ligado nas atualização!");
            });
        }

        render.slot(40, new ItemCreator(Material.ARROW, "§cVoltar")
                        .setLore("§eClique para voltar").build())
                .onClick((event) -> {
                    LothusDelivery.getInventories().open(event.getPlayer(), StoreInventory.class);
                });
    }
}
