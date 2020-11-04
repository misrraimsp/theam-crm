# theam-crm

This project shows a simple REST API to manage customer data. It is built using Spring Boot and Keycloak.

### Authorization Server and Resource Server set up

 1. [Download](https://quay.io/repository/keycloak/keycloak) the Keycloak Docker image by running `docker pull quay.io/keycloak/keycloak`
 
 2. Run the Authorization Server (AS) with:
 
    `docker run --name AS -e KEYCLOAK_USER=master -e KEYCLOAK_PASSWORD=master -e KEYCLOAK_IMPORT=/tmp/realm-config.json -v <path>/realm-config.json:/tmp/realm-config.json -p 8080:8080 -it quay.io/keycloak/keycloak `
    
    Note that the above command:
    
    - Creates a `master` user that has full control of the AS.
    - Set a volume mapping in order to pass as argument the realm configuration file [__realm-config.json__](https://github.com/misrraimsp/theam-crm/blob/master/realm-config.json), being `<path>` the address of that file in your local machine.
    - Imports the realm configuration file [__realm-config.json__](https://github.com/misrraimsp/theam-crm/blob/master/realm-config.json). This file defines a _realm_ (named _first-realm_) with a single _client_ (named _first-client_) and two _realm-roles_ (USER and ADMIN).
    - Enable the AS at port 8080.

 3. Head over `http://localhost:8080/auth/`, click on `Administration Console` and fill in the form with _master_ as username and password.
 
 4. Create a new user with ADMIN role. To do so:
    - Click on `Users`, and then `Add user`.
    - Fill in the form (only `Username` is required) and save.
    - Go to `Role Mappings` and add the ADMIN role to assigned roles.
    - If desired, go to `Credentials` and set a password for this newly created ADMIN user.
 
 5. After the AS initial configuration, you can start the Resource Server (RS) in a separate terminal via `mvn package spring-boot:run`, from the project directory. This will make available at port 8081 the RS.

### Consuming the Resource Server API

The developed API is secured via OAuth 2.0 protocol, so that every request must hold a valid Access Token. In order to do that, the [OAuth 2.0 Authorization Code](https://oauth.net/2/grant-types/authorization-code/) flow has to be followed:

 1. Place a GET request with the browser at:
 
    ```http://localhost:8080/auth/realms/first-realm/protocol/openid-connect/auth?client_id=first-client&response_type=code&scope=openid profile&redirect_uri=http://localhost:8082/callback&state=wtfmnnn```
    
    The above is the request from the client for a __code__. The AS will then response, if everything is ok, at `redirect_uri=http://localhost:8082/callback` with the __code__ as a url query parameter.
 
 2. With that code, POST a request at `http://localhost:8080/auth/realms/first-realm/protocol/openid-connect/token`. The __code__, and another OAuth required info, has to be in form parameters. For simplicity, use the [__OAUTH.postman_collection.json__](https://github.com/misrraimsp/theam-crm/blob/master/postman/OAUTH.postman_collection.json) provided with the project. Note that the `client_secret` parameter can be found by the `master` user under `Clients >> first-client >> Credentials >> Regenerate Secret`
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
    
    - [__CRM-THEAM.postman_collection.json__](https://github.com/misrraimsp/theam-crm/blob/master/postman/CRM-THEAM.postman_collection.json)
    - [__OAUTH.postman_collection.json__](https://github.com/misrraimsp/theam-crm/blob/master/postman/OAUTH.postman_collection.json)
