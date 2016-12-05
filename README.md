# Libronuments
Libronuments is a great way to view all of the public libraries and monuments in Baltimore, MD.
In addition, the underlying code can be used to learn about Hibernate, Spring, JUnit, RESTful Web Services, Gradle and AngularJS, as well as various software development concepts.

# Features
- Displays all libraries and monuments together on a single grid.
- Searching / filtering / sorting capabilities.
- Ability to add, delete and update libraries and monuments. Updates occur in-line by double-clicking on data. All changes persist to the DB.
- All fields are validated.
- Google Maps integration - Libraries and monuments are geocoded on startup and added to the map. Data updates occur in real-time.
- Dynamic pie chart which shows the numbers of libraries and monuments in Baltimore, MD in real-time.
- Automated installation - Run one command to compile and run the application in an embedded Tomcat instance.
- Organized code and data structures, which can serve as a reference on how to use various technologies and how they work together.

# Technologies Used
- [Hibernate](http://hibernate.org/) for ORM.
- [Spring](https://spring.io/) as a general application framework as well as Hibernate management, dependency injection and RESTful Web Services.
- [AngularJS](https://angularjs.org/) for client MVC.
- [Angular UI Grid](http://ui-grid.info/) for the client data grid.
- [Google Maps API](https://developers.google.com/maps/) for mapping.
- [United States Census data](https://geocoding.geo.census.gov/) for geocoding.
- [Angular Chart](https://jtblin.github.io/angular-chart.js/) for the pie chart.
- [JUnit](http://junit.org/junit4/) for automated unit testing.
- [Gradle](https://gradle.org/) for building.
- [Flyway](https://flywaydb.org/) for DB script migration.
- [Gradle Tomcat Plugin](https://github.com/bmuschko/gradle-tomcat-plugin) for the embedded Tomcat instance.
- [Library](https://data.baltimorecity.gov/Culture-Arts/Libraries/tgtv-wr5u) and [Monument](https://data.baltimorecity.gov/Culture-Arts/Monuments/cpxf-kxp3) data were exported from the [Open Baltimore](https://data.baltimorecity.gov/) project. Both datasets share a common 'Neighborhood' column that was used to join the data together.

# Installation
Requirements
- [MySQL](https://www.mysql.com/)
- [Gradle](https://gradle.org/)

### 1. Edit the DB properties in etc/db.properties to match your database.
    db.url = jdbc:mysql://localhost:3306/yourDB
    db.user = username
    db.password = 123456

### 2. Edit the API properties in etc/api.properties to add your Google API key. If you don't have an API key you can get one [here](https://developers.google.com/maps/documentation/javascript/get-api-key).
	google.maps.api.key = YOUR_API_KEY
    
### 3. Run the 'demo' task. Make sure to use the "--daemon" parameter unless your Gradle daemon is already running.
	gradle --daemon demo
This will do the following:

1. Run the DB scripts
2. Build the code
3. Run the unit tests
4. Start an embedded Tomcat instance with the code
5. Launch a browser to the main page of the application

NOTE - Make sure nothing else is running on localhost:8080 as that is the address the embedded Tomcat instance uses by default.

**If you can see the map with pins, pie chart and a grid full of data then the build was successful!**

# Other Useful Gradle Tasks
1. gradle flywayClean flywayMigrate - Clears the DB and re-runs the DB scripts. 
Useful if you need the DB back in its initial state.
2. gradle clean build - Creates a WAR file that can be deployed manually

# Author
[Chuck Caplan](https://www.linkedin.com/in/charlescaplan)

# License
Copyright 2016 - Chuck Caplan. Libronuments is licensed under the BSD license. See the included LICENSE file for details.
