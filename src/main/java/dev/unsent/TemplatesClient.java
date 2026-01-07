package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.CreateTemplateRequest;
import dev.unsent.types.UpdateTemplateRequest;

public class TemplatesClient {
    private final UnsentClient client;
    
    public TemplatesClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return client.get("/templates");
    }
    
    public UnsentResponse get(String templateId) throws UnsentException {
        return client.get("/templates/" + templateId);
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return client.post("/templates", payload);
    }

    public UnsentResponse create(CreateTemplateRequest payload) throws UnsentException {
        return client.post("/templates", payload);
    }
    
    public UnsentResponse update(String templateId, Object payload) throws UnsentException {
        return client.patch("/templates/" + templateId, payload);
    }

    public UnsentResponse update(String templateId, UpdateTemplateRequest payload) throws UnsentException {
        return client.patch("/templates/" + templateId, payload);
    }
    
    public UnsentResponse delete(String templateId) throws UnsentException {
        return client.delete("/templates/" + templateId);
    }
}
