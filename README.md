# Taxi Station Simulator

This is study project for course "JAVA Programming Languages" @ Afeka College of Engineering.

Project simulates lifecycle of taxi station. 
There is station that has taxi cabs and passengers
Each taxi can be in next 3 states: waiting for passengers, on break or driving to the distination
Passengers can wait in queue for available taxi cab or drive to destination

## Implementation Highlights

* Java 1.7 compatible
* Maven dependency manager
* 4 different implmentations: Swing GUI, Socket Client-Server, Web Application, [Android Client][1]
* Derby db
* Glassfish web server
* Spring Framework
* JPA or JDBC to communicate with db (Can be switched in spring.xml)
* JAXB for loading and saving of configuration
* Spring AOP used for logging
* Multi-threading: Station, taxis and passengers are running in separate threads 

## Authors

Alex Gavrishev, Eran Zimbler 2012

 [1]: https://github.com/anod/TaxiStationAndroid
