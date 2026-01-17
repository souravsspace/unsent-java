package dev.unsent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import dev.unsent.UnsentClient.UnsentResponse;
import dev.unsent.types.Types.CreateWebhookRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebhooksTest {

    @Mock
    private UnsentClient client;

    private WebhooksClient webhooks;

    // NOTE: Webhooks are a feature of future and not implemented yet
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webhooks = new WebhooksClient(client);
    }

    @Test
    public void testCreateWebhook() throws UnsentException {
        // Arrange
        String url = "https://example.com/webhook";
        List<String> events = List.of("email.sent");
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        
        when(client.post(eq("/webhooks"), any(CreateWebhookRequest.class))).thenReturn(mockResponse);

        // Act
        UnsentResponse response = webhooks.create(url, events);

        // Assert
        assertNotNull(response);
        verify(client).post(eq("/webhooks"), any(CreateWebhookRequest.class));
    }
    
    @Test 
    public void testListWebhooks() throws UnsentException {
        // Arrange
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get("/webhooks")).thenReturn(mockResponse);

        // Act
        UnsentResponse response = webhooks.list();

        // Assert
        assertNotNull(response);
        verify(client).get("/webhooks");
    }

    @Test
    public void testGetWebhook() throws UnsentException {
        // Arrange
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        // Act
        webhooks.get("wh_123");

        // Assert
        verify(client).get("/webhooks/wh_123");
    }

    @Test
    public void testTestWebhook() throws UnsentException {
        // Arrange
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        // Act
        webhooks.test("wh_123");

        // Assert
        verify(client).post(eq("/webhooks/wh_123/test"), any());
    }
}
