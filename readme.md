# CSS project

Equipa a Desenvolver

David Dantas 56331
Filipe Egipto 56272
Rafael Nisa 56329


## How to run:

1. Turn on the server by running ./run.sh inside the folder server/
    * That will begin the data base as well as initializing a bunch of citizens and delegates:
        * 15000 citizens will be created of name: **Citizen i**  (where 1 <= i <= 1500)
        * 100 delegates will be created of name: **Delegate i**  (where 1 <= i <= 100)
    * That way you can login in the native app and web by using Delegate 5 for example

2. Now that the server is running, you can either use the web interface in (http://localhost:8080), or by sending rest calls to (http://localhost:8080/api)

3. If you want to use the native app just run client.sh inside the app/ folder

## Server

Contains all business logic, tests, persistence, and Contollers for rest web interaction, and business interaction.


## App

A javafx native app, to run it juwst run ./client.sh,  **maven is required**, and server should be running.(http://localhost:8080)
