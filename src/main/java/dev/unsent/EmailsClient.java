package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.SendEmailRequest; // Depending on how generator handled AnyOf, might be needed
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class EmailsClient {
    private final UnsentClient client;
    
    public EmailsClient(UnsentClient client) {
        this.client = client;
    }
    
    // --- Send Email ---

    public UnsentResponse send(Object payload) throws UnsentException {
        return create(payload, null);
    }

    public UnsentResponse send(SendEmailRequest payload) throws UnsentException {
        return create(payload, null);
    }

    public UnsentResponse send(Object payload, Map<String, String> options) throws UnsentException {
        return create(payload, options);
    }

    public UnsentResponse send(SendEmailRequest payload, Map<String, String> options) throws UnsentException {
        return create(payload, options);
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return create(payload, null);
    }

    public UnsentResponse create(SendEmailRequest payload) throws UnsentException {
        return create(payload, null);
    }

    public UnsentResponse create(Object payload, Map<String, String> options) throws UnsentException {
        return client.post("/emails", normalizePayload(payload), extractHeaders(options));
    }

    public UnsentResponse create(SendEmailRequest payload, Map<String, String> options) throws UnsentException {
        return client.post("/emails", payload, extractHeaders(options));
    }
    
    // --- Batch ---

    public UnsentResponse batch(List<?> emails) throws UnsentException {
        return batch(emails, null);
    }

    public UnsentResponse batch(List<?> emails, Map<String, String> options) throws UnsentException {
        // Simple normalization if it's a list of Objects (Maps)
        List<Object> normalized = new ArrayList<>();
        for (Object item : emails) {
            normalized.add(normalizePayload(item));
        }
        return client.post("/emails/batch", normalized, extractHeaders(options));
    }
    
    // --- Operations ---

    public UnsentResponse list() throws UnsentException {
        return list(null, null, null, null, null);
    }
    
    public UnsentResponse list(Integer page, Integer limit) throws UnsentException {
        return list(page, limit, null, null, null);
    }

    public UnsentResponse list(Integer page, Integer limit, String startDate, String endDate, String domainId) throws UnsentException {
        String query = "";
        if (page != null) query += "page=" + page + "&";
        if (limit != null) query += "limit=" + limit + "&";
        if (startDate != null) query += "startDate=" + startDate + "&";
        if (endDate != null) query += "endDate=" + endDate + "&";
        if (domainId != null) query += "domainId=" + domainId + "&";
        if (!query.isEmpty()) query = "?" + query.substring(0, query.length() - 1);
        
        return client.get("/emails" + query);
    }

    public UnsentResponse get(String emailId) throws UnsentException {
        return client.get("/emails/" + emailId);
    }
    
    public UnsentResponse update(String emailId, Object payload) throws UnsentException {
        return client.patch("/emails/" + emailId, payload);
    }
    
    public UnsentResponse cancel(String emailId) throws UnsentException {
        return client.post("/emails/" + emailId + "/cancel", new Object());
    }

    // --- Analytics / Lists ---

    public UnsentResponse getBounces(Integer page, Integer limit) throws UnsentException {
        String query = buildPaginationQuery(page, limit);
        return client.get("/emails/bounces" + query);
    }

    public UnsentResponse getComplaints(Integer page, Integer limit) throws UnsentException {
        String query = buildPaginationQuery(page, limit);
        return client.get("/emails/complaints" + query);
    }

    public UnsentResponse getUnsubscribes(Integer page, Integer limit) throws UnsentException {
        String query = buildPaginationQuery(page, limit);
        return client.get("/emails/unsubscribes" + query);
    }

    private String buildPaginationQuery(Integer page, Integer limit) {
        String query = "";
        if (page != null) query += "page=" + page + "&";
        if (limit != null) query += "limit=" + limit + "&";
        return query.isEmpty() ? "" : "?" + query.substring(0, query.length() - 1);
    }

    private Map<String, String> extractHeaders(Map<String, String> options) {
        if (options == null) {
            return null;
        }
        Map<String, String> headers = new HashMap<>();
        if (options.containsKey("idempotencyKey")) {
            headers.put("Idempotency-Key", options.get("idempotencyKey"));
        }
        // Add other options as headers if needed, or just pass through if they are already headers
        for (Map.Entry<String, String> entry : options.entrySet()) {
            if (!entry.getKey().equals("idempotencyKey")) {
                headers.put(entry.getKey(), entry.getValue());
            }
        }
        return headers;
    }

    @SuppressWarnings("unchecked")
    private Object normalizePayload(Object payload) {
        if (payload instanceof Map) {
            Map<String, Object> map = new HashMap<>((Map<String, Object>) payload);
            if (map.containsKey("from_") && !map.containsKey("from")) {
                map.put("from", map.remove("from_"));
            }
            return map;
        }
        return payload;
    }
}
