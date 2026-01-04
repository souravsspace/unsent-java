package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.DomainCreate;

public class DomainsClient {
    private final UnsentClient client;
    
    public DomainsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return client.get("/domains");
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return client.post("/domains", payload);
    }

    public UnsentResponse create(DomainCreate payload) throws UnsentException {
        return client.post("/domains", payload);
    }
    
    public UnsentResponse verify(int domainId) throws UnsentException {
        return client.put("/domains/" + domainId + "/verify", new Object());
    }
    
    public UnsentResponse get(int domainId) throws UnsentException {
        return client.get("/domains/" + domainId);
    }
    
    public UnsentResponse delete(int domainId) throws UnsentException {
        return client.delete("/domains/" + domainId);
    }
}
