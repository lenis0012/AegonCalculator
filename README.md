# AegonCalculator
This project consists of 2 sub-projects and source control managed in git.

`calculator-api` is a Java 11 REST API using Spring Boot, JUnit and Maven.  
`calculator-frontend` is a Javascript front-end using Angular and jasmine.

The git commits can be viewed using `git log`. 

# Installation
Both projects include dependency management. But you do need the following
programs to get set up.

* Java 11 (any sdk)
* Node.js (12.x recommended)
* Angular CLI (`npm i -g @angular/cli`)

Dependency set up:
* Front-end: `npm install`

# Running locally

1. Start the api using `./mvnw spring-boot:run`
2. Start the front-end using `ng serve -o`

# Running the tests

1. Run the back-end tests in JUnit using `./mvnw test`
2. Run the front-end component tests using `ng test`

# Additional information

The front-end routes trafic to the back-end using a proxy.
This proxy forwards request from `/api/**` to `http://localhost:8080`, 
make sure this port is available.

the calculator is tested in the front-end in chrome using jasmine. 
If you do not have chrome or chromium, it is suggested you install it.
Support in other browsers is not guaranteed.
