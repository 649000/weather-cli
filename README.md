# Weather CLI
## Overview
This is a Java based CLI app that queries for weather condition for a given location.
The objective of this CLI app are as follows:
1. Familiarise with Java
2. Utilise package manager such as Maven
3. Read in arguments through the command line
4. Initiating and consuming HTTP REST APIs

## Features
The CLI app will return current weather conditions for a given location.

## Running the project
This project utilises APIs from
1. [Open Weather API](https://openweathermap.org/api)
2. [Mapbox API](https://docs.mapbox.com/api/overview/)

API keys are required and must be set as an environment variable to run this app

Example on setting environment variable on macOS:
```
export mapboxAPIKey=<API KEY GOES HERE>
export weatherAPIKey=<API KEY GOES HERE>
```

To run the app
1. Compile the project into a executable jar

```
mvn clean package
```

2. Change directory into `/target` folder, and execute the jar file with dependency
```
java -jar weather-cli-1.0-SNAPSHOT-jar-with-dependencies.jar Melbourne
```

## Dependencies
1. Java 14.0.1
2. Apache Maven 3.6.3
3. Apache httpcomponents 4.5.13
4. Jackson Databind 2.13.3
5. Lombok 1.18.24