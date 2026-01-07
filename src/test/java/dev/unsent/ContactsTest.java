package dev.unsent;

import dev.unsent.types.CreateContactRequest;
import dev.unsent.types.UpdateContactRequest;
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

public class ContactsTest {

    @Mock
    private UnsentClient client;

    private ContactsClient contacts;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        contacts = new ContactsClient(client);
    }

    @Test
    public void testListContacts() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        contacts.list("cb_123");

        verify(client).get("/contactBooks/cb_123/contacts");
    }

    @Test
    public void testListContactsWithParams() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        contacts.list("cb_123", 1, 10, "email@test.com", "c_123");

        verify(client).get("/contactBooks/cb_123/contacts?page=1&limit=10&emails=email@test.com&ids=c_123");
    }
    
    @Test
    public void testCreateContact() throws UnsentException {
        CreateContactRequest payload = new CreateContactRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        contacts.create("cb_123", payload);

        verify(client).post("/contactBooks/cb_123/contacts", payload);
    }

    @Test
    public void testGetContact() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        contacts.get("cb_123", "c_123");

        verify(client).get("/contactBooks/cb_123/contacts/c_123");
    }
    
    @Test
    public void testUpdateContact() throws UnsentException {
        UpdateContactRequest payload = new UpdateContactRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.patch(any(), any())).thenReturn(mockResponse);

        contacts.update("cb_123", "c_123", payload);

        verify(client).patch("/contactBooks/cb_123/contacts/c_123", payload);
    }

    @Test
    public void testUpsertContact() throws UnsentException {
        CreateContactRequest payload = new CreateContactRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.put(any(), any())).thenReturn(mockResponse);

        contacts.upsert("cb_123", "c_123", payload);

        verify(client).put("/contactBooks/cb_123/contacts/c_123", payload);
    }
    
    @Test
    public void testDeleteContact() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.delete(any())).thenReturn(mockResponse);

        contacts.delete("cb_123", "c_123");

        verify(client).delete("/contactBooks/cb_123/contacts/c_123");
    }
}
