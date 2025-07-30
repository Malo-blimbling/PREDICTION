package com.ProjetPrediction.controllers;

import com.ProjetPrediction.entities.users.dtos.AuthenticationDTO;
import com.ProjetPrediction.entities.users.dtos.LoginResponseDTO;
import com.ProjetPrediction.entities.users.dtos.RegisterDTO;
import com.ProjetPrediction.entities.users.User;
import com.ProjetPrediction.infra.security.TokenService;
import com.ProjetPrediction.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/auth", produces = {"application/json"})
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    /**
     * Authenticates user login.
     *
     * @param data Object containing user credentials
     * @return ResponseEntity containing authentication token
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody AuthenticationDTO data) {
        try {
            var credentials = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(credentials);
            var user = (User) auth.getPrincipal();
            var token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponseDTO(token, user.getId(), user.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Registers a new user.
     *
     * @param data Object containing user registration data
     * @return ResponseEntity indicating success or failure of registration
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.status(409).body(new HashMap<String, String>() {{ put("message", "Un utilisateur avec cet email existe déjà."); }});
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        // Par défaut, on attribue le rôle USER
        User user = new User(data.name(), data.email(), encryptedPassword, com.ProjetPrediction.entities.users.UserRole.USER);
        this.userRepository.save(user);
        return ResponseEntity.ok(new HashMap<String, String>() {{ put("message", "Inscription réussie."); }});
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing list of users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id UUID of the user
     * @return ResponseEntity containing user data or not found status
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
