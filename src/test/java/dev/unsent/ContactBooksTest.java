package dev.unsent;

import dev.unsent.types.Types.CreateContactBookRequest;
import dev.unsent.types.Types.UpdateContactBookRequest;
import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactBooksTest {

    @Mock
    private UnsentClient client;

    private ContactBooksClient contactBooks;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        contactBooks = new ContactBooksClient(client);
    }

    @Test
    public void testListContactBooks() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        contactBooks.list();

        verify(client).get("/contactBooks");
    }
    
    @Test
    public void testCreateContactBook() throws UnsentException {
        CreateContactBookRequest payload = new CreateContactBookRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.post(any(), any())).thenReturn(mockResponse);

        contactBooks.create(payload);

        verify(client).post("/contactBooks", payload);
    }
    
    @Test
    public void testGetContactBook() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        contactBooks.get("cb_123");

        verify(client).get("/contactBooks/cb_123");
    }
    
    @Test
    public void testUpdateContactBook() throws UnsentException {
        UpdateContactBookRequest payload = new UpdateContactBookRequest();
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.patch(any(), any())).thenReturn(mockResponse);

        contactBooks.update("cb_123", payload);

        verify(client).patch("/contactBooks/cb_123", payload);
    }
    
    @Test
    public void testDeleteContactBook() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.delete(any())).thenReturn(mockResponse);

        contactBooks.delete("cb_123");

        verify(client).delete("/contactBooks/cb_123");
    }
}
