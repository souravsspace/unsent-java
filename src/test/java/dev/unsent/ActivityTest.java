package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ActivityTest {

    @Mock
    private UnsentClient client;

    private ActivityClient activity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        activity = new ActivityClient(client);
    }

    @Test
    public void testGetActivity() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        activity.get();

        verify(client).get("/activity");
    }

    @Test
    public void testGetActivityWithPagination() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        activity.get(2, 50);

        verify(client).get("/activity?page=2&limit=50");
    }
}
