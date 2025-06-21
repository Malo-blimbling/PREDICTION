# Documentation locale du Service Communication

> Dernière mise à jour : 26 mai 2025

## Présentation
Ce service permet de gérer les messages associés à des publications. Il expose une API REST accessible sur le port 8083.

## Endpoints principaux

### 1. Créer un message
- **POST** `/api/messages`
- **Body exemple :**
```json
{
  "publicationId": 1,
  "utilisateurId": "123e4567-e89b-12d3-a456-426614174000",
  "contenu": "Ceci est un message de test"
}
```
- **Réponse :** 201 + Message créé

### 2. Récupérer les messages d'une publication
- **GET** `/api/messages/publication/{publicationId}`
- **Réponse :** 200 + Liste de messages

### 3. Récupérer un message par son id
- **GET** `/api/messages/{id}`
- **Réponse :** 200 + Message ou 404

### 4. Modifier un message
- **PUT** `/api/messages/{id}`
- **Body exemple :**
```json
{
  "publicationId": 1,
  "utilisateurId": "123e4567-e89b-12d3-a456-426614174000",
  "contenu": "Message modifié"
}
```
- **Réponse :** 200 + Message modifié ou 404

### 5. Supprimer un message
- **DELETE** `/api/messages/{id}`
- **Réponse :** 204 (No Content)

## Accès à la documentation interactive
Après démarrage du service, accédez à :
- [http://localhost:8083/swagger-ui.html](http://localhost:8083/swagger-ui.html)

## Sécurité
- Tous les endpoints sont accessibles sans authentification supplémentaire (la sécurité est gérée en amont par le microservice Authentification).

## Base de données
- PostgreSQL, nom de la base : `BD-PUBLICATION`, port : `4321`

## Exemple de configuration `application.properties`
```
spring.application.name=ServiceCommunication
server.port=8083
spring.datasource.url=jdbc:postgresql://localhost:4321/BD-PUBLICATION
spring.datasource.username=postgres
spring.datasource.password=4321
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## Technologies utilisées
- Spring Boot
- Spring Data JPA
- PostgreSQL
- MapStruct
- Lombok
- Springdoc OpenAPI (Swagger)

## Appels inter-microservices via l’API Gateway

### URLs utilisées dans `MessageService`

```
private final String USER_SERVICE_URL = "http://localhost:9000/api/auth/users/";
private final String PUBLICATION_SERVICE_URL = "http://localhost:9000/api/publications/";
```

### Bonnes pratiques appliquées
- Tous les appels inter-microservices passent par l’API Gateway (port 9000).
- Centralisation de la sécurité, du routage et du monitoring via le Gateway.
- Découplage : les microservices n’ont plus besoin de connaître les ports internes des autres services.

### Exemple d’appel dans le code

```java
try {
    restTemplate.getForEntity(USER_SERVICE_URL + messageDTO.getUtilisateurId(), Object.class);
} catch (HttpClientErrorException.NotFound e) {
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
}
```

### Exemple d’appel direct via Postman/Thunder Client
- Vérifier un utilisateur :  
  `GET http://localhost:9000/api/auth/users/{id}`
- Vérifier une publication :  
  `GET http://localhost:9000/api/publications/{id}`
- Vérifier un message (exposé par ServiceCommunication) :  
  `GET http://localhost:9000/api/messages/{id}`

### À retenir
- Ne jamais utiliser d’URL localhost:port direct entre microservices : toujours passer par le gateway.
- Vérifiez que vos routes dans le gateway correspondent bien aux chemins utilisés dans vos appels.
- Le gateway doit être démarré et enregistré dans Eureka pour que tout fonctionne.

---
Pour toute question ou évolution, contactez l’équipe de développement.
