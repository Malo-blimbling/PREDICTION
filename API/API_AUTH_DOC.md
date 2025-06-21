# Documentation API Gateway – Authentification et Sécurité (Mise à jour)

## URL de base

```
http://localhost:9000
```

---

## Endpoints d’authentification

- **Connexion (login)** :
  - `POST /auth/login`
- **Inscription (register)** :
  - `POST /auth/register`

---

## Format des requêtes

### Login
```json
{
  "email": "user@email.com",
  "password": "motdepasse"
}
```

### Register
```json
{
  "name": "Nom",
  "email": "user@email.com",
  "password": "motdepasse",
  "role": "USER"
}
```

---

## Format des réponses

### Succès (login)
```json
{
  "token": "JWT_TOKEN",
  "user": { ... }
}
```

### Succès (register)
```json
{
  "message": "Inscription réussie."
}
```

### Erreur
```json
{
  "error": "message d’erreur"
}
```

---

## Headers spécifiques

- `Content-Type: application/json` (pour POST/PUT)
- `Authorization: Bearer VOTRE_JWT` (pour les routes protégées)

---

## Fonctionnalités protégées

- Création, modification, suppression de publications (`/api/private/**`, `/api/user/**`)
- Accès/modification du profil utilisateur
- Toute opération nécessitant l’identification de l’utilisateur

---

## Exemple de requête login

```
POST http://localhost:9000/auth/login
Content-Type: application/json

{
  "email": "user@email.com",
  "password": "motdepasse"
}
```

### Réponse
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@email.com"
  }
}
```

---

## Remarques

- Les routes publiques (ex : GET /api/publications) sont accessibles sans authentification.
- Les routes protégées nécessitent un token JWT dans le header `Authorization`.
- Un visiteur non authentifié ne peut pas créer de publication.
- CORS est activé pour permettre les requêtes du frontend (ex : http://localhost:3000).
