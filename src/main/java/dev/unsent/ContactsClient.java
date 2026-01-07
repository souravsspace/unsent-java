package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.CreateContactRequest;
import dev.unsent.types.UpdateContactRequest;

public class ContactsClient {
    private final UnsentClient client;
    
    public ContactsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list(String contactBookId) throws UnsentException {
        return list(contactBookId, null, null, null, null);
    }
    
    public UnsentResponse list(String contactBookId, Integer page, Integer limit) throws UnsentException {
        return list(contactBookId, page, limit, null, null);
    }

    public UnsentResponse list(String contactBookId, Integer page, Integer limit, String emails, String ids) throws UnsentException {
        String query = "";
        if (page != null) query += "page=" + page + "&";
        if (limit != null) query += "limit=" + limit + "&";
        if (emails != null) query += "emails=" + emails + "&";
        if (ids != null) query += "ids=" + ids + "&";
        if (!query.isEmpty()) query = "?" + query.substring(0, query.length() - 1);
        
        return client.get("/contactBooks/" + contactBookId + "/contacts" + query);
    }
    
    public UnsentResponse get(String contactBookId, String contactId) throws UnsentException {
        return client.get("/contactBooks/" + contactBookId + "/contacts/" + contactId);
    }

    public UnsentResponse create(String contactBookId, Object payload) throws UnsentException {
        return client.post("/contactBooks/" + contactBookId + "/contacts", payload);
    }
    
    public UnsentResponse create(String contactBookId, CreateContactRequest payload) throws UnsentException {
        return client.post("/contactBooks/" + contactBookId + "/contacts", payload);
    }
    
    public UnsentResponse update(String contactBookId, String contactId, Object payload) throws UnsentException {
        return client.patch("/contactBooks/" + contactBookId + "/contacts/" + contactId, payload);
    }

    public UnsentResponse update(String contactBookId, String contactId, UpdateContactRequest payload) throws UnsentException {
        return client.patch("/contactBooks/" + contactBookId + "/contacts/" + contactId, payload);
    }

    // Since 'upsert' uses PUT on the specific contact resource or collection?
    // API ref: PUT /v1/contactBooks/{contactBookId}/contacts/{contactId}
    public UnsentResponse upsert(String contactBookId, String contactId, Object payload) throws UnsentException {
        return client.put("/contactBooks/" + contactBookId + "/contacts/" + contactId, payload);
    }

    public UnsentResponse upsert(String contactBookId, String contactId, CreateContactRequest payload) throws UnsentException {
        return client.put("/contactBooks/" + contactBookId + "/contacts/" + contactId, payload);
    }

    public UnsentResponse delete(String contactBookId, String contactId) throws UnsentException {
        return client.delete("/contactBooks/" + contactBookId + "/contacts/" + contactId);
    }
}
