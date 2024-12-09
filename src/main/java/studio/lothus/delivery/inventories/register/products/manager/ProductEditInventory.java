package studio.lothus.delivery.inventories.register.products.manager;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.inventories.register.products.ProductsInventory;
import studio.lothus.delivery.model.Product;
import studio.lothus.delivery.utils.ItemCreator;

import java.util.HashMap;
import java.util.Map;

public class ProductEditInventory extends View implements Listener {

    private final Product product;
    private static final Map<Player, String> playerInput = new HashMap<>();
    private static final Map<Player, String> editingField = new HashMap<>();

    public ProductEditInventory(Product product) {
        this.product = product;
    }

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title("Editar Produto");
        config.size(9 * 3);
        config.cancelOnClick();
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {
        render.slot(11,
                        new ItemCreator(Material.NAME_TAG, "§eEditar Nome")
                                .setLore("§7Clique para editar o nome do produto.")
                                .build())
                .onClick((event) -> {
                    Player player = (Player) event.getPlayer();
                    player.sendMessage("§eDigite o novo nome do produto:");
                    editingField.put(player, "name");
                });

        // Botão para editar o preço
        render.slot(13,
                        new ItemCreator(Material.GOLD_NUGGET, "§eEditar Preço")
                                .setLore("§7Clique para editar o preço do produto.")
                                .build())
                .onClick((event) -> {
                    Player player = (Player) event.getPlayer();
                    player.sendMessage("§eDigite o novo preço do produto:");
                    editingField.put(player, "price");
                });

        render.slot(15,
                        new ItemCreator(Material.GREEN_RECORD, "§aSalvar Alterações")
                                .setLore("§7Clique para salvar as alterações.")
                                .build())
                .onClick((event) -> {
                    Player player = (Player) event.getPlayer();
                    saveProductChanges(player);
                });

        render.slot(17,
                        new ItemCreator(Material.BOOK, "§cCancelar")
                                .setLore("§7Clique para cancelar.")
                                .build())
                .onClick((event) -> {
                    Player player = (Player) event.getPlayer();
                    LothusDelivery.getInventories().open(player, ProductsInventory.class);
                });
    }

    private void saveProductChanges(Player player) {
        String name = playerInput.get(player);
        String priceString = playerInput.get(player);
        if (name != null && !name.isEmpty()) {
            product.setName(name);
        }

        if (priceString != null && !priceString.isEmpty()) {
            try {
                double price = Double.parseDouble(priceString);
                product.setPrice(price);
            } catch (NumberFormatException e) {
                player.sendMessage("§cPreço inválido. Tente novamente.");
                return;
            }
        }

        LothusDelivery.getService().products();

        player.sendMessage("§aProduto atualizado com sucesso!");

        LothusDelivery.getInventories().open(player, ProductsInventory.class);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!editingField.containsKey(player)) return;

        String field = editingField.get(player);
        String message = event.getMessage();

        if (field.equals("name")) {
            playerInput.put(player, message);
            player.sendMessage("§eVocê alterou o nome para: §7" + message);
        } else if (field.equals("price")) {
            playerInput.put(player, message);
            player.sendMessage("§eVocê alterou o preço para: §7" + message);
        }

        editingField.remove(player);
        event.setCancelled(true);
    }
}
