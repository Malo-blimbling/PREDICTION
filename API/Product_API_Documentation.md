# Documentation du Service Produit via API Gateway

## Présentation Générale

Le service Produit est accessible via l'API Gateway, qui sert de point d'entrée unique pour les clients. Toutes les requêtes vers le service Produit doivent passer par l'API Gateway.

## Endpoints principaux (via API Gateway)

- `POST   /api/produits` : Créer un produit  
- `GET    /api/produits` : Lister tous les produits  
- `GET    /api/produits/{id}` : Récupérer un produit par son id  
- `PUT    /api/produits/{id}` : Modifier un produit  
- `DELETE /api/produits/{id}` : Supprimer un produit  
- `GET    /api/produits/search?nom=...` : Rechercher par nom  
- `GET    /api/produits/search?categorie=...` : Rechercher par catégorie  

## Fonctionnalités principales

- Gestion complète du catalogue produits (CRUD).  
- Recherche avancée par nom ou catégorie.  
- Validation métier stricte (ex: prix non négatif).  
- Gestion des erreurs HTTP claires (400, 404).  
- Documentation interactive disponible via Swagger UI sur le service Produit.

## Exemples de requêtes JSON

### Créer un produit (POST /api/produits)

```json
{
  "nom": "Chaise ergonomique",
  "description": "Chaise confortable pour bureau",
  "prix": 129.99,
  "categorie": "Mobilier"
}
```

### Modifier un produit (PUT /api/produits/{id})

```json
{
  "nom": "Chaise ergonomique Deluxe",
  "description": "Chaise confortable avec support lombaire",
  "prix": 159.99,
  "categorie": "Mobilier"
}
```

### Rechercher par nom (GET /api/produits/search?nom=chaise)

Aucun corps JSON requis, paramètre de requête `nom` utilisé.

### Rechercher par catégorie (GET /api/produits/search?categorie=Mobilier)

Aucun corps JSON requis, paramètre de requête `categorie` utilisé.

## Gestion des erreurs

- Prix négatif :  
  - Code HTTP : 400 BAD REQUEST  
  - Message : "Le prix ne peut pas être négatif."

- Produit non trouvé :  
  - Code HTTP : 404 NOT FOUND  
  - Message : "Produit non trouvé."

## Configuration

- Base de données PostgreSQL configurée dans le service Produit.  
- L'API Gateway redirige les requêtes vers le service Produit sur le port et chemin configurés.

## Bonnes pratiques

- Utilisation de Lombok, MapStruct pour simplifier le code.  
- Architecture en couches claire et maintenable.  
- Tests unitaires et d'intégration pour assurer la qualité.

## Documentation interactive

- Accès à Swagger UI : [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)  
- Tous les endpoints, modèles et exemples de requêtes y sont visibles et testables.

---

Cette documentation permet d'orienter efficacement un développeur frontend pour consommer le service Produit via l'API Gateway.
