package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MetricsTest {

    @Mock
    private UnsentClient client;

    private MetricsClient metrics;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        metrics = new MetricsClient(client);
    }

    @Test
    public void testGetMetrics() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        metrics.get();

        verify(client).get("/metrics");
    }

    @Test
    public void testGetMetricsWithPeriod() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        metrics.get("week");

        verify(client).get("/metrics?period=week");
    }
}
