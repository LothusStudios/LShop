package studio.lothus.delivery.inventories.register.buyers;

import com.google.gson.JsonElement;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.ViewType;
import me.devnatan.inventoryframework.context.RenderContext;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.inventories.register.StoreInventory;
import studio.lothus.delivery.utils.ItemCreator;

public class BuyersInventory extends View {

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title("Compradores - Lothus Shop");
        config.cancelOnClick();
        config.size(9 * 3);
        config.type(ViewType.CHEST);
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {

        int i = 10;
        for (JsonElement buyer : LothusDelivery.getService().getTopBuyers()) {
            double totalAmount = buyer.getAsJsonObject().get("totalAmount").getAsDouble();

            render.slot(i,
                    new ItemCreator(Material.SKULL_ITEM, "§a" + buyer.getAsJsonObject().get("name").getAsString())
                            .setLore(
                                    "§fEmail: §e" + buyer.getAsJsonObject().get("email").getAsString(),
                                    "",
                                    "§fTotal de pedidos: §e" + buyer.getAsJsonObject().get("totalOrders").getAsInt(),
                                    "§fTotal gasto: §aR$ " + String.format("%.2f", totalAmount)
                            )
                            .build());
            i++;
        }

        render.slot(22,
                        new ItemCreator(Material.ARROW, "§aVoltar")
                                .setLore(
                                        "§eClique para voltar"
                                )
                                .build())
                .onClick((event) -> {
                    LothusDelivery.getInventories().open((event.getPlayer()), StoreInventory.class);
                });
    }
}
