package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;

public class MetricsClient {
    private final UnsentClient client;
    
    public MetricsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse get() throws UnsentException {
        return get(null);
    }

    public UnsentResponse get(String period) throws UnsentException {
        String query = "";
        if (period != null) query += "period=" + period + "&";
        if (!query.isEmpty()) query = "?" + query.substring(0, query.length() - 1);
        
        return client.get("/metrics" + query);
    }
}
