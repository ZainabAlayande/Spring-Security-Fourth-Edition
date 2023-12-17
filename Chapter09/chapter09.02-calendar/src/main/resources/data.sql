-- ROLES --
insert into role(id, name) values (0, 'ROLE_USER');
insert into role(id, name) values (1, 'ROLE_ADMIN');


-- Events --
insert into events (id,date_when,summary,description,owner,attendee) values (100,'2023-07-03 20:30:00','Birthday Party','This is going to be a great birthday',0,1);
insert into events (id,date_when,summary,description,owner,attendee) values (101,'2023-12-23 13:00:00','Conference Call','Call with the client',2,0);
insert into events (id,date_when,summary,description,owner,attendee) values (102,'2023-09-14 11:30:00','Vacation','Paragliding in Greece',1,2);

