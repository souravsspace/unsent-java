package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;

public class TeamsClient {
    private final UnsentClient client;
    
    public TeamsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return client.get("/teams");
    }

    public UnsentResponse get() throws UnsentException {
        return client.get("/team");
    }
}
