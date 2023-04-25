# How to run it?

I have added one endpoint for getting all patients here. Two patients are added by default at
app start time.
This app has two end points for visits: add visit and view visit. I added Two lookups
for getting visit types and reasons.
For more info, please use swagger on this link after running app locally (app uses port 9091);
http://localhost:9091/webjars/swagger-ui/index.html

You can use below gradle command to build:

./gradlew build .
Run the app using below command:

./gradlew bootRun

Run the tests using:

./gradlew test .

I usually use buildpack to create image instead of dockerfile:

./gradlew bootBuildImage .


# What good I have added:

Actuator and Micrometer for prometheus metrics

Unit and integration tests

Use of Java Record

Lombok to reduce boilerplate

Swagger/Open API for documentation

Mongo DB Test Container for integration tests 

# What is missing due to time constraints:

Redis for Cache

Spring Security for authentication and authorisation

Logging

# Some opinionated decisions I took:

This app uses Reactive APIs and I chose MongoDB precisely because of more stable reactive repositories.

Another choice, r2dbc availble for reactive repo for RDBMS is not that stable with newer Spring versions so I chose
Mongo over mysql/pgsql/oracle.





