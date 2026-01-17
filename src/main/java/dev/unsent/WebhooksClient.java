package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.Types.CreateWebhookRequest;
import dev.unsent.types.Types.UpdateWebhookRequest;
import java.util.List;

import java.net.URI;

public class WebhooksClient {
    private final UnsentClient client;

    // NOTE: Webhooks are a feature of future and not implemented yet
    public WebhooksClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return client.get("/webhooks");
    }

    public UnsentResponse get(String id) throws UnsentException {
        return client.get("/webhooks/" + id);
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return client.post("/webhooks", payload);
    }

    public UnsentResponse create(String url, List<String> events) throws UnsentException {
        java.util.List<CreateWebhookRequest.EventTypesEnum> eventEnums = events.stream()
            .map(CreateWebhookRequest.EventTypesEnum::fromValue)
            .collect(java.util.stream.Collectors.toList());
        CreateWebhookRequest payload = new CreateWebhookRequest().url(URI.create(url)).eventTypes(eventEnums);
        return create(payload);
    }

    public UnsentResponse create(CreateWebhookRequest payload) throws UnsentException {
        return client.post("/webhooks", payload);
    }
    
    public UnsentResponse update(String id, Object payload) throws UnsentException {
        return client.patch("/webhooks/" + id, payload);
    }

    public UnsentResponse update(String id, String url, List<String> events) throws UnsentException {
        java.util.List<UpdateWebhookRequest.EventTypesEnum> eventEnums = events.stream()
            .map(UpdateWebhookRequest.EventTypesEnum::fromValue)
            .collect(java.util.stream.Collectors.toList());
        UpdateWebhookRequest payload = new UpdateWebhookRequest().url(URI.create(url)).eventTypes(eventEnums);
        return update(id, payload);
    }

    public UnsentResponse update(String id, UpdateWebhookRequest payload) throws UnsentException {
        return client.patch("/webhooks/" + id, payload);
    }
    
    public UnsentResponse delete(String id) throws UnsentException {
        return client.delete("/webhooks/" + id);
    }

    public UnsentResponse test(String id) throws UnsentException {
        return client.post("/webhooks/" + id + "/test", new Object());
    }
}
