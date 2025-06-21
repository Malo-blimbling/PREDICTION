# Documentation du Service Produit

## Présentation Générale

Ce service est une API REST de gestion de produits développée avec Spring Boot, Spring Data JPA, Lombok, MapStruct et PostgreSQL. Il permet de gérer un catalogue de produits de façon robuste, sécurisée et documentée.

## Fonctionnalités principales
- **CRUD Produit** : Création, lecture, modification et suppression de produits.
- **Recherche** : Recherche de produits par nom ou par catégorie.
- **Validation métier** :
  - Le prix d’un produit ne peut pas être négatif.
  - Gestion des erreurs pour les opérations sur des produits inexistants.
- **Gestion des exceptions** : Retour d’erreurs HTTP claires (400, 404) en cas de problème métier ou technique.
- **Documentation interactive** :
  - Swagger UI (Springdoc OpenAPI) accessible sur `/swagger-ui.html` ou `/swagger-ui/index.html`.
- **Architecture en couches** :
  - Contrôleur (REST)
  - Service (logique métier)
  - Repository (accès base de données)
  - DTO (transfert de données)
  - Mapper (conversion entité/DTO)
  - Entité JPA (modèle de données)

## Structure du projet

- `controller/ProductController.java` : Gère les endpoints REST.
- `service/ProductService.java` : Contient la logique métier et la gestion des exceptions.
- `repository/ProductRepository.java` : Interface d’accès aux données (Spring Data JPA).
- `dto/ProductDTO.java` : Objet de transfert de données pour les produits.
- `mapper/ProductMapper.java` : Conversion entre entité et DTO (MapStruct).
- `model/Product.java` : Entité JPA représentant un produit.
- `src/main/resources/application.properties` : Configuration de la base de données et du port serveur.

## Endpoints principaux

- `POST   /api/produits` : Créer un produit
- `GET    /api/produits` : Lister tous les produits
- `GET    /api/produits/{id}` : Récupérer un produit par son id
- `PUT    /api/produits/{id}` : Modifier un produit
- `DELETE /api/produits/{id}` : Supprimer un produit
- `GET    /api/produits/search?nom=...` : Rechercher par nom
- `GET    /api/produits/search?categorie=...` : Rechercher par catégorie

## Validation et gestion des erreurs
- Un prix négatif retourne une erreur HTTP 400 (BAD REQUEST).
- Les opérations sur un produit inexistant retournent une erreur HTTP 404 (NOT FOUND).

## Documentation interactive
- Accès à Swagger UI : [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- Tous les endpoints, modèles et exemples de requêtes y sont visibles et testables.

## Configuration de la base de données
- Base PostgreSQL : `BD-PUBLICATION`
- Utilisateur : `postgres`
- Mot de passe : `4321`
- Port : `5432`

## Bonnes pratiques
- Utilisation de Lombok pour réduire le code boilerplate.
- Utilisation de MapStruct pour le mapping DTO/entité.
- Architecture claire et évolutive.
- Gestion centralisée des erreurs et validation métier.

---

**Ce service est prêt pour une utilisation professionnelle, facilement testable et extensible.**
