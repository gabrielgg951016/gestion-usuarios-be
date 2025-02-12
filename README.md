Documentación de la API

Descripción:

Esta API REST proporciona funcionalidades para gestionar usuarios y pedidos.

Autenticación:

Todos los endpoints requieren un token de autorización Bearer. El token se obtiene a través de los siguientes endpoints:

    Registro:
        URL: /auth/register
        Método: POST
        Cuerpo:
        JSON

    {
      "user": "xideral",
      "password": "x1d3r4l"
    }

Login:

    URL: /auth/login
    Método: POST
    Cuerpo:
    JSON

        {
          "name": "xideral",
          "password": "x1d3r4l"
        }

Endpoints de Usuario:

    GET /users/{id}: Obtiene un usuario por su ID.
    GET /users/by-name/{name}: Obtiene un usuario por su nombre.
    POST /users: Crea un nuevo usuario.
    PUT /users/{id}: Actualiza un usuario existente.
    DELETE /users/{id}: Elimina un usuario.

Endpoints de Pedido:

    GET /orders/{id}: Obtiene un pedido por su ID.
    GET /orders/by-user-id/{userId}: Obtiene los pedidos de un usuario específico.
    POST /orders: Crea un nuevo pedido.
    PUT /orders/{id}: Actualiza un pedido existente.
    DELETE /orders/{id}: Elimina un pedido.

Validaciones:

    Se realizan validaciones a nivel de tipo de dato en el controlador.
    Se realizan validaciones a nivel de persistencia en el servicio.
    No se permiten correos electrónicos duplicados.

Características adicionales:

    Manejo de fechas: El campo dateCreated se formatea como "aaaa-mm-dd" en las respuestas.
    Transaccionalidad: Se utiliza @Transactional para garantizar la integridad de las operaciones de base de datos.
    Autenticación basada en tokens: Se utiliza un token JWT para proteger los endpoints.
    Validación de datos: Se validan los datos de entrada para evitar errores.

Variables de entorno:

    SERVER_PORT: Puerto del servidor (por defecto: 8081)
    DB_POSTGRES_LOCAL_URL: URL de conexión a la base de datos PostgreSQL
    DB_POSTGRES_LOCAL_NAME: Nombre de la base de datos PostgreSQL
    DB_POSTGRES_LOCAL_USERNAME: Usuario de la base de datos PostgreSQL
    DB_POSTGRES_LOCAL_PASSWORD: Contraseña del usuario de la base de datos PostgreSQL
    JWT_SECRET_KEY: Clave secreta utilizada para firmar los tokens JWT

Curl - Usuario (User)

GET - por Id

curl --request GET \
  --url http://localhost:8081/users/6 \
  --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij'

GET - por nombre

curl --request GET \ --url http://localhost:8081/users/by-name/Gabriel \ --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij'

POST - crear Usuario

curl --request POST \
  --url http://localhost:8081/users \
  --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij' \
  --header 'content-type: application/json' \
  --data '{
  "name": "marcos",
  "email": "marcos@gmail.comm",
  "date_created": "2025-12-01"
}'

PUT - editar Usuario

curl --request PUT \
  --url http://localhost:8081/users/3 \
  --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij' \
  --header 'content-type: application/json' \
  --data '{
  "id": 3,
  "name": "jorge",
  "email": "jorge@gmail.comm",
  "date_created": "2025-02-10"
}'

DELETE - eliminar usuario por Id

curl --request DELETE \
  --url http://localhost:8081/users/3 \
  --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij'

Pedidos (Order)

GET - por Id

curl --request GET \
  --url http://localhost:8081/orders/6 \
  --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij'

GET - por id del usuario

curl --request GET \
  --url http://localhost:8081/orders/by-user-id/6 \
  --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij'

POST - crear pedido

curl --request POST \
  --url http://localhost:8081/orders \
  --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij' \
  --header 'content-type: application/json' \
  --data '{
    "user_id": 6,
    "status": "COMPLETADO",
    "total": 12,
    "date_created": "2025-01-29"
  }'

PUT - editar pedido

curl --request PUT \
  --url http://localhost:8081/orders/1 \
  --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij' \
  --header 'content-type: application/json' \
  --data '{
    "id": 1,
    "user_id": 7,
    "status": "COMPLETADO",
    "total": 12,
    "date_created": "2025-01-29"
}'

DELETE - borrar pedido

curl --request DELETE \
  --url http://localhost:8081/orders/1 \
  --header 'authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxMCIsIm5hbWUiOiJ4aWRlcmFsIiwic3ViIjoieGlkZXJhbCIsImlhdCI6MTczOTM0OTY5NywiZXhwIjoxNzM5NDM2MDk3fQ.UwjBwl_2vHD4ngfIcjQAORFn_RvWuwwD_mdX5yL6b7GrWTlVi2XM7PqvCQeRHPij'
