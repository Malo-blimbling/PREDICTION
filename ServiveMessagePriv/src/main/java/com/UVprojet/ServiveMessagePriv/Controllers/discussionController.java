package com.UVprojet.ServiveMessagePriv.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.UVprojet.ServiveMessagePriv.Dtos.discussionDTO;
import com.UVprojet.ServiveMessagePriv.Services.discussionServiceImpl;


import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/discussions")
public class discussionController {

    @Autowired
    private discussionServiceImpl discussionService;

    @PostMapping
    public discussionDTO creerDiscussion(@RequestBody discussionDTO discussionDTO) {
        return discussionService.creerDiscussion(discussionDTO);
    }

    @GetMapping
    public List<discussionDTO> obtenirDiscussions() {
        return discussionService.obtenirDiscussions();
    }

    // Endpoint pour filtrer par utilisateur (expediteur ou destinataire)
    @GetMapping("/utilisateur/{utilisateurId}")
    public List<discussionDTO> obtenirDiscussionsPourUtilisateur(@PathVariable UUID utilisateurId) {
        return discussionService.obtenirDiscussionsPourUtilisateur(utilisateurId);
    }
}
