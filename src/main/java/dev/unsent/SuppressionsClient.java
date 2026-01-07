package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.AddSuppressionRequest;

public class SuppressionsClient {
    private final UnsentClient client;
    
    public SuppressionsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return list(null, null, null, null);
    }

    public UnsentResponse list(Integer page, Integer limit, String search, String reason) throws UnsentException {
        String query = "";
        if (page != null) query += "page=" + page + "&";
        if (limit != null) query += "limit=" + limit + "&";
        if (search != null) query += "search=" + search + "&";
        if (reason != null) query += "reason=" + reason + "&";
        if (!query.isEmpty()) query = "?" + query.substring(0, query.length() - 1);
        
        return client.get("/suppressions" + query);
    }
    
    public UnsentResponse add(Object payload) throws UnsentException {
        return client.post("/suppressions", payload);
    }

    public UnsentResponse add(AddSuppressionRequest payload) throws UnsentException {
        return client.post("/suppressions", payload);
    }
    
    public UnsentResponse delete(String email) throws UnsentException {
        return client.delete("/suppressions/email/" + email);
    }
}
