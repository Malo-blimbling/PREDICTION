# Spring Boot User Authentication Service

Ce microservice gère l'inscription, la connexion et la gestion des utilisateurs avec Spring Security, JWT et PostgreSQL.

## Endpoints principaux

### POST /auth/register
- **Description** : Inscription d'un nouvel utilisateur.
- **Corps attendu** (JSON) :
  ```json
  {
    "name": "Nom complet",
    "email": "email@exemple.com",
    "password": "motdepasse",
    "role": "USER" // ou "ADMIN"
  }
  ```
- **Réponses** :
  - `200 OK` : Inscription réussie (message dans le body).
  - `409 CONFLICT` : Un utilisateur avec cet email existe déjà (message dans le body).

### POST /auth/login
- **Description** : Authentification d'un utilisateur.
- **Corps attendu** (JSON) :
  ```json
  {
    "email": "email@exemple.com",
    "password": "motdepasse"
  }
  ```
- **Réponses** :
  - `200 OK` :
    ```json
    { "token": "<JWT>" }
    ```
  - `401 UNAUTHORIZED` : Identifiants invalides ou erreur d'authentification.

## Modèle Utilisateur
- `id` : UUID (généré automatiquement)
- `name` : String
- `email` : String (unique)
- `password` : String (hashé)
- `role` : Enum (USER, ADMIN)

## Sécurité
- Les mots de passe sont hashés avec BCrypt.
- Les tokens JWT sont utilisés pour l'authentification.
- Les endpoints `/auth/register` et `/auth/login` sont publics.
- Les autres endpoints sont protégés (à adapter selon besoin).

## Gestion des erreurs
- 409 CONFLICT si email déjà utilisé à l'inscription (message explicite dans le body).
- 401 UNAUTHORIZED si échec de connexion.

## Exemple d'utilisation

### Inscription
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@example.com","password":"1234","role":"USER"}'
```

### Connexion
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"1234"}'
```

## Dépendances principales
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (Auth0 Java JWT)
- Lombok

## Configuration
Voir le fichier `application.properties` pour la configuration de la base de données et du secret JWT.

---

**Auteur :** ProjetPrediction
**Date :** 2025-05-24
