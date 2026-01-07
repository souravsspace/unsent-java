package dev.unsent;

import dev.unsent.types.AddSuppressionRequest;
import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SuppressionsTest {

    @Mock
    private UnsentClient client;

    private SuppressionsClient suppressions;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        suppressions = new SuppressionsClient(client);
    }

    @Test
    public void testListSuppressions() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        suppressions.list();

        verify(client).get("/suppressions");
    }
    
    @Test
    public void testListSuppressionsWithParams() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        suppressions.list(1, 10, null, null);

        verify(client).get("/suppressions?page=1&limit=10");
    }

    @Test
    public void testAddSuppression() throws UnsentException {
        AddSuppressionRequest payload = new AddSuppressionRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        suppressions.add(payload);

        verify(client).post("/suppressions", payload);
    }

    @Test
    public void testDeleteSuppression() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.delete(any())).thenReturn(mockResponse);

        suppressions.delete("email@example.com");

        verify(client).delete("/suppressions/email/email@example.com");
    }
}
