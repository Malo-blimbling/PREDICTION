package com.ProjetPrediction.entities.users.dtos;

import java.util.UUID;

public record LoginResponseDTO(String token, UUID userId, String email) {
}
