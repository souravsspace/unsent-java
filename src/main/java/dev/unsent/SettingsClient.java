package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;

public class SettingsClient {
    private final UnsentClient client;
    
    public SettingsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse get() throws UnsentException {
        return client.get("/settings");
    }
}
