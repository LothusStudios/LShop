package studio.lothus.delivery.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import okhttp3.*;
import org.bukkit.Bukkit;
import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.model.Product;

import java.io.IOException;
import java.util.List;

public class LothusService {

    private String token;
    private OkHttpClient client;

    private final String API_URL = "https://api.lothus.shop";

    @Getter
    private JsonArray pendingOrders;

    @Getter
    private JsonArray topBuyers;

    @Getter
    private List<Product> products;

    @Getter
    private JsonObject stats;

    @Getter
    private JsonArray coupons;


    public LothusService(String token) {
        this.token = token;
        this.client = new OkHttpClient();
    }

    public boolean checkToken() {
        String response = makeRequest("/integration/check", "GET", null);

        JsonObject object = LothusDelivery.getGson().fromJson(response, JsonObject.class);

        return object.get("response").getAsBoolean();
    }

    public void pendingOrders() {
        String response = makeRequest("/integration/pendingOrders", "GET", null);

        JsonObject object = LothusDelivery.getGson().fromJson(response, JsonObject.class);

        pendingOrders = object.getAsJsonArray("response");
    }
    public void products() {
        String response = makeRequest("/integration/products", "GET", null);

        JsonObject object = LothusDelivery.getGson().fromJson(response, JsonObject.class);

        products = LothusDelivery.getGson().fromJson(object.getAsJsonArray("response"), new TypeToken<List<Product>>() {}.getType());
    }
    public void topBuyers() {
        String response = makeRequest("/integration/buyers/7", "GET", null);

        JsonObject object = LothusDelivery.getGson().fromJson(response, JsonObject.class);

        topBuyers = object.getAsJsonArray("response");
    }
    public void stats() {
        String response = makeRequest("/integration/stats", "GET", null);

        JsonObject object = LothusDelivery.getGson().fromJson(response, JsonObject.class);

        stats = object.getAsJsonObject("response");
    }

    public void coupons() {
        String response = makeRequest("/integration/coupons", "GET", null);

        JsonObject object = LothusDelivery.getGson().fromJson(response, JsonObject.class);

        if (object.has("response") && object.get("response").isJsonArray()) {
            coupons = object.getAsJsonArray("response");
        } else {
            coupons = new JsonArray();
        }
    }


    public String makeRequest(String route, String method, String body) {
        RequestBody requestBody = RequestBody.create(body == null ? "" : body, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(API_URL + route)
                .method(method, (method.equals("GET") ? null : requestBody))
                .addHeader("App-Token", token)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseString = response.body().string();
            return responseString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
