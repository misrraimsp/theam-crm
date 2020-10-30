# theam-crm

This project shows a simple REST API to manage customer data. It is built using Spring Boot and Keycloak.

### Authorization Server and Resource Server set up

 1. [Download Keycloak zip file](https://www.keycloak.org/downloads), unzip it with file name <_name_> at <_path_> (this project used keycloak-11.0.2) and then run `<path>/<name>/bin/standalone.sh`. That command will launch Keycloak server on port 8080, so that you can head `http://localhost:8080/auth`
    and create an admin account by filling the form that will be shown the first time you reach the server.
    
 2. Import the realm configuration file __realm-config.json__. This file defines a _realm_ with a single _client_ and two _realm-roles_ (USER and ADMIN).
 3. Create a user with ADMIN role following [this](https://www.keycloak.org/docs/latest/getting_started/index.html#creating-a-user).
 4. After Keycloak started successfully, you can start the Spring Boot application in a separate terminal via `mvn package spring-boot:run`, from the project directory. This will make available at port 8081 the Resource Server (this REST API).

### Consuming the Resource Server API

The developed API is secured via OAuth 2.0 protocol, so that every request must hold a valid Access Token. In order to do that, the [OAuth 2.0 Authorization Code](https://oauth.net/2/grant-types/authorization-code/) flow has to be followed:

 1. Place a GET request with the browser at:
 
    ```http://localhost:8080/auth/realms/first-realm/protocol/openid-connect/auth?client_id=first-client&response_type=code&scope=openid profile&redirect_uri=http://localhost:8082/callback&state=wtfmnnn```
    
    The above is the request from the client for a __code__. The Authorization Server will then response, if everything is ok, at `redirect_uri=http://localhost:8082/callback` with the __code__ as a url query parameter.
 
 2. With that code, POST a request at `http://localhost:8080/auth/realms/first-realm/protocol/openid-connect/token`. The code has to be in a form parameter.
 3. Keycloak will response with the Access Token, so that now you can insert it on every request. The insertion has to be made in the Authorization header, as __Bearer__.
 4. The Resource Server exposed endpoints are (the ones under `/admin` require the user to have ADMIN role):
 
    - GET ~/user/customers
    - GET ~/user/customers/{id}
    - POST ~/user/customers
    - PUT ~/user/customers
    - DELETE ~/user/customers/{id}
    - GET ~/user/customers/{id}/image
    - POST ~/user/customers/{id}/image
    - GET ~/admin/users
    - GET ~/admin/users/{id}
    - POST ~/admin/users
    - PUT ~/admin/users
    - DELETE ~/admin/users/{id}
    
 5. Examples of all these HTTP requests, both to the Resource Server and the Authorization Server, can be found in the [Postman](https://www.postman.com/) collections provided with the project:
    
    __CRM-THEAM.postman_collection.json__
    __OAUTH.postman_collection.json__
