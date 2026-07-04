
application.properties is given below
```
spring.application.name=codeverse
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.atr8mdg.mongodb.net/?appName=Cluster0
spring.data.mongodb.database=<dbname>
spring.data.mongodb.auto-index-creation=true
google.auth.redirect_uri=https://developers.google.com/oauthplayground
google.auth.client_id=<google_auth_client_id>
google.auth.client_secret=<google_auth_client_secret>
cloud.cloud-name=<cloud_name>
cloud.api-key=<cloud_api_key>
cloud.api-secret=<cloud_api_secret>
```

![Swagger UI](https://github.com/aditya-kumar-patraan1/Codeverse-Springboot-Backend/blob/main/src/main/resources/static/swagger.png)


Having:

1. Creation of Admin
2. Creation User
3. Getting all Users
4. Login/Register (using Jwt Tokens)
5. Adding Problem
6. Geting all Problems
7. Integration of OAuth2
8. Integration of swagger
9. Added mongo relationship between collections
10. Dashboard handling
