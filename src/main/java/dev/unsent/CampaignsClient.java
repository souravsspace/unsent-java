package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.CreateCampaignRequest;
import dev.unsent.types.ScheduleCampaignRequest;

public class CampaignsClient {
    private final UnsentClient client;
    
    public CampaignsClient(UnsentClient client) {
        this.client = client;
    }
    
    public UnsentResponse list() throws UnsentException {
        return client.get("/campaigns");
    }
    
    public UnsentResponse get(String campaignId) throws UnsentException {
        return client.get("/campaigns/" + campaignId);
    }
    
    public UnsentResponse create(Object payload) throws UnsentException {
        return client.post("/campaigns", payload);
    }
    
    public UnsentResponse create(CreateCampaignRequest payload) throws UnsentException {
        return client.post("/campaigns", payload);
    }
    
    public UnsentResponse schedule(String campaignId, Object payload) throws UnsentException {
        return client.post("/campaigns/" + campaignId + "/schedule", payload);
    }

    public UnsentResponse schedule(String campaignId, ScheduleCampaignRequest payload) throws UnsentException {
        return client.post("/campaigns/" + campaignId + "/schedule", payload);
    }

    public UnsentResponse pause(String campaignId) throws UnsentException {
        return client.post("/campaigns/" + campaignId + "/pause", new Object());
    }

    public UnsentResponse resume(String campaignId) throws UnsentException {
        return client.post("/campaigns/" + campaignId + "/resume", new Object());
    }
}
