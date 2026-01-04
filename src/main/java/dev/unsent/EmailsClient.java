package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.EmailCreate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class EmailsClient {
    private final UnsentClient client;
    
    public EmailsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse send(Object payload) throws UnsentException {
        return create(payload, null);
    }

    public UnsentResponse send(EmailCreate payload) throws UnsentException {
        return create(payload, null);
    }

    public UnsentResponse send(Object payload, Map<String, String> options) throws UnsentException {
        return create(payload, options);
    }

    public UnsentResponse send(EmailCreate payload, Map<String, String> options) throws UnsentException {
        return create(payload, options);
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return create(payload, null);
    }

    public UnsentResponse create(EmailCreate payload) throws UnsentException {
        return create(payload, null);
    }

    public UnsentResponse create(Object payload, Map<String, String> options) throws UnsentException {
        return client.post("/emails", normalizePayload(payload), extractHeaders(options));
    }

    public UnsentResponse create(EmailCreate payload, Map<String, String> options) throws UnsentException {
        return client.post("/emails", payload, extractHeaders(options));
    }
    
    public UnsentResponse batch(Object emails) throws UnsentException {
        return batch(emails, null);
    }

    public UnsentResponse batch(Object emails, Map<String, String> options) throws UnsentException {
        if (emails instanceof List) {
            List<?> list = (List<?>) emails;
            List<Object> normalized = new ArrayList<>();
            for (Object item : list) {
                normalized.add(normalizePayload(item));
            }
            return client.post("/emails/batch", normalized, extractHeaders(options));
        }
        return client.post("/emails/batch", emails, extractHeaders(options));
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
