package dev.unsent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import dev.unsent.UnsentClient.UnsentResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SystemTest {

    @Mock
    private UnsentClient client;

    private SystemClient system;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        system = new SystemClient(client);
    }

    @Test
    public void testHealthCheck() throws UnsentException {
        // Arrange
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get("/health")).thenReturn(mockResponse);

        // Act
        UnsentResponse response = system.health();

        // Assert
        assertNotNull(response);
        verify(client).get("/health");
    }

    @Test
    public void testVersion() throws UnsentException {
        // Arrange
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get("/version")).thenReturn(mockResponse);

        // Act
        UnsentResponse response = system.version();

        // Assert
        assertNotNull(response);
        verify(client).get("/version");
    }
}
