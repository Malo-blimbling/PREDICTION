package com.ProjetPrediction.entities.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
    @NotBlank(message = "L'email est obligatoire.")
    @Email(message = "Format d'email invalide.")
    String email,
    @NotBlank(message = "Le mot de passe est obligatoire.")
    String password
) {}
