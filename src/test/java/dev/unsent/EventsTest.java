package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EventsTest {

    @Mock
    private UnsentClient client;

    private EventsClient events;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        events = new EventsClient(client);
    }

    @Test
    public void testListEvents() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        events.list();

        verify(client).get("/events");
    }

    @Test
    public void testListEventsWithParams() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        events.list(1, 10, "sent", "2023-01-01");

        verify(client).get("/events?page=1&limit=10&status=sent&startDate=2023-01-01");
    }
}
