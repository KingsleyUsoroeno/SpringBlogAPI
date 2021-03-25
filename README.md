# Spring Boot JWT

![](https://img.shields.io/badge/build-success-brightgreen.svg)

# Stack

![](https://img.shields.io/badge/java_8-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/mysql-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)
![](https://img.shields.io/badge/swagger_2-✓-blue.svg)


<h3 align="center">Please help this repo with a :star: if you find it useful! :blush:</h3>


# File structure

```
BlogPostApi/
 │
 ├── src/main/java/
 │   └── kingtech
 │       ├── constants
 │       │   └── SecurityConstants.java
 │       │
 │       ├── controller
 │       │   └── BlogController.java
 │       │   └── UserController.java
 │       │
 │       ├── data
 │       ├── model
 │       │   ├── Blog.java
 │       │   └── CacheUserModel.java
 │       │   └── CacheUserModel.java
 │       │   └── CreateBlogRequestModel.java
 │       │   └── CustomResponseModel.java
 │       │   └── JwtResponse.java
 │       │   └── LoginRequest.java
 │       │
 │       ├── filters
 │       │   ├── JWTAuthenticationFilter.java
 │       │   └── JWTAuthorizationFilter.java
 │       │   └── WebSecurity.java
 │       │
 │       ├── repository
 │       │   ├── Role.java
 │       │   └── User.java
 │       │
 │       ├── repository
 │       │   └── BlogRepository.java
 │       │   └── UserRepository.java
 │       │
 │       │
 │       ├── services
 │       │   └── UserService.java
 │       │   └── UserServiceImpl.java
 │       │
 │       └── MainApplication.java
 │
 ├── src/main/resources/
 │   └── application.yml
 │
 ├── .gitignore
 ├── LICENSE
 ├── mvnw/mvnw.cmd
 ├── README.md
 └── pom.xml
```


 The Following project is my take on building a functional web based backend service with spring and spring MVC
 The project is backed by a SQL Database and is responsible for saving user details as well as saving Blog 
 post for a user, only authorised users are allowed access to certain routes such as **/blog/** which fetches all the users
 in the database , this is done by using a **JWTAuthentication** Filter which is triggered anytime a request comes into the 
 Application and validates whether a token is provided or whether the provided token is valid or has expired.
 
 
 Feel free to contribute by forking and creating a PR, i hope to add upon the project by creating roles and
 making it more extensive
 
 
    