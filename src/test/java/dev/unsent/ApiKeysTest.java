package dev.unsent;

import dev.unsent.types.Types.CreateApiKeyRequest;
import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApiKeysTest {

    @Mock
    private UnsentClient client;

    private ApiKeysClient apiKeys;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        apiKeys = new ApiKeysClient(client);
    }

    @Test
    public void testListApiKeys() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        apiKeys.list();

        verify(client).get("/api-keys");
    }

    @Test
    public void testCreateApiKey() throws UnsentException {
        CreateApiKeyRequest payload = new CreateApiKeyRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        apiKeys.create(payload);

        verify(client).post("/api-keys", payload);
    }

    @Test
    public void testDeleteApiKey() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.delete(any())).thenReturn(mockResponse);

        apiKeys.delete("key_123");

        verify(client).delete("/api-keys/key_123");
    }
}
