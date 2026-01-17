package dev.unsent;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UnsentClient {
    private static final String DEFAULT_BASE_URL = "https://api.unsent.dev";
    
    private final String apiKey;
    private final String baseUrl;
    private final boolean raiseOnError;
    private final Gson gson;
    private final OkHttpClient httpClient;
    
    public final EmailsClient emails;
    public final ContactsClient contacts;
    public final CampaignsClient campaigns;
    public final DomainsClient domains;
    public final TemplatesClient templates;
    public final ContactBooksClient contactBooks;
    public final SuppressionsClient suppressions;
    public final ApiKeysClient apiKeys;
    public final AnalyticsClient analytics;
    public final SettingsClient settings;
    public final WebhooksClient webhooks;
    public final SystemClient system;
    public final ActivityClient activity;
    public final EventsClient events;
    public final MetricsClient metrics;
    public final StatsClient stats;
    public final TeamsClient teams;
    
    public UnsentClient(String apiKey) {
        this(apiKey, null, true);
    }
    
    public UnsentClient(String apiKey, String baseUrl, boolean raiseOnError) {
        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = System.getenv("UNSENT_API_KEY");
        }
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("Missing API key. Pass it to UnsentClient constructor or set UNSENT_API_KEY environment variable");
        }
        
        this.apiKey = apiKey;
        String url = baseUrl != null ? baseUrl : System.getenv("UNSENT_BASE_URL");
        this.baseUrl = (url != null ? url : DEFAULT_BASE_URL) + "/v1";
        this.raiseOnError = raiseOnError;
        
        // Use the shared Gson instance with all adapters registered
        if (JSON.getGson() == null) {
            JSON.setGson(JSON.createGson().create());
        }
        this.gson = JSON.getGson();
        
        this.httpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build();
        
        // Initialize resource clients
        this.emails = new EmailsClient(this);
        this.contacts = new ContactsClient(this);
        this.campaigns = new CampaignsClient(this);
        this.domains = new DomainsClient(this);
        this.templates = new TemplatesClient(this);
        this.contactBooks = new ContactBooksClient(this);
        this.suppressions = new SuppressionsClient(this);
        this.apiKeys = new ApiKeysClient(this);
        this.analytics = new AnalyticsClient(this);
        this.settings = new SettingsClient(this);
        this.webhooks = new WebhooksClient(this);
        this.system = new SystemClient(this);
        this.activity = new ActivityClient(this);
        this.events = new EventsClient(this);
        this.metrics = new MetricsClient(this);
        this.stats = new StatsClient(this);
        this.teams = new TeamsClient(this);
    }
    
    public UnsentResponse request(String method, String path, Object body) throws UnsentException {
        return request(method, path, body, null);
    }

    public UnsentResponse request(String method, String path, Object body, Map<String, String> headers) throws UnsentException {
        try {
            Request.Builder requestBuilder = new Request.Builder()
                .url(this.baseUrl + path)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json");

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }

            RequestBody requestBody = null;
            if (body != null) {
                String jsonBody = gson.toJson(body);
                requestBody = RequestBody.create(jsonBody, MediaType.parse("application/json; charset=utf-8"));
            } else if (method.equals("POST") || method.equals("PUT") || method.equals("PATCH")) {
                requestBody = RequestBody.create("", MediaType.parse("application/json; charset=utf-8"));
            }

            requestBuilder.method(method, requestBody);
            
            try (Response response = httpClient.newCall(requestBuilder.build()).execute()) {
                String responseBodyStr = response.body() != null ? response.body().string() : "{}";
                
                // Try parsing as JSON, handle non-JSON responses gracefully
                JsonElement jsonElement;
                try {
                     jsonElement = JsonParser.parseString(responseBodyStr);
                } catch (Exception e) {
                     // Not a JSON response
                     jsonElement = new JsonObject();
                }

                if (!response.isSuccessful()) {
                    JsonObject errorObj;
                    if (jsonElement.isJsonObject()) {
                        errorObj = jsonElement.getAsJsonObject();
                        if (errorObj.has("error")) {
                            JsonElement errorElem = errorObj.get("error");
                            if (errorElem.isJsonObject()) {
                                errorObj = errorElem.getAsJsonObject();
                            }
                        }
                    } else {
                        errorObj = new JsonObject();
                        errorObj.addProperty("code", "ERROR");
                        errorObj.addProperty("message", response.message());
                        errorObj.addProperty("body", responseBodyStr);
                    }

                    if (raiseOnError) {
                        throw new UnsentException(response.code(), errorObj, method, path);
                    }
                    return new UnsentResponse(null, errorObj);
                }

                JsonObject data;
                if (jsonElement.isJsonObject()) {
                    data = jsonElement.getAsJsonObject();
                } else if (jsonElement.isJsonArray()) {
                    // Wrap array in a data object
                    data = new JsonObject();
                    data.add("data", jsonElement.getAsJsonArray());
                } else {
                    data = new JsonObject();
                    // Handle primitives if necessary, primarily we expect Object or Array
                }

                return new UnsentResponse(data, null);
            }

        } catch (UnsentException e) {
            throw e;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("code", "INTERNAL_ERROR");
            error.addProperty("message", e.getMessage());
            
            if (raiseOnError) {
                // If it's IO exception etc
                throw new UnsentException(500, error, method, path);
            }
            return new UnsentResponse(null, error);
        }
    }
    
    public UnsentResponse post(String path, Object body) throws UnsentException {
        return request("POST", path, body, null);
    }

    public UnsentResponse post(String path, Object body, Map<String, String> headers) throws UnsentException {
        return request("POST", path, body, headers);
    }
    
    public UnsentResponse get(String path) throws UnsentException {
        return request("GET", path, null, null);
    }

    public UnsentResponse get(String path, Map<String, String> headers) throws UnsentException {
        return request("GET", path, null, headers);
    }
    
    public UnsentResponse put(String path, Object body) throws UnsentException {
        return request("PUT", path, body, null);
    }

    public UnsentResponse put(String path, Object body, Map<String, String> headers) throws UnsentException {
        return request("PUT", path, body, headers);
    }
    
    public UnsentResponse patch(String path, Object body) throws UnsentException {
        return request("PATCH", path, body, null);
    }

    public UnsentResponse patch(String path, Object body, Map<String, String> headers) throws UnsentException {
        return request("PATCH", path, body, headers);
    }
    
    public UnsentResponse delete(String path) throws UnsentException {
        return request("DELETE", path, null, null);
    }

    public UnsentResponse delete(String path, Map<String, String> headers) throws UnsentException {
        return request("DELETE", path, null, headers);
    }
    
    public static class UnsentResponse {
        public final JsonObject data;
        public final JsonObject error;
        
        public UnsentResponse(JsonObject data, JsonObject error) {
            this.data = data;
            this.error = error;
        }
        
        public boolean isSuccess() {
            return error == null;
        }
    }
}
