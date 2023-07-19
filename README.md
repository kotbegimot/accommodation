# Accommodation Catalogue Backend
### Overview
This application is a middleware that exposes accommodation DB via endpoints.

Open API documentation can be viewed via [Swagger UI](http://localhost:8070/swagger-ui/index.html)
after application start.

### System requirements
The application is containerized with all dependencies being obtained on start.

In order to run application Docker is required.

_Note:_ For Mac OS, once the repository content is cloned, make sure that the folder with the application
is exposed to Docker via File Share to ensure the image can be mounted.

### How to run
To run the application execute from the application folder: ```docker-compose up```

The application will run on http://localhost:8070.

The DB can be accessed via [mysql://localhost:3306/hotels_db](mysql://localhost:3306/hotels_db).
It is pre-filled with mock data for test purposes via `init.sql` in the app root folder.

_Note_: it takes DB a couple of moments to start. Until it is started the application will fail with Connection Error. It will be resolved once DB is up.

### Development requirements
- Java 17
- Spring Boot 3.0.0^
- Maven
- MySQL DB 8.0.0^

## Notes on implementation
### Business logic
Due to time limitation, I was not able to cover all edge-cases I noticed while writing the application. 

Here are notes on such edge-cases:
- **Accommodation duplication**: there is no check if an application already exists on create; however it does make sense 
to have one to prevent duplications from being created
- **Locations' duplication**: on create there is no check if a location exists. As before, for further iterations additional changes 
are necessary to avoid duplications. 
- **Locations**: locations cannot be changed or removed in this implementation.
- **General**: it is possible to create/update an accommodations without providing numeric fields values (e.g. reputation). 
In this case, the accommodation will be assigned default values (0) for such fields. 
- **Validation**: URL validation is strict, to pass it the URL must be full and include protocol. 
- **Filters**: due to time constrains, in this implementation the filters can be only applied one at a time.

### Technical implementation
Due to time constraints I was not able to implement the following:
- Unit tests
- Migration to Kotlin