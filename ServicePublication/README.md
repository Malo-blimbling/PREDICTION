# Documentation rapide des endpoints du service SERVICEPUBLICATION

## Ports et chemins
- **API Gateway** : http://localhost:9000
- **Tous les endpoints sont accessibles via la Gateway** :
  - `/api/publications` (POST, GET)
  - `/api/publications/{id}` (GET, DELETE)
  - `/api/publications/utilisateur/{utilisateurId}` (GET)

## Endpoints à appeler

| Méthode | Endpoint                                         | Description                                      |
|---------|--------------------------------------------------|--------------------------------------------------|
| POST    | `/api/publications`                              | Créer une publication                            |
| GET     | `/api/publications`                              | Lister toutes les publications                   |
| GET     | `/api/publications/{id}`                         | Récupérer une publication par son ID             |
| GET     | `/api/publications/utilisateur/{utilisateurId}`  | Lister les publications d’un utilisateur         |
| DELETE  | `/api/publications/{id}`                         | Supprimer une publication par son ID             |

## Formats de réponse

### Succès (200)
- **GET /api/publications**
```json
[
  {
    "id": 1,
    "utilisateurId": "...",
    "produitId": "...",
    "description": "...",
    "prix": 99.99,
    "datePublication": "2025-06-05T12:34:56"
  },
  ...
]
```

- **GET /api/publications/{id}**
```json
{
  "id": 1,
  "utilisateurId": "...",
  "produitId": "...",
  "description": "...",
  "prix": 99.99,
  "datePublication": "2025-06-05T12:34:56"
}
```

- **POST /api/publications** (création)
```json
{
  "id": 1,
  "utilisateurId": "...",
  "produitId": "...",
  "description": "...",
  "prix": 99.99,
  "datePublication": "2025-06-05T12:34:56"
}
```

- **DELETE /api/publications/{id}**
Réponse : 200 OK (pas de contenu)

### Erreur (404)
- **GET ou DELETE avec un id inexistant**
```json
{
  "error": "Publication non trouvée"
}
```

### Erreur (400 ou 500)
- **POST avec données invalides ou problème de communication**
```json
{
  "error": "Erreur lors de la création de la publication : ..."
}
```

---
**Remarque** :
- Tous les appels entre microservices doivent passer par l’API Gateway (http://localhost:9000), jamais en direct.
- Les réponses d’erreur peuvent varier selon la logique métier et la gestion d’exception du service.
