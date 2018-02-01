DROP DATABASE IF EXISTS community;
CREATE DATABASE community Character Set UTF8;
USE community;

CREATE TABLE user_info(
        user_id BIGINT NOT NULL, 
        user_name VARCHAR(100) NOT NULL,
        user_password VARCHAR(100) NOT NULL,
        submission_time DATETIME,
        PRIMARY KEY ( user_id ))ENGINE=InnoDB DEFAULT CHARSET=utf8; 

