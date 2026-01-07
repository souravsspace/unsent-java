package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;

public class SystemClient {
    private final UnsentClient client;
    
    public SystemClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse health() throws UnsentException {
        return client.get("/health");
    }
}
