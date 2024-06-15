drop table learningdata;
drop table questions;
drop table questions;
drop table user_test_results;
drop table courses;
drop table registration;


create table registration(id serial primary key,name varchar(50),email varchar(50) unique,password varchar(50));
create table courses(id serial primary key,title varchar(100),description varchar(200),image varchar(80));
create table learningdata(id serial primary key,courseid int,title varchar(100),description text,foreign key (courseid) references courses(id));
create table admin(id serial primary key,name varchar(50),email varchar(50) unique,password varchar(50));
CREATE TABLE user_test_results (
    result_id SERIAL PRIMARY KEY,
    user_id INT,
    course_id INT,
    score INT NOT NULL,
    time_taken INT NOT NULL,
    test_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	feedback text,
    FOREIGN KEY (user_id) REFERENCES registration(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE questions (
    question_id SERIAL PRIMARY KEY,
    course_id INT,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option VARCHAR(1) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);


insert into courses(title,description,image) values('Learning portal','You can explore evrythong','loginportal.svg');
insert into learningdata(courseid,title,description) values(1,'Instrcutons','Instructions will be added later');
insert into admin(name,email,password) values('admin','admin@gmail.com','123');