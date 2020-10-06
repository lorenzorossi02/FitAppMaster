drop table if exists booked_session;
drop table if exists training_session;
drop table if exists trainer;
drop table if exists review;
drop table if exists gym;
drop table if exists course;
drop table if exists users;

create table if not exists users(
    user_id   serial  not null primary key,
    username varchar not null,
    password varchar not null,
    email varchar not null,
    manager  boolean,
    street varchar not null,
    unique(email),
    unique (user_id, username));

create table if not exists course(
    course_id serial not null primary key,
    course_name varchar not null);

create table if not exists gym(
    gym_id serial not null primary key,
    gym_name varchar not null,
    street varchar not null,
    manager_id integer not null,
    manager_name varchar not null,
    foreign key (manager_id, manager_name) references users(user_id, username),
    unique (gym_id, manager_id, manager_name),
    unique (street));

create table if not exists trainer(
    trainer_id serial not null primary key,
    trainer_name varchar not null,
    gym_id int not null,
    kickboxing boolean,
    pugilato boolean,
    zumba boolean,
    salsa boolean,
    funzionale boolean,
    walking boolean,
    pump boolean,
    foreign key (gym_id) references gym(gym_id),
    unique (trainer_name, gym_id));

create table if not exists training_session(
    session_id serial not null primary key,
    trainer_id int not null,
    trainer_name varchar not null,
    course_id integer not null,
    individual boolean,
    gym_id integer not null,
    street varchar not null,
    time_start time not null,
    time_end time not null,
    day date not null,
    description varchar,
    recurrence varchar,
    foreign key (course_id) references course(course_id),
    foreign key (trainer_id) references trainer(trainer_id),
    foreign key (trainer_name,gym_id) references trainer(trainer_name, gym_id),
    foreign key (street) references gym(street));
    /*unique (trainer_id, course_id, time_start, time_end, day));*/

create table if not exists booked_session(
  book_id serial not null primary key,
  session_id int not null,
  user_id int not null,
  foreign key (session_id) references training_session(session_id),
  foreign key (user_id) references users(user_id));

create table if not exists review(
    review_id serial not null primary key,
    user_id   int    not null,
    course_id int    not null,
    gym_id    int    not null,
    review    varchar,
    rating    int    not null,
    foreign key (user_id) references users (user_id),
    foreign key (course_id) references course (course_id),
    foreign key (gym_id) references gym(gym_id),
    unique(review_id, user_id, course_id, gym_id));
/* User */
insert into  users(username, password, email, manager, street) VALUES ('reds','passreds','lorybtc@gmail.com','false', 'Via due Giugno 3');
insert into  users(username, password, email, manager, street) VALUES ('effi','pass','a.effi@gmail.com','false', 'Via del muro linari 21');

/*Gym's manager user */
insert into  users(username, password, email, manager, street) VALUES ('Francesco Totti','pass','redslorenz@gmail.com','true', '');
insert into  users(username, password, email, manager, street) VALUES ('Dzeko','pass','zaguza97@gmail.com','true', '');
insert into  users(username, password, email, manager, street) VALUES ('Florenzi','pass','monkey.d.rossi@gmail.com','true', '');
insert into  users(username, password, email, manager, street) VALUES ('De Rossi','pass','gliassidicuori@gmail.com','true', '');
insert into  users(username, password, email, manager, street) VALUES ('Aldair','pass','a.effi.97@gmail.com','true', '');

/*Courses */
insert into course(course_name) values ('Kick Boxing');
insert into course(course_name) values ('Pugilato');
insert into course(course_name) values ('Zumba');
insert into course(course_name) values ('Salsa');
insert into course(course_name) values ('Funzionale');
insert into course(course_name) values ('Walking');
insert into course(course_name) values ('Pump');


