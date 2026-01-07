package dev.unsent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UnsentClient {
    private static final String DEFAULT_BASE_URL = "https://api.unsent.dev";
    
    private final String apiKey;
    private final String baseUrl;
    private final boolean raiseOnError;
    private final Gson gson;
    
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
        this.gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
            .create();
        
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
    }
    
    public UnsentResponse request(String method, String path, Object body) throws UnsentException {
        return request(method, path, body, null);
    }

    public UnsentResponse request(String method, String path, Object body, Map<String, String> headers) throws UnsentException {
        try {
            URL url = new URL(baseUrl + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            
            if (body != null) {
                conn.setDoOutput(true);
                String jsonBody = gson.toJson(body);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }
            
            int responseCode = conn.getResponseCode();
            BufferedReader reader;
            
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }
            
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            if (responseCode < 200 || responseCode >= 300) {
                JsonObject errorObj;
                try {
                    errorObj = gson.fromJson(response.toString(), JsonObject.class);
                    if (errorObj.has("error")) {
                        errorObj = errorObj.getAsJsonObject("error");
                    }
                } catch (Exception e) {
                    errorObj = new JsonObject();
                    errorObj.addProperty("code", "INTERNAL_SERVER_ERROR");
                    errorObj.addProperty("message", conn.getResponseMessage());
                }
                
                if (raiseOnError) {
                    throw new UnsentException(responseCode, errorObj, method, path);
                }
                return new UnsentResponse(null, errorObj);
            }
            
            JsonObject data = gson.fromJson(response.toString(), JsonObject.class);
            return new UnsentResponse(data, null);
            
        } catch (UnsentException e) {
            throw e;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("code", "INTERNAL_ERROR");
            error.addProperty("message", e.getMessage());
            
            if (raiseOnError) {
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
