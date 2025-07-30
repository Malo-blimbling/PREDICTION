# Documentation – API Messagerie Privée

## 📦 API REST

### Envoyer un message
- **POST** `/api/messagepriv`
- **Body** :
  ```json
  {
    "discussionId": "UUID",
    "expediteurId": "UUID",
    "contenu": "string"
  }
  ```
- **Réponse** : messagePriveDTO (le message créé)

---

### Récupérer l’historique d’une discussion
- **GET** `/api/messagepriv/discussion/{discussionId}`
- **Réponse** :
  ```json
  [
    {
      "id": "UUID",
      "discussionId": "UUID",
      "expediteurId": "UUID",
      "destinataireId": "UUID",
      "contenu": "string",
      "dateCreation": "2025-07-22T12:34:56"
    },
    ...
  ]
  ```

---

### Supprimer un message
- **DELETE** `/api/messagepriv/{messageId}?utilisateurId={utilisateurId}`
- **Réponse** : (vide, 200 OK si succès)

---

## 🔔 WebSocket (temps réel)

### Abonnement pour recevoir les nouveaux messages
- **Abonnement** : `/user/queue/messages/{userId}`
- **Payload reçu** :
  ```json
  {
    "id": "UUID",
    "discussionId": "UUID",
    "expediteurId": "UUID",
    "destinataireId": "UUID",
    "contenu": "string",
    "dateCreation": "2025-07-22T12:34:56",
    "type": "NOUVEAU_MESSAGE"
  }
  ```

### Envoi d’un message via WebSocket (optionnel)
- **Envoi** : `/app/chat.envoyer`
- **Payload** : MessageWebSocketDTO

---

## Résumé pour le frontend

- Pour afficher l’historique :
  → Appeler `GET /api/messagepriv/discussion/{discussionId}` au chargement de la discussion.
- Pour recevoir les nouveaux messages en temps réel :
  → S’abonner à `/user/queue/messages/{userId}` (userId = UUID de l’utilisateur connecté).
- Pour envoyer un message :
  → Utiliser `POST /api/messagepriv` (ou `/app/chat.envoyer` en WebSocket si implémenté).
