package dev.unsent;

import dev.unsent.types.SendEmailRequest;
import dev.unsent.types.CreateContactRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnsentTest {

    @Test
    public void testClientInitialization() {
        UnsentClient client = new UnsentClient("test_key");
        assertNotNull(client.emails);
        assertNotNull(client.contacts);
        assertNotNull(client.campaigns);
        assertNotNull(client.domains);
        assertNotNull(client.templates);
        assertNotNull(client.contactBooks);
        assertNotNull(client.suppressions);
        assertNotNull(client.apiKeys);
        assertNotNull(client.analytics);
        assertNotNull(client.settings);
        assertNotNull(client.webhooks);
    }

    @Test
    public void testSendEmailRequest() {
        SendEmailRequest request = new SendEmailRequest();
        request.setFrom("test@example.com");
        request.setSubject("Hello");
        request.setText("World");
        
        assertEquals("test@example.com", request.getFrom());
        assertEquals("Hello", request.getSubject());
        assertEquals("World", request.getText());
    }

    @Test
    public void testCreateContactRequest() {
        CreateContactRequest request = new CreateContactRequest();
        request.setEmail("user@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        
        assertEquals("user@example.com", request.getEmail());
        assertEquals("John", request.getFirstName());
        assertEquals("Doe", request.getLastName());
    }
}
