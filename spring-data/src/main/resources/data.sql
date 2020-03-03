insert into CourseDetails(id, courseName, createdDate, updateDate) 
values(10001,'JPA in 50 Steps', sysdate(), sysdate());
insert into CourseDetails(id, courseName, createdDate, updateDate) 
values(10002,'Spring in 50 Steps', sysdate(), sysdate());
insert into CourseDetails(id, courseName, createdDate, updateDate) 
values(10003,'Spring Boot in 100 Steps', sysdate(), sysdate());

insert into Passport(id, number, createdDate, updateDate) 
values(30001,'A123456', sysdate(), sysdate());
insert into Passport(id, number, createdDate, updateDate) 
values(30002,'B123456', sysdate(), sysdate());
insert into Passport(id, number, createdDate, updateDate) 
values(30003,'C123456', sysdate(), sysdate());

insert into Student(id, name, passport_id, createdDate, updateDate) 
values(20001,'Nitish', 30001, sysdate(), sysdate());
insert into Student(id, name, passport_id, createdDate, updateDate) 
values(20002,'Shitij', 30002, sysdate(), sysdate());
insert into Student(id, name, passport_id, createdDate, updateDate) 
values(20003,'Pankaj', 30003, sysdate(), sysdate());