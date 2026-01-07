package dev.unsent;

import dev.unsent.types.CreateTemplateRequest;
import dev.unsent.types.UpdateTemplateRequest;
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

public class TemplatesTest {

    @Mock
    private UnsentClient client;

    private TemplatesClient templates;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        templates = new TemplatesClient(client);
    }

    @Test
    public void testListTemplates() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        templates.list();

        verify(client).get("/templates");
    }
    
    @Test
    public void testCreateTemplate() throws UnsentException {
        CreateTemplateRequest payload = new CreateTemplateRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        templates.create(payload);

        verify(client).post("/templates", payload);
    }
    
    @Test
    public void testGetTemplate() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        templates.get("tmp_123");

        verify(client).get("/templates/tmp_123");
    }
    
    @Test
    public void testUpdateTemplate() throws UnsentException {
        UpdateTemplateRequest payload = new UpdateTemplateRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.patch(any(), any())).thenReturn(mockResponse);

        templates.update("tmp_123", payload);

        verify(client).patch("/templates/tmp_123", payload);
    }
    
    @Test
    public void testDeleteTemplate() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.delete(any())).thenReturn(mockResponse);

        templates.delete("tmp_123");

        verify(client).delete("/templates/tmp_123");
    }
}
