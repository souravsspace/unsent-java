package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.CreateWebhookRequest;
import dev.unsent.UpdateWebhookRequest;
import java.util.List;

public class WebhooksClient {
    private final UnsentClient client;

    // NOTE: Webhooks are a feature of future and not implemented yet
    public WebhooksClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return client.get("/webhooks");
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return client.post("/webhooks", payload);
    }

    public UnsentResponse create(String url, List<String> events) throws UnsentException {
        CreateWebhookRequest payload = new CreateWebhookRequest().url(url).events(events);
        return create(payload);
    }

    public UnsentResponse create(CreateWebhookRequest payload) throws UnsentException {
        return client.post("/webhooks", payload);
    }
    
    public UnsentResponse update(String id, Object payload) throws UnsentException {
        return client.patch("/webhooks/" + id, payload);
    }

    public UnsentResponse update(String id, String url, List<String> events) throws UnsentException {
        UpdateWebhookRequest payload = new UpdateWebhookRequest().url(url).events(events);
        return update(id, payload);
    }

    public UnsentResponse update(String id, UpdateWebhookRequest payload) throws UnsentException {
        return client.patch("/webhooks/" + id, payload);
    }
    
    public UnsentResponse delete(String id) throws UnsentException {
        return client.delete("/webhooks/" + id);
    }
}
