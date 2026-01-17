package dev.unsent;

import dev.unsent.types.Types.SendEmailRequest;
import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmailsTest {

    @Mock
    private UnsentClient client;

    private EmailsClient emails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        emails = new EmailsClient(client);
    }

    @Test
    public void testSendEmail() throws UnsentException {
        SendEmailRequest payload = new SendEmailRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(eq("/emails"), any(SendEmailRequest.class), any())).thenReturn(mockResponse);

        UnsentResponse response = emails.send(payload);

        assertNotNull(response);
        verify(client).post(eq("/emails"), eq(payload), any());
    }

    @Test
    public void testSendEmailWithOptions() throws UnsentException {
        SendEmailRequest payload = new SendEmailRequest();
        Map<String, String> options = new HashMap<>();
        options.put("idempotencyKey", "123");
        
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(eq("/emails"), any(SendEmailRequest.class), any())).thenReturn(mockResponse);

        UnsentResponse response = emails.send(payload, options);

        assertNotNull(response);
        verify(client).post(eq("/emails"), eq(payload), any());
    }

    @Test
    public void testListEmails() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        UnsentResponse response = emails.list();

        assertNotNull(response);
        verify(client).get("/emails");
    }
    
    @Test
    public void testListEmailsWithParams() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        emails.list(1, 10, "2023-01-01", "2023-01-31", "d_123");

        verify(client).get("/emails?page=1&limit=10&startDate=2023-01-01&endDate=2023-01-31&domainId=d_123");
    }

    @Test
    public void testGetEmail() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        UnsentResponse response = emails.get("e_123");

        assertNotNull(response);
        verify(client).get("/emails/e_123");
    }
    
    @Test
    public void testUpdateEmail() throws UnsentException {
         UnsentResponse mockResponse = new UnsentResponse(null, null);
         when(client.patch(any(), any())).thenReturn(mockResponse);
         
         Object payload = new Object();
         emails.update("e_123", payload);
         
         verify(client).patch("/emails/e_123", payload);
    }
    
    @Test
    public void testCancelEmail() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);
        
        emails.cancel("e_123");
        
        verify(client).post(eq("/emails/e_123/cancel"), any());
    }

    @Test
    public void testGetEvents() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        emails.getEvents("e_123");

        verify(client).get("/emails/e_123/events");
    }
    
    @Test
    public void testGetBounces() throws UnsentException {
         UnsentResponse mockResponse = new UnsentResponse(null, null);
         when(client.get(any())).thenReturn(mockResponse);
         
         emails.getBounces(1, 10);
         
         verify(client).get("/emails/bounces?page=1&limit=10");
    }

    @Test
    public void testGetComplaints() throws UnsentException {
         UnsentResponse mockResponse = new UnsentResponse(null, null);
         when(client.get(any())).thenReturn(mockResponse);
         
         emails.getComplaints(1, 10);
         
         verify(client).get("/emails/complaints?page=1&limit=10");
    }

    @Test
    public void testGetUnsubscribes() throws UnsentException {
         UnsentResponse mockResponse = new UnsentResponse(null, null);
         when(client.get(any())).thenReturn(mockResponse);
         
         emails.getUnsubscribes(1, 10);
         
         verify(client).get("/emails/unsubscribes?page=1&limit=10");
    }

    @Test
    public void testBatchSend() throws UnsentException {
         List<SendEmailRequest> payload = new ArrayList<>();
         payload.add(new SendEmailRequest());
         payload.add(new SendEmailRequest());
         
         UnsentResponse mockResponse = new UnsentResponse(null, null);
         when(client.post(eq("/emails/batch"), any(), any())).thenReturn(mockResponse);
         
         emails.batch(payload);
         
         verify(client).post(eq("/emails/batch"), any(), any());
    }
}
