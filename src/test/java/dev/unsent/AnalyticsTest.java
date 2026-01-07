package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnalyticsTest {

    @Mock
    private UnsentClient client;

    private AnalyticsClient analytics;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        analytics = new AnalyticsClient(client);
    }

    @Test
    public void testGetAnalytics() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        analytics.get();

        verify(client).get("/analytics");
    }

    @Test
    public void testGetTimeSeries() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        analytics.getTimeSeries("7d", "d_123");

        verify(client).get("/analytics/time-series?days=7d&domain=d_123");
    }

    @Test
    public void testGetReputation() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        analytics.getReputation("d_123");

        verify(client).get("/analytics/reputation?domain=d_123");
    }
}
