package studio.lothus.delivery.inventories.register;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.ViewType;
import me.devnatan.inventoryframework.context.RenderContext;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.inventories.register.buyers.BuyersInventory;
import studio.lothus.delivery.inventories.register.coupon.CouponsInventory;
import studio.lothus.delivery.inventories.register.products.ProductsInventory;
import studio.lothus.delivery.utils.ItemCreator;

public class StoreInventory extends View {

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title("Loja - Lothus Shop");
        config.cancelOnClick();
        config.size(9*3);
        config.type(ViewType.CHEST);
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {
        render.slot(10,
                new ItemCreator(Material.STORAGE_MINECART, "§aGerenciar Produtos")
                        .setLore(
                                "§eClique para gerenciar os produtos"
                        )
                        .build())
                .onClick((event) -> {
                    LothusDelivery.getInventories().open((Player) event.getPlayer(), ProductsInventory.class);
                });

        render.slot(11,
                        new ItemCreator(Material.PAPER, "§aGerenciar Cupons")
                                .setLore("§eClique para gerenciar os cupons")
                                .build())
                .onClick((event) -> {
                    LothusDelivery.getInventories().open((Player) event.getPlayer(), CouponsInventory.class);
                });

        render.slot(13,
                new ItemCreator(Material.EMERALD, "§aResumo Total")
                        .setLore(
                                "",
                                "§eÚltimas 24h:",
                                "§fTotal de vendas: §e" + LothusDelivery.getService().getStats().get("totalDayOrders").getAsInt(),
                                "§fTotal em vendas: §aR$ " + String.format("%.2f", LothusDelivery.getService().getStats().get("totalDay").getAsDouble()),
                                "",
                                "§eÚltimas 7d:",
                                "§fTotal de vendas: §e" + LothusDelivery.getService().getStats().get("totalWeekOrders").getAsInt(),
                                "§fTotal em vendas: §aR$ " + String.format("%.2f", LothusDelivery.getService().getStats().get("totalWeek").getAsDouble()),
                                "",
                                "§eÚltimas 30d:",
                                "§fTotal de vendas: §e" + LothusDelivery.getService().getStats().get("totalMonthOrders").getAsInt(),
                                "§fTotal em vendas: §aR$ " + String.format("%.2f", LothusDelivery.getService().getStats().get("totalMonth").getAsDouble()),
                                "",
                                "§eAcesse o painel de controle para mais informações."
                        )

                        .build());

        render.slot(15,
                new ItemCreator(Material.BOOK, "§aVer Pedidos")
                        .setLore(
                                "§eClique para ver os pedidos"
                        )
                        .build());

        render.slot(16,
                new ItemCreator(Material.ARMOR_STAND, "§aTop Compradores")
                        .setLore(
                                "§eClique para ver os top compradores"
                        )
                        .build())
                .onClick(((event) -> {
                    LothusDelivery.getInventories().open((Player) event.getPlayer(), BuyersInventory.class);
        }));
    }
}