/*Gyms*/
insert into gym(gym_name, street, manager_id, manager_name) values ('Evolution Muscle & Fitness','Via Mura dei Francesi, 164L, 00043 Ciampino RM',3,'Francesco Totti');
insert into gym(gym_name, street, manager_id, manager_name) values ('Haeven Sporting Club','Via Frascineto, 67-71, 00173 Roma RM',4,'Dzeko');
insert into gym(gym_name, street, manager_id, manager_name) values ('McFit','Via Ostiense, 131 L, 00154 Roma RM',5,'Florenzi');
insert into gym(gym_name, street, manager_id, manager_name) values ('Palestra Foro Italico','Viale dei Gladiatori, 248-286, 00135 Roma RM',6,'De Rossi');
insert into gym(gym_name, street, manager_id, manager_name) values ('Supreme Sport Center','Viale Giorgio Morandi, 81, 00155 Roma RM',7,'Aldair');

/*Trainer */
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Giancarlo Magalli', 1, true, true, true, true, true, true, true);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Robert Speedwagon', 2, true, true, true, true, true, true, true);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Carlo Amendola', 3, true, true, true, true, true, true, true);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Jerry Scotti', 4, true, true, true, true, true, true, true);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Francesco Lo Presti', 5, true, false, false, false, false, false, false);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Daniele Carnevale', 5, false, true, false, false, false, false, false);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Giovanni Saggio', 5, false, false, true, false, false, false, false);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Francesco Marinelli', 5, false, false, false, true, false, false, false);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Laura Menini', 5, false, false, false, false, true, false, false);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Vincenzo Grassi', 5, false, false, false, false, false, true, false);
insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('Francesco Quaglia', 5, false, false, false, false, false, false, true);


/*Training Sessions */

