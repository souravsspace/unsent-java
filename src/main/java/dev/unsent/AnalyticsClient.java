package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;

public class AnalyticsClient {
    private final UnsentClient client;
    
    public AnalyticsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse get() throws UnsentException {
        return client.get("/analytics");
    }
    
    public UnsentResponse getTimeSeries(String days, String domain) throws UnsentException {
        String query = "";
        if (days != null) query += "days=" + days + "&";
        if (domain != null) query += "domain=" + domain + "&";
        if (!query.isEmpty()) query = "?" + query.substring(0, query.length() - 1);
        
        return client.get("/analytics/time-series" + query);
    }

    public UnsentResponse getReputation(String domain) throws UnsentException {
        String query = "";
        if (domain != null) query += "domain=" + domain;
        if (!query.isEmpty()) query = "?" + query;

        return client.get("/analytics/reputation" + query);
    }
}
