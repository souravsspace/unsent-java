package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;

public class EventsClient {
    private final UnsentClient client;
    
    public EventsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return list(null, null, null, null);
    }

    public UnsentResponse list(Integer page, Integer limit, String status, String startDate) throws UnsentException {
        String query = "";
        if (page != null) query += "page=" + page + "&";
        if (limit != null) query += "limit=" + limit + "&";
        if (status != null) query += "status=" + status + "&";
        if (startDate != null) query += "startDate=" + startDate + "&";
        if (!query.isEmpty()) query = "?" + query.substring(0, query.length() - 1);
        
        return client.get("/events" + query);
    }
}
