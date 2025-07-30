package com.UVprojet.ServiveMessagePriv.Services;

import com.UVprojet.ServiveMessagePriv.Dtos.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class discussionServiceImplIntegrationTest {

    @Autowired
    private discussionService discussionService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGetUserById_Success() {
        // Remplacez par un ID utilisateur valide existant dans le microservice utilisateur
        String userId = "00000000-0000-0000-0000-000000000000";

        UserDTO user = discussionService.getUserById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId().toString());
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
    }

    @Test
    public void testGetUserById_NotFound() {
        String invalidUserId = "11111111-1111-1111-1111-111111111111";

        try {
            discussionService.getUserById(invalidUserId);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // Vérifier que l'exception est bien liée à un utilisateur non trouvé ou erreur HTTP
            assertTrue(e.getMessage().contains("404") || e.getMessage().contains("Not Found"));
        }
    }
}
