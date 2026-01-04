package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.ContactCreate;

public class ContactsClient {
    private final UnsentClient client;
    
    public ContactsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse create(String bookId, Object payload) throws UnsentException {
        return client.post("/contactBooks/" + bookId + "/contacts", payload);
    }

    public UnsentResponse create(String bookId, ContactCreate payload) throws UnsentException {
        return client.post("/contactBooks/" + bookId + "/contacts", payload);
    }
    
    public UnsentResponse get(String bookId, String contactId) throws UnsentException {
        return client.get("/contactBooks/" + bookId + "/contacts/" + contactId);
    }
    
    public UnsentResponse update(String bookId, String contactId, Object payload) throws UnsentException {
        return client.patch("/contactBooks/" + bookId + "/contacts/" + contactId, payload);
    }

    public UnsentResponse update(String bookId, String contactId, ContactCreate payload) throws UnsentException {
        return client.patch("/contactBooks/" + bookId + "/contacts/" + contactId, payload);
    }
    
    public UnsentResponse upsert(String bookId, String contactId, Object payload) throws UnsentException {
        return client.put("/contactBooks/" + bookId + "/contacts/" + contactId, payload);
    }

    public UnsentResponse upsert(String bookId, String contactId, ContactCreate payload) throws UnsentException {
        return client.put("/contactBooks/" + bookId + "/contacts/" + contactId, payload);
    }
    
    public UnsentResponse delete(String bookId, String contactId) throws UnsentException {
        return client.delete("/contactBooks/" + bookId + "/contacts/" + contactId);
    }
}
