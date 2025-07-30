# Documentation du Microservice ServiveMessagePriv

## Description générale

Ce microservice gère les discussions privées et les messages échangés entre utilisateurs via une API REST et un système de WebSocket pour la communication en temps réel.

---

## Endpoints REST

### 1. Discussions

- **GET /api/discussions**
  - **Description** : Récupère la liste de toutes les discussions de l’utilisateur connecté.
  - **Réponse** :  
    ```json
    [
      {
        "id": "UUID",
        "expediteurId": "UUID",
        "sujet": "string",
        "dateCreation": "2025-07-04T12:00:00",
        "statut": "string"
      }
    ]
    ```

- **POST /api/discussions**
  - **Description** : Crée une nouvelle discussion.
  - **Corps de la requête** :
    ```json
    {
      "expediteurId": "UUID",
      "sujet": "string"
    }
    ```
  - **Réponse** :
    ```json
    {
      "id": "UUID",
      "expediteurId": "UUID",
      "sujet": "string",
      "dateCreation": "2025-07-04T12:00:00",
      "statut": "string"
    }
    ```

### 2. Messages privés

- **GET /api/messages/discussion/{discussionId}**
  - **Description** : Récupère tous les messages d’une discussion.
  - **Réponse** :
    ```json
    [
      {
        "id": "UUID",
        "discussionId": "UUID",
        "expediteurId": "UUID",
        "contenu": "string",
        "dateCreation": "2025-07-04T12:00:00"
      }
    ]
    ```

- **POST /api/messages**
  - **Description** : Envoie un message privé dans une discussion.
  - **Corps de la requête** :
    ```json
    {
      "discussionId": "UUID",
      "expediteurId": "UUID",
      "contenu": "string"
    }
    ```
  - **Réponse** :
    ```json
    {
      "id": "UUID",
      "discussionId": "UUID",
      "expediteurId": "UUID",
      "contenu": "string",
      "dateCreation": "2025-07-04T12:00:00"
    }
    ```

- **DELETE /api/messages/{messageId}?utilisateurId={utilisateurId}**
  - **Description** : Supprime un message si l’utilisateur est l’expéditeur.
  - **Réponse** : 204 No Content

---

## WebSocket

### 1. Endpoints WebSocket

- **/app/chat.envoyer**
  - **Description** : Envoie un message dans une discussion.
  - **Payload** :
    ```json
    {
      "discussionId": "UUID",
      "expediteurId": "UUID",
      "expediteurNom": "string",
      "contenu": "string"
    }
    ```

- **/app/chat.rejoindre**
  - **Description** : Permet à un utilisateur de rejoindre une discussion.
  - **Payload** :
    ```json
    {
      "discussionId": "UUID",
      "expediteurId": "UUID"
    }
    ```

### 2. Topics WebSocket

- **/topic/discussion/{discussionId}**
  - **Description** : Topic auquel s’abonner pour recevoir les messages en temps réel d’une discussion.
  - **Message reçu** :
    ```json
    {
      "id": "UUID",
      "discussionId": "UUID",
      "expediteurId": "UUID",
      "expediteurNom": "string",
      "contenu": "string",
      "dateCreation": "2025-07-04T12:00:00",
      "type": "NOUVEAU_MESSAGE" // ou MESSAGE_SUPPRIME, DISCUSSION_MISE_A_JOUR
    }
    ```

- **/topic/utilisateurs**
  - **Description** : Topic pour recevoir les notifications de connexion/déconnexion des utilisateurs.
  - **Message reçu** :
    ```json
    {
      "expediteurId": "UUID",
      "expediteurNom": "string",
      "type": "UTILISATEUR_CONNECTE" // ou UTILISATEUR_DECONNECTE
    }
    ```

---

## Structures des DTOs

### discussionDTO

```json
{
  "id": "UUID",
  "expediteurId": "UUID",
  "sujet": "string",
  "dateCreation": "2025-07-04T12:00:00",
  "statut": "string"
}
```

### messagePriveDTO

```json
{
  "id": "UUID",
  "discussionId": "UUID",
  "expediteurId": "UUID",
  "contenu": "string",
  "dateCreation": "2025-07-04T12:00:00"
}
```

### MessageWebSocketDTO

```json
{
  "id": "UUID",
  "discussionId": "UUID",
  "expediteurId": "UUID",
  "expediteurNom": "string",
  "contenu": "string",
  "dateCreation": "2025-07-04T12:00:00",
  "type": "NOUVEAU_MESSAGE" // ou MESSAGE_SUPPRIME, UTILISATEUR_CONNECTE, UTILISATEUR_DECONNECTE, DISCUSSION_MISE_A_JOUR
}
```

---

## Fonctionnalités attendues côté frontend

- Affichage de la liste des discussions de l’utilisateur.
- Création d’une nouvelle discussion.
- Affichage des messages d’une discussion.
- Envoi de messages dans une discussion (REST ou WebSocket).
- Suppression de ses propres messages.
- Réception en temps réel des nouveaux messages, suppressions, et mises à jour via WebSocket.
- Affichage des notifications de connexion/déconnexion des utilisateurs.

---

## Technologies utilisées

- Spring Boot
- Spring WebSocket (STOMP)
- MapStruct
- JPA/Hibernate

---

## Contact

Pour toute question ou problème, contacter l’équipe de développement.
