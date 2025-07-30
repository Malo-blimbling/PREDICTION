# Documentation API - Publication d'une annonce

Cette documentation décrit les endpoints API pour gérer les annonces de produits, destinés à être consommés par le frontend.

---

## Endpoints REST

### Créer une annonce

- **POST /api/publications**

  Crée une nouvelle annonce de produit.

  **Corps de la requête :**  
  Objet JSON `PublicationDTO` avec les champs suivants (exemple) :  
  ```json
  {
    "titre": "Nom du produit",
    "description": "Description détaillée du produit",
    "prix": 100.0,
    "datePublication": "2024-06-01T12:00:00",
    "utilisateurId": "uuid-de-l-utilisateur",
    "region": "nom-de-la-region",
    "photos": [
      {
        "url": "http://exemple.com/photo1.jpg"
      }
    ]
  }
  ```

  **Réponse :**  
  Objet JSON `PublicationDTO` avec l'ID généré et les données de l'annonce créée.

  **Codes de réponse :**  
  - 201 Created : Annonce créée avec succès  
  - 400 Bad Request : Données invalides

---

### Récupérer toutes les annonces

- **GET /api/publications**

  Récupère la liste de toutes les annonces.

  **Réponse :**  
  Liste d'objets `PublicationDTO`.

---

### Récupérer une annonce par ID

- **GET /api/publications/{id}**

  Récupère une annonce spécifique par son ID.

  **Paramètres :**  
  - `id` : ID de l'annonce (Long)

  **Réponse :**  
  Objet `PublicationDTO` correspondant à l'annonce.

  **Codes de réponse :**  
  - 200 OK : Annonce trouvée  
  - 404 Not Found : Annonce non trouvée

---

### Récupérer les annonces d'un utilisateur

- **GET /api/publications/utilisateur/{utilisateurId}**

  Récupère toutes les annonces publiées par un utilisateur donné.

  **Paramètres :**  
  - `utilisateurId` : UUID de l'utilisateur

  **Réponse :**  
  Liste d'objets `PublicationDTO`.

---

### Récupérer les annonces par région

- **GET /api/publications/region/{region}**

  Récupère toutes les annonces publiées dans une région donnée.

  **Paramètres :**  
  - `region` : Nom de la région (String)

  **Réponse :**  
  Liste d'objets `PublicationDTO`.

---

### Supprimer une annonce

- **DELETE /api/publications/{id}**

  Supprime une annonce par son ID.

  **Paramètres :**  
  - `id` : ID de l'annonce (Long)

  **Réponse :**  
  Aucun contenu (204 No Content)

---

## Notes

- Les IDs des annonces sont de type Long.
- Les IDs des utilisateurs sont de type UUID.
- Les objets `PublicationDTO` contiennent les informations détaillées des annonces (titre, description, prix, photos, région, etc.).
- Les dates doivent être au format ISO8601.
- Le frontend doit envoyer et recevoir les données au format JSON.

---

Pour toute question, contactez l'équipe de développement.
