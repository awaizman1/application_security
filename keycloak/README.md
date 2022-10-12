# Keycloak
We are going to use keycloak as our IDP.

To run keycloak and import realm settings (clients, roles, groups, etc.) , run:
```bash
docker run -d -p 8180:8080 --name=keycloak -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -v "$(pwd)"/export:/opt/keycloak/data/import quay.io/keycloak/keycloak:19.0.2 start-dev --import-realm
```

Keycloak home page will be served at http://localhost:8180/.
