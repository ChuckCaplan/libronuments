# Libronuments
Libronuments is a great way to view all of the public libraries and monuments in Baltimore, MD.
In addition, the underlying code can be used to learn about Hibernate, Spring, JUnit, RESTful Web Services, AngularJS and Angular UI Grid,
as well as various software development concepts.

# Features
- Displays all libraries and monuments together on a single grid.
- Searching / filtering / sorting capabilities.
- Ability to add, delete and update libraries and monuments. Updates occur in-line by double-clicking on data. All changes persist to the DB.
- All fields are validated.
- Dynamic pie chart which shows the numbers of libraries, monuments and neighborhoods in Baltimore, MD in real-time.
- Organized code and data structures, which can serve as a reference on how to use various technologies and how they work together.

# Technologies Used
- [Hibernate](http://hibernate.org/) for ORM.
- [Spring](https://spring.io/) as a general application framework as well as Hibernate management, dependency injection and RESTful Web Services.
- [AngularJS](https://angularjs.org/) for client MVC.
- [Angular UI Grid](http://ui-grid.info/) for client data grid.
- [Angular Chart](https://jtblin.github.io/angular-chart.js/) for the pie chart.
- [JUnit](http://junit.org/junit4/) for automated unit testing.
- [Gradle](https://gradle.org/) for building.
- [Library](https://data.baltimorecity.gov/Culture-Arts/Libraries/tgtv-wr5u) and [Monument](https://data.baltimorecity.gov/Culture-Arts/Monuments/cpxf-kxp3) data was exported from the [Open Baltimore](https://data.baltimorecity.gov/) project. Both datasets share a common 'Neighborhood' column that was used to join the data together.

# Installation
Requirements
- [MySQL](https://www.mysql.com/)
- [Tomcat](http://tomcat.apache.org/) or another Java Servlet Container
- [Gradle](https://gradle.org/) for builds

### Load the data into the DB
    cd db
	mysql -u username -p
	create database libronuments;
	use libronuments;
	source load.sql;
	exit

### Edit the **bold** sections below in etc/db.properties
    db.class = com.mysql.jdbc.Driver
    db.url = **jdbc:mysql://localhost:3306/libronuments**
    db.user = **username**
    db.password = **123456**
	
### Create the WAR file and run unit tests
    gradle clean build
    
### Run the unit tests individually if necessary
    gradle test    

### Deploy and run the WAR file
- Copy build/libs/Libronuments-1.war to $TOMCAT_HOME/webapps and wait for it to deploy.
- Point your browser to http://host:port/Libronuments-1/libronuments/home
- If you can see the pie chart and grid full of data then installation was successful!

# Author
[Chuck Caplan](https://www.linkedin.com/in/charlescaplan)

# License
Copyright 2016 - Chuck Caplan. Libronuments is licensed under the BSD license. See the included LICENSE file for details.