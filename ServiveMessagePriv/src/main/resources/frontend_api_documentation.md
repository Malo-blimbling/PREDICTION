# Documentation API pour le Frontend - ServiveMessagePriv

Ce document décrit les endpoints REST et WebSocket que le frontend doit utiliser pour interagir avec le microservice ServiveMessagePriv.

---

## Endpoints REST

### Discussions

- **GET /api/discussions**

  Récupère la liste de toutes les discussions.

  **Réponse :**  
  Liste d'objets `discussionDTO` avec les champs :  
  - `id` (UUID)  
  - `expediteurId` (UUID)  
  - `sujet` (string)  
  - `dateCreation` (ISO8601 datetime)  
  - `statut` (string)

- **POST /api/discussions**

  Crée une nouvelle discussion.

  **Corps de la requête :**  
  Objet `discussionDTO` sans `id`.

  **Réponse :**  
  Objet `discussionDTO` créé avec un `id` généré.

---

### Messages privés

- **POST /api/messagepriv**

  Envoie un message privé dans une discussion.

  **Corps de la requête :**  
  Objet `messagePriveDTO` avec les champs :  
  - `discussionId` (UUID)  
  - `expediteurId` (UUID)  
  - `contenu` (string)  
  - `dateCreation` (ISO8601 datetime)

- **GET /api/messagepriv/discussion/{discussionId}**

  Récupère tous les messages d'une discussion.

- **DELETE /api/messagepriv/{messageId}?utilisateurId={utilisateurId}**

  Supprime un message si l'utilisateur est l'expéditeur.

---

## WebSocket

### Endpoints WebSocket

- **/app/chat.envoyer**

  Envoie un message dans une discussion.

  **Payload :**  
  Objet `MessageWebSocketDTO` avec les champs :  
  - `discussionId` (UUID)  
  - `expediteurId` (UUID)  
  - `expediteurNom` (string)  
  - `contenu` (string)

- **/app/chat.rejoindre**

  Permet à un utilisateur de rejoindre une discussion.

  **Payload :**  
  Objet `MessageWebSocketDTO` avec les champs :  
  - `discussionId` (UUID)  
  - `expediteurId` (UUID)

### Topics WebSocket

- **/topic/discussion/{discussionId}**

  Topic auquel le frontend doit s'abonner pour recevoir les messages en temps réel d'une discussion.

- **/topic/utilisateurs**

  Topic pour recevoir les notifications de connexion/déconnexion des utilisateurs.

---

## Notes

- Les dates doivent être au format ISO8601.
- Les UUID doivent être des chaînes valides.
- Le frontend doit gérer la connexion WebSocket et l'abonnement aux topics pour recevoir les messages en temps réel.

---

Pour toute question, contactez l'équipe de développement.