/*Gym 1*/
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (1,'Giancarlo Magalli',1, true, 1, 'Via Mura dei Francesi, 164L, 00043 Ciampino RM', '15:00', '16:30', '2020-10-24', 'Questa è una lezione di KickBoxing tenuta da Giancarlo Magalli',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (1,'Giancarlo Magalli',2, false, 1, 'Via Mura dei Francesi, 164L, 00043 Ciampino RM', '16:30', '18:30', '2020-10-24', 'Questa è una lezione di Pugilato tenuta da Giancarlo Magalli',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (1,'Giancarlo Magalli',3, true, 1, 'Via Mura dei Francesi, 164L, 00043 Ciampino RM', '19:00', '20:30', '2020-10-24', 'Questa è una lezione di Zumba tenuta da Giancarlo Magalli',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (1,'Giancarlo Magalli',4, false, 1, 'Via Mura dei Francesi, 164L, 00043 Ciampino RM', '10:00', '11:30', '2020-10-24', 'Questa è una lezione di Salsa tenuta da Giancarlo Magalli',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (1,'Giancarlo Magalli',5, true, 1, 'Via Mura dei Francesi, 164L, 00043 Ciampino RM', '09:00', '10:00', '2020-10-24', 'Questa è una lezione di Funzionale tenuta da Giancarlo Magalli',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (1,'Giancarlo Magalli',6, false, 1, 'Via Mura dei Francesi, 164L, 00043 Ciampino RM', '21:00', '22:30', '2020-10-24', 'Questa è una lezione di Walking tenuta da Giancarlo Magalli',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (1,'Giancarlo Magalli',7, true, 1, 'Via Mura dei Francesi, 164L, 00043 Ciampino RM', '14:00', '15:30', '2020-10-24', 'Questa è una lezione di Pump tenuta da Giancarlo Magalli',null);

/*Gym 2*/
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (2,'Robert Speedwagon',1, true, 2, 'Via Frascineto, 67-71, 00173 Roma RM', '15:00', '16:30', '2020-10-24', 'Questa è una lezione di KickBoxing tenuta da Robert Speedwagon',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (2,'Robert Speedwagon',2, false, 2, 'Via Frascineto, 67-71, 00173 Roma RM', '16:30', '18:30', '2020-10-24', 'Questa è una lezione di Pugilato tenuta da Robert Speedwagon',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (2,'Robert Speedwagon',3, true, 2, 'Via Frascineto, 67-71, 00173 Roma RM', '19:00', '20:30', '2020-10-24', 'Questa è una lezione di Zumba tenuta da Robert Speedwagon',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (2,'Robert Speedwagon',4, false, 2, 'Via Frascineto, 67-71, 00173 Roma RM', '10:00', '11:30', '2020-10-24', 'Questa è una lezione di Salsa tenuta da Robert Speedwagon',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (2,'Robert Speedwagon',5, true, 2, 'Via Frascineto, 67-71, 00173 Roma RM', '09:00', '10:00', '2020-10-24', 'Questa è una lezione di Funzionale tenuta da Robert Speedwagon',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (2,'Robert Speedwagon',6, false, 2, 'Via Frascineto, 67-71, 00173 Roma RM', '21:00', '22:30', '2020-10-24', 'Questa è una lezione di Walking tenuta da Robert Speedwagon',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (2,'Robert Speedwagon',7, true, 2, 'Via Frascineto, 67-71, 00173 Roma RM', '14:00', '15:30', '2020-10-24', 'Questa è una lezione di Pump tenuta da Robert Speedwagon',null);

/*Gym 3*/
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (3,'Carlo Amendola',1, true, 3, 'Via Ostiense, 131 L, 00154 Roma RM', '15:00', '16:30', '2020-10-24', 'Questa è una lezione di KickBoxing tenuta da Carlo Amendola',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (3,'Carlo Amendola',2, false, 3, 'Via Ostiense, 131 L, 00154 Roma RM', '16:30', '18:30', '2020-10-24', 'Questa è una lezione di Pugilato tenuta da Carlo Amendola',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (3,'Carlo Amendola',3, true, 3, 'Via Ostiense, 131 L, 00154 Roma RM', '19:00', '20:30', '2020-10-24', 'Questa è una lezione di Zumba tenuta da Carlo Amendola',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (3,'Carlo Amendola',4, false, 3, 'Via Ostiense, 131 L, 00154 Roma RM', '10:00', '11:30', '2020-10-24', 'Questa è una lezione di Salsa tenuta da Carlo Amendola',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (3,'Carlo Amendola',5, true, 3, 'Via Ostiense, 131 L, 00154 Roma RM', '09:00', '10:00', '2020-10-24', 'Questa è una lezione di Funzionale tenuta da Carlo Amendola',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (3,'Carlo Amendola',6, false, 3, 'Via Ostiense, 131 L, 00154 Roma RM', '21:00', '22:30', '2020-10-24', 'Questa è una lezione di Walking tenuta da Carlo Amendola',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (3,'Carlo Amendola',7, true, 3, 'Via Ostiense, 131 L, 00154 Roma RM', '14:00', '15:30', '2020-10-24', 'Questa è una lezione di Pump tenuta da Carlo Amendola',null);

/*Gym 4*/
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (4,'Jerry Scotti',1, true, 4, 'Viale dei Gladiatori, 248-286, 00135 Roma RM', '15:00', '16:30', '2020-10-24', 'Questa è una lezione di KickBoxing tenuta da Jerry Scotti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (4,'Jerry Scotti',2, false, 4, 'Viale dei Gladiatori, 248-286, 00135 Roma RM', '16:30', '18:30', '2020-10-24', 'Questa è una lezione di Pugilato tenuta da Jerry Scotti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (4,'Jerry Scotti',3, true, 4, 'Viale dei Gladiatori, 248-286, 00135 Roma RM', '19:00', '20:30', '2020-10-24', 'Questa è una lezione di Zumba tenuta da Jerry Scotti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (4,'Jerry Scotti',4, false, 4, 'Viale dei Gladiatori, 248-286, 00135 Roma RM', '10:00', '11:30', '2020-10-24', 'Questa è una lezione di Salsa tenuta da Jerry Scotti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (4,'Jerry Scotti',5, true, 4, 'Viale dei Gladiatori, 248-286, 00135 Roma RM', '09:00', '10:00', '2020-10-24', 'Questa è una lezione di Funzionale tenuta da Jerry Scotti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (4,'Jerry Scotti',6, false, 4, 'Viale dei Gladiatori, 248-286, 00135 Roma RM', '21:00', '22:30', '2020-10-24', 'Questa è una lezione di Walking tenuta da Jerry Scotti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (4,'Jerry Scotti',7, true, 4, 'Viale dei Gladiatori, 248-286, 00135 Roma RM', '14:00', '15:30', '2020-10-24', 'Questa è una lezione di Pump tenuta da Jerry Scotti',null);

/*Gym 5*/
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (5,'Francesco Lo Presti',1, true, 5, 'Viale Giorgio Morandi, 81, 00155 Roma RM', '15:00', '16:30', '2020-10-24', 'Questa è una lezione di KickBoxing tenuta da Francesco Lo Presti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (5,'Francesco Lo Presti',2, false, 5, 'Viale Giorgio Morandi, 81, 00155 Roma RM', '16:30', '18:30', '2020-10-24', 'Questa è una lezione di Pugilato tenuta da Francesco Lo Presti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (5,'Francesco Lo Presti',3, true, 5, 'Viale Giorgio Morandi, 81, 00155 Roma RM', '19:00', '20:30', '2020-10-24', 'Questa è una lezione di Zumba tenuta da Francesco Lo Presti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (5,'Francesco Lo Presti',4, false, 5, 'Viale Giorgio Morandi, 81, 00155 Roma RM', '10:00', '11:30', '2020-10-24', 'Questa è una lezione di Salsa tenuta da Francesco Lo Presti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (5,'Francesco Lo Presti',5, true, 5, 'Viale Giorgio Morandi, 81, 00155 Roma RM', '09:00', '10:00', '2020-10-24', 'Questa è una lezione di Funzionale tenuta da Francesco Lo Presti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (5,'Francesco Lo Presti',6, false, 5, 'Viale Giorgio Morandi, 81, 00155 Roma RM', '21:00', '22:30', '2020-10-24', 'Questa è una lezione di Walking tenuta da Francesco Lo Presti',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (5,'Francesco Lo Presti',7, true, 5, 'Viale Giorgio Morandi, 81, 00155 Roma RM', '14:00', '15:30', '2020-10-24', 'Questa è una lezione di Pump tenuta da Francesco Lo Presti',null);


/*Booked Sessions*/
insert into booked_session(session_id, user_id) VALUES (1,1);
insert into booked_session(session_id, user_id) VALUES (4,2);
insert into booked_session(session_id, user_id) VALUES (3,1);
insert into booked_session(session_id, user_id) VALUES (8,2);
insert into booked_session(session_id, user_id) VALUES (11,1);
insert into booked_session(session_id, user_id) VALUES (22,2);
insert into booked_session(session_id, user_id) VALUES (32,1);
insert into booked_session(session_id, user_id) VALUES (1, 2);
insert into booked_session(session_id, user_id) VALUES (15,1);
insert into booked_session(session_id, user_id) VALUES (18,2);
insert into booked_session(session_id, user_id) VALUES (21,1);
insert into booked_session(session_id, user_id) VALUES (19,2);
insert into booked_session(session_id, user_id) VALUES (17,1);
insert into booked_session(session_id, user_id) VALUES (4,2);
insert into booked_session(session_id, user_id) VALUES (3,1);
insert into booked_session(session_id, user_id) VALUES (2, 2);
insert into booked_session(session_id, user_id) VALUES (25,1);
insert into booked_session(session_id, user_id) VALUES (26,2);
insert into booked_session(session_id, user_id) VALUES (23,1);
insert into booked_session(session_id, user_id) VALUES (7,2);
insert into booked_session(session_id, user_id) VALUES (9,1);
insert into booked_session(session_id, user_id) VALUES (31,2);
insert into booked_session(session_id, user_id) VALUES (32,1);
insert into booked_session(session_id, user_id) VALUES (34, 2);
