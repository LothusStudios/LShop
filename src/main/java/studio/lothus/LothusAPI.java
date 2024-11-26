package studio.lothus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import studio.lothus.model.Order;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LothusAPI {

    private static final String ENDPOINT = "https://api.lothus.shop/";
    private final String token;
    private final OkHttpClient client;

    public LothusAPI(String token) {
        this.token = token;
        this.client = new OkHttpClient();
    }

    private String getAuthHeader() {
        return "Bearer " + token;
    }

    public Response makeRequest(String endpoint, String method, JsonObject body) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(ENDPOINT + endpoint)
                .header("Authorization", getAuthHeader())
                .header("Content-Type", "application/json")
                .header("User-Agent", "LothusPlugin");

        if (body != null) {
            requestBuilder.method(method, RequestBody.create(MediaType.parse("application/json"), body.toString()));
        } else {
            requestBuilder.method(method, null);
        }

        try (okhttp3.Response response = client.newCall(requestBuilder.build()).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";
            return new Response(response.isSuccessful(), response.code(), responseBody);
        } catch (IOException e) {
            System.err.println("Failed to connect to the API: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Order> getPendingOrders() {
        Response response = makeRequest("orders/pending", "GET", null);
        if (response == null || !response.isSuccessful()) {
            handleErrorResponse(response);
            return null;
        }
        return Arrays.asList(new Gson().fromJson(response.getBody(), Order[].class));
    }

    private void handleErrorResponse(Response response) {
        if (response == null) return;
        if (response.getStatusCode() >= 500) {
            System.err.println("Internal server error when connecting to Lothus API.");
        } else if (response.getStatusCode() == 401) {
            System.err.println("Unauthorized access. Please check your token.");
        } else {
            System.err.println("API request failed: " + response.getBody());
        }
    }

    private static class Response {
        private final boolean success;
        private final int statusCode;
        private final String body;

        public Response(boolean success, int statusCode, String body) {
            this.success = success;
            this.statusCode = statusCode;
            this.body = body;
        }

        public boolean isSuccessful() {
            return success;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getBody() {
            return body;
        }
    }
}