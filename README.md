# DRIVEO - Backend

## Introduction
Backend part of application developed for courses [IDATA2301](https://www.ntnu.edu/studies/courses/IDATA2301#tab=omEmnet) and [IDATA2306](https://www.ntnu.edu/studies/courses/IDATA2306#tab=omEmnet) at NTNU Ålesund.

The project is a simple car rental application where companies go to advertise their cars.


### Contributors
| Student Name     | GitHub account                              | Email Account            |
| -----------------|---------------------------------------------|--------------------------|
|Kristian Garder   | [Kristian54](https://github.com/Kristian54) | kristian.garder@ntnu.no  |
|Ludvik Lund-Hole  | [ludlun04](https://github.com/ludlun04)     | ludvik.lund-hole@ntnu.no |
|Stian Øye Jenssen | [Grade123](https://github.com/Grade123)     | stianoj@stud.ntnu.no     |


## How to run (With maven)

### Prerequisites:
- JDK-21 or later
- Maven
- Docker installed and running
- env file with the following content

```
POSTGRES_DB
POSTGRES_USERNAME
POSTGRES_PASSWORD
DATABASE_ADDRESS
JWT_SECRET_KEY
REACT_ORIGIN
REGULAR_USER_EMAIL
REGULAR_USER_PASSWORD
ADMIN_USER_EMAIL
ADMIN_USER_PASSWORD
COMPANY_USER_EMAIL
COMPANY_USER_PASSWORD
```

### Run The Application
1. Open terminal and navigate to the root of the project
2. Run ``mvn spring-boot:run``

