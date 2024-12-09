package studio.lothus.delivery.commands.register;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.service.LothusService;
import studio.lothus.delivery.commands.BukkitCommand;

public class CreateCouponCommand extends BukkitCommand {

    private final LothusService lothusService;

    public CreateCouponCommand(LothusService lothusService) {
        super("createcoupon", "lothusdelivery.createcoupon", null, false);
        this.lothusService = lothusService;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return;
        }

        String couponKey = args[0];
        double discount;
        try {
            discount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c§lERROR §c O desconto precisa ser um número válido.");
            return;
        }

        boolean success = createCoupon(couponKey, discount, (Player) sender);
        if (success) {
            sender.sendMessage(
                    "§a§lSUCESSO" +
                     " " +
                     "§aCupom " +
                     " " +
                     couponKey +
                     " " +
                     "foi criado com sucesso! " +
                     "Desconto" +
                     " §3"
                     + discount + "%");

        } else {
            sender.sendMessage("§c§lERROR §cFalha ao criar o cupom. Tente novamente.");
        }
    }

    private boolean createCoupon(String couponKey, double discount, Player player) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("key", couponKey);
        requestBody.addProperty("discount", discount);

        String response = lothusService.makeRequest("/integration/coupons/create", "POST", requestBody.toString());

        if (response.isEmpty()) {
            return false;
        }

        JsonObject responseJson = LothusDelivery.getGson().fromJson(response, JsonObject.class);

        if (responseJson.has("error")) {
            JsonElement errorElement = responseJson.get("error");

            if (errorElement.isJsonPrimitive() && errorElement.getAsBoolean()) {
                String errorMessage = responseJson.has("message") ? responseJson.get("message").getAsString() : "Erro desconhecido";

                if ("Key already exists.".equals(errorMessage) && player != null) {
                    player.sendMessage("§c§lCUPOM JÁ EXISTE!");
                }
                return false;
            }
        }

        return true;
    }
}
