package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SettingsTest {

    @Mock
    private UnsentClient client;

    private SettingsClient settings;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        settings = new SettingsClient(client);
    }

    @Test
    public void testGetSettings() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        settings.get();

        verify(client).get("/settings");
    }
}
