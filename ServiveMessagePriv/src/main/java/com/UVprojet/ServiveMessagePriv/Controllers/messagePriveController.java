package com.UVprojet.ServiveMessagePriv.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.UVprojet.ServiveMessagePriv.Dtos.messagePriveDTO;
import com.UVprojet.ServiveMessagePriv.Services.messagePriveServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messagepriv")
public class messagePriveController {

    @Autowired
    private messagePriveServiceImpl messagePriveService;

    @PostMapping
    public messagePriveDTO envoyerMessage(@RequestBody messagePriveDTO messagePriveDTO) {
        return messagePriveService.envoyerMessage(messagePriveDTO);
    }

    @GetMapping("/discussion/{discussionId}")
    public List<messagePriveDTO> obtenirMessagesParDiscussion(@PathVariable UUID discussionId) {
        return messagePriveService.obtenirMessagesParDiscussion(discussionId);
    }

    @DeleteMapping("/{messageId}")
    public void supprimerMessage(@PathVariable UUID messageId, @RequestParam UUID utilisateurId) {
        messagePriveService.supprimerMessage(messageId, utilisateurId);
    }
}
