package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.CreateApiKeyRequest;

public class ApiKeysClient {
    private final UnsentClient client;
    
    public ApiKeysClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return client.get("/api-keys");
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return client.post("/api-keys", payload);
    }
    
    public UnsentResponse create(CreateApiKeyRequest payload) throws UnsentException {
        return client.post("/api-keys", payload);
    }
    
    public UnsentResponse delete(String id) throws UnsentException {
        return client.delete("/api-keys/" + id);
    }
}
