package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;

public class StatsClient {
    private final UnsentClient client;
    
    public StatsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse get() throws UnsentException {
        return get(null, null);
    }

    public UnsentResponse get(String startDate, String endDate) throws UnsentException {
        String query = "";
        if (startDate != null) query += "startDate=" + startDate + "&";
        if (endDate != null) query += "endDate=" + endDate + "&";
        if (!query.isEmpty()) query = "?" + query.substring(0, query.length() - 1);
        
        return client.get("/stats" + query);
    }
}
