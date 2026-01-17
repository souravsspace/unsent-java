package dev.unsent;

import dev.unsent.UnsentClient.UnsentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeamsTest {

    @Mock
    private UnsentClient client;

    private TeamsClient teams;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        teams = new TeamsClient(client);
    }

    @Test
    public void testListTeams() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        teams.list();

        verify(client).get("/teams");
    }

    @Test
    public void testGetTeam() throws UnsentException {
        UnsentResponse mockResponse = new UnsentResponse(null, null);
        when(client.get(any())).thenReturn(mockResponse);

        teams.get();

        verify(client).get("/team");
    }
}
