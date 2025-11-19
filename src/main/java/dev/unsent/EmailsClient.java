package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;

public class EmailsClient {
    private final UnsentClient client;
    
    public EmailsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse send(Object payload) throws UnsentException {
        return create(payload);
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return client.post("/emails", payload);
    }
    
    public UnsentResponse batch(Object emails) throws UnsentException {
        return client.post("/emails/batch", emails);
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
}
