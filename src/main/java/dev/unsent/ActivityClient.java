package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;

public class ActivityClient {
    private final UnsentClient client;
    
    public ActivityClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse get() throws UnsentException {
        return get(null, null);
    }

    public UnsentResponse get(Integer page, Integer limit) throws UnsentException {
        String query = "";
        if (page != null) query += "page=" + page + "&";
        if (limit != null) query += "limit=" + limit + "&";
        if (!query.isEmpty()) query = "?" + query.substring(0, query.length() - 1);
        
        return client.get("/activity" + query);
    }
}
