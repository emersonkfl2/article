# Article Management System
This project is a simple article management system that allows the creation, retrieval and deletion of articles, as well as their comments. The application uses the REST architecture style, and the data is stored in memory (not persisted).

## Features
- Creation of articles and comments.
- Retrieval of articles and comments.
- Deletion of articles and comments.
- Updating articles and comments
## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- JUnit
- Jackson

## Endpoints
The following endpoints are available:

### Article Endpoints
- `GET /articles`: Retrieve a list of all articles.
- `GET /articles/{id}`: Retrieve a single article by ID.
- `POST /articles`: creates a new article
- `PUT /articles/{id}`: Update an existing article.
- `DELETE /articles/{id}` : deletes an article by ID.
### Comments Endpoints
- `GET /comments`: Retrieve a list of all comments.
- `GET /comments/{id}`: Retrieve a single comment by ID.
- `POST /comments`: creates a new comment and add this comment to referring article.
- `PUT /comments/{id}`: Update an existing comment.
- `DELETE /comments/{id}` : deletes an comment by ID.

## How to run the project
1. Clone the repository using `git clone https://github.com/emersonkfl2/article.git`
2. Navigate to the project directory using `cd article`
3. Build the project using `mvn clean install`
4. Run the project using `mvn spring-boot:run`
5. The application will be running on `http://localhost:8080/api/`


