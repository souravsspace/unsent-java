package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.CreateDomainRequest;

public class DomainsClient {
    private final UnsentClient client;
    
    public DomainsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return client.get("/domains");
    }
    
    public UnsentResponse get(String domainId) throws UnsentException {
        return client.get("/domains/" + domainId);
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return client.post("/domains", payload);
    }

    public UnsentResponse create(CreateDomainRequest payload) throws UnsentException {
        return client.post("/domains", payload);
    }
    
    public UnsentResponse verify(String domainId) throws UnsentException {
        return client.put("/domains/" + domainId + "/verify", new Object());
    }

    public UnsentResponse delete(String domainId) throws UnsentException {
        return client.delete("/domains/" + domainId);
    }
}
