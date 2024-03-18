# REST API with Authentication (JWT)

This project was generated with [Spring Boot]()

## About this project

*This project is a secure login and registration system with JWT authentication and validations.*

**Note:** Only authenticated users can update or delete their own accounts. 
Authentication is done using a token received after login.
With the `users/me` endpoint it is possible to receive information about the authenticated user.

#### Example:

1. In Postman, navigate to the Headers section.
2. Add a new header with the key `Authorization`.
3. Set the value to `Bearer` followed by a space and then your token.

## Technologies
```diff
+ Java
+ Maven - version 4.0.0
+ Spring Web
+ JPA
+ Database development MySQL
+ Lombok
+ JWT - version 4.4.0
+ Spring Security 
+ Validation
+ Modelmapper - version 3.1.1
```

## Development in localhost

- [x] [*$*](https://git-scm.com/) `git clone https://github.com/jnorgini/spring-jwt`

- [x] in the cloned project directory [*$*]() `git checkout develop`

- [x] [VS Code](https://code.visualstudio.com/) open the project and start `SpringJwtApplication.java` after adjusting the database settings in `application.properties`

- [x] [STS4](https://spring.io/tools) import maven project and start local server after adjusting database settings in `application.properties`

## Project structure

![Captura de tela 2024-03-17 200605](https://github.com/jnorgini/spring-jwt/assets/114461353/58eb4711-0c7e-4351-a1a3-12197f5926d4)

## Automation testing with Postman

<div>
  <img src="https://github.com/jnorgini/jnorgini/assets/114461353/2bf58641-ae62-488e-97cc-7d6494cb903a" width="800" height="439" style="margin-right: 5px;">
  <img src="https://github.com/jnorgini/jnorgini/assets/114461353/9d8879ef-11c5-4d81-9696-83231344bc3e" width="800" height="395" style="margin-left: 5px;">
</div>

<div style="margin-top: 5px;">
  <img src="https://github.com/jnorgini/jnorgini/assets/114461353/ef83dc5d-1b68-486b-83cd-33652050b0af" width="800" height="480" style="margin-right: 5px;">
  <img src="https://github.com/jnorgini/jnorgini/assets/114461353/05a57cc0-45e5-4e6e-b263-29aec0695fe2" width="800" height="461" style="margin-left: 5px;">
</div>

<div style="margin-top: 5px;">
  <img src="https://github.com/jnorgini/jnorgini/assets/114461353/d19a0e91-fb5b-4285-ac71-cc1f27261295" width="800" height="461" style="margin-right: 5px;">
  <img src="https://github.com/jnorgini/jnorgini/assets/114461353/e692c279-681d-400b-bd3a-5bc91c5edcb6" width="800" height="432" style="margin-left: 5px;">
</div>

<div style="margin-top: 5px;">
  <img src="https://github.com/jnorgini/jnorgini/assets/114461353/48bb223f-239b-4623-8231-f5f6b8cadf8a" width="800" height="267" style="margin-bottom: 10px;">
</div>

## In progress...

📌 Deployment and [cron jobs](https://cron-job.org/en/) 

## Developed by

🛠️ Juliana Norgini

## Contact

💼 [LinkedIn](https://www.linkedin.com/in/juliana-norgini/)

📧 [E-mail](mailto:jnorgini@gmail.com)
