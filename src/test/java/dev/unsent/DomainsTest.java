package dev.unsent;

import dev.unsent.types.CreateDomainRequest;
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

public class DomainsTest {

    @Mock
    private UnsentClient client;

    private DomainsClient domains;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        domains = new DomainsClient(client);
    }

    @Test
    public void testListDomains() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        domains.list();

        verify(client).get("/domains");
    }

    @Test
    public void testCreateDomain() throws UnsentException {
        CreateDomainRequest payload = new CreateDomainRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        domains.create(payload);

        verify(client).post("/domains", payload);
    }

    @Test
    public void testGetDomain() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        domains.get("d_123");

        verify(client).get("/domains/d_123");
    }

    @Test
    public void testVerifyDomain() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.put(any(), any())).thenReturn(mockResponse);

        domains.verify("d_123");

        verify(client).put(eq("/domains/d_123/verify"), any());
    }

    @Test
    public void testDeleteDomain() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.delete(any())).thenReturn(mockResponse);

        domains.delete("d_123");

        verify(client).delete("/domains/d_123");
    }
}
