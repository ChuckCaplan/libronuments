-- CSV data comes from https://data.baltimorecity.gov/

-- create the tables. re-create them if they already exist.

drop table if exists library;

drop table if exists monument;

drop table if exists mstr_neighborhood;

create table library (
name_txt varchar(50) COMMENT 'Name of the library',
zipcode_txt varchar(5) COMMENT '5 digit ZIP code of the library',
neighborhood_txt varchar(50) COMMENT 'Neighborhood, will be deleted later in this script',
council_district_txt int COMMENT 'council, will be dropped later in this script',
police_district_txt varchar(15) COMMENT 'police district, will be dropped later in this script',
location_txt varchar(100) COMMENT 'Address of the library'
);

create table monument (
name_txt varchar(50) COMMENT 'Name of the monument',
zipcode_txt varchar(5) COMMENT '5 digit ZIP code of the monument',
neighborhood_txt varchar(50) COMMENT 'Neighborhood, will be deleted later in this script',
council_district_txt int COMMENT 'council, will be dropped later in this script',
police_district_txt varchar(15) COMMENT 'police district, will be dropped later in this script',
location_txt varchar(100) COMMENT 'Address of the monument'
);

-- load the data from the csv's

LOAD DATA LOCAL INFILE './libraries.csv' 
INTO TABLE library 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

LOAD DATA LOCAL INFILE './monuments.csv' 
INTO TABLE monument
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

-- drop data we will not be using
alter table library drop column council_district_txt;

alter table library drop column police_district_txt;

alter table monument drop column council_district_txt;

alter table monument drop column police_district_txt;

-- add primary keys

ALTER TABLE library ADD library_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'unique identifier for a library' FIRST;

ALTER TABLE monument ADD monument_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'unique identifier for a monument' FIRST;

-- create the master neighborhood table, populate it and alter the other tables to reference it

create table mstr_neighborhood (
neighborhood_id int auto_increment primary key COMMENT 'unique identifier for a neighborhood',
neighborhood_txt varchar(50) COMMENT 'neighborhood description'
);

-- populate the master neighborhood table
insert into mstr_neighborhood (neighborhood_txt) (select distinct neighborhood_txt from
(select distinct neighborhood_txt from library
UNION
select distinct neighborhood_txt from monument) u
);

alter table library
add column neighborhood_id int COMMENT 'foreign key to neighborhood table',
ADD FOREIGN KEY fk_name(neighborhood_id) REFERENCES mstr_neighborhood(neighborhood_id);

alter table monument 
add column neighborhood_id int COMMENT 'foreign key to neighborhood table',
ADD FOREIGN KEY fk_name(neighborhood_id) REFERENCES mstr_neighborhood(neighborhood_id);

-- populate neighborhood_id's and drop text

update library l set neighborhood_id = (select neighborhood_id from mstr_neighborhood m where m.neighborhood_txt = l.neighborhood_txt);

update monument l set neighborhood_id = (select neighborhood_id from mstr_neighborhood m where m.neighborhood_txt = l.neighborhood_txt); 

alter table library drop column neighborhood_txt;

alter table monument drop column neighborhood_txt;
