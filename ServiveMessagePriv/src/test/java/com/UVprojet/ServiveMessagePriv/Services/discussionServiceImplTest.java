package com.UVprojet.ServiveMessagePriv.Services;

import com.UVprojet.ServiveMessagePriv.Dtos.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class discussionServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private discussionServiceImpl discussionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById() {
        String userId = UUID.randomUUID().toString();
        UserDTO mockUser = new UserDTO();
        mockUser.setId(UUID.fromString(userId));
        mockUser.setName("Test User");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password");
        mockUser.setRole("USER");

        when(restTemplate.getForObject("http://localhost:9000/api/auth/users/" + userId, UserDTO.class))
                .thenReturn(mockUser);

        UserDTO result = discussionService.getUserById(userId);

        assertNotNull(result);
        assertEquals(mockUser.getId(), result.getId());
        assertEquals(mockUser.getName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
        assertEquals(mockUser.getPassword(), result.getPassword());
        assertEquals(mockUser.getRole(), result.getRole());

        verify(restTemplate, times(1)).getForObject("http://localhost:9000/api/auth/users/" + userId, UserDTO.class);
    }
}
