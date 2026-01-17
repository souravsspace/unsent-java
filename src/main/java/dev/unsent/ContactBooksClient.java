package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.Types.CreateContactBookRequest;
import dev.unsent.types.Types.UpdateContactBookRequest;

public class ContactBooksClient {
    private final UnsentClient client;
    
    public ContactBooksClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return client.get("/contactBooks");
    }
    
    public UnsentResponse get(String contactBookId) throws UnsentException {
        return client.get("/contactBooks/" + contactBookId);
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return client.post("/contactBooks", payload);
    }

    public UnsentResponse create(CreateContactBookRequest payload) throws UnsentException {
        return client.post("/contactBooks", payload);
    }
    
    public UnsentResponse update(String contactBookId, Object payload) throws UnsentException {
        return client.patch("/contactBooks/" + contactBookId, payload);
    }

    public UnsentResponse update(String contactBookId, UpdateContactBookRequest payload) throws UnsentException {
        return client.patch("/contactBooks/" + contactBookId, payload);
    }
    
    public UnsentResponse delete(String contactBookId) throws UnsentException {
        return client.delete("/contactBooks/" + contactBookId);
    }
}
