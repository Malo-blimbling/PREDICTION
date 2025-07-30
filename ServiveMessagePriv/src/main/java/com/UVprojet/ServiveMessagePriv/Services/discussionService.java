package com.UVprojet.ServiveMessagePriv.Services;

import com.UVprojet.ServiveMessagePriv.Dtos.discussionDTO;
import com.UVprojet.ServiveMessagePriv.Dtos.UserDTO;
import java.util.List;

public interface discussionService {
    discussionDTO creerDiscussion(discussionDTO discussionDTO);
    List<discussionDTO> obtenirDiscussions();
    UserDTO getUserById(String id);
}
