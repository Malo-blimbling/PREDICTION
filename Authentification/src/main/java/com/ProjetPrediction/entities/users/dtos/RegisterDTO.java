package com.ProjetPrediction.entities.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
    @NotBlank(message = "Le nom est obligatoire.")
    String name,
    @NotBlank(message = "L'email est obligatoire.")
    @Email(message = "Format d'email invalide.")
    String email,
    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    @Pattern(
        regexp = ".*[A-Z].*",
        message = "Le mot de passe doit contenir au moins une majuscule."
    )
    @Pattern(
        regexp = ".*[0-9].*",
        message = "Le mot de passe doit contenir au moins un chiffre."
    )
    String password
) {}
