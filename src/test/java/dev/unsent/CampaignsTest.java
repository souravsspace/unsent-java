package dev.unsent;

import dev.unsent.types.Types.CreateCampaignRequest;
import dev.unsent.types.Types.ScheduleCampaignRequest;
import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CampaignsTest {

    @Mock
    private UnsentClient client;

    private CampaignsClient campaigns;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        campaigns = new CampaignsClient(client);
    }

    @Test
    public void testListCampaigns() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        campaigns.list();

        verify(client).get("/campaigns");
    }
    
    @Test
    public void testCreateCampaign() throws UnsentException {
        CreateCampaignRequest payload = new CreateCampaignRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        campaigns.create(payload);

        verify(client).post("/campaigns", payload);
    }
    
    @Test
    public void testGetCampaign() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        campaigns.get("cmp_123");

        verify(client).get("/campaigns/cmp_123");
    }
    
    @Test
    public void testScheduleCampaign() throws UnsentException {
        ScheduleCampaignRequest payload = new ScheduleCampaignRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        campaigns.schedule("cmp_123", payload);

        verify(client).post("/campaigns/cmp_123/schedule", payload);
    }
    
    @Test
    public void testPauseCampaign() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        campaigns.pause("cmp_123");

        verify(client).post(eq("/campaigns/cmp_123/pause"), any());
    }
    
    @Test
    public void testResumeCampaign() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        campaigns.resume("cmp_123");

        verify(client).post(eq("/campaigns/cmp_123/resume"), any());
    }
}
