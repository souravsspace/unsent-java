package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StatsTest {

    @Mock
    private UnsentClient client;

    private StatsClient stats;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stats = new StatsClient(client);
    }

    @Test
    public void testGetStats() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        stats.get();

        verify(client).get("/stats");
    }

    @Test
    public void testGetStatsWithDateRange() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        stats.get("2023-01-01", "2023-01-31");

        verify(client).get("/stats?startDate=2023-01-01&endDate=2023-01-31");
    }
}
