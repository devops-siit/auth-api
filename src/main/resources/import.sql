insert into authority (role) values ('ROLE_REGISTERED_USER');
insert into authority (role) values ('ROLE_ADMIN');

--REGISTERED USERS
insert into users ( active, username, email, first_name, last_name, password, verified) values ( true, 'at@gmail.com','at@gmail.com', 'Elaina','Woodward', '$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO', true);
insert into users (active, username, email, first_name, last_name, password, verified) values ( true, 'ulempenny1@weebly.com', 'ulempenny1@weebly.com', 'Ulrick', 'Lempenny', '$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO', true);
insert into users (active, username, email, first_name, last_name, password, verified) values ( true, 'acooke2@seesaa.net','acooke2@seesaa.net', 'Adolf', 'Cooke', '$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO', true);

--ADMINS
insert into users (active, username, email, first_name, last_name, password, verified) values ( true, 'admin@gmail.com','admin@gmail.com', 'Fanya', 'Attow', '$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO', true);

-- USERS AUTHORITIES
insert into users_authority (user_id, authority_id) values (1, 1);
insert into users_authority (user_id, authority_id) values (2, 1);
insert into users_authority (user_id, authority_id) values (3, 1);
insert into users_authority (user_id, authority_id) values (4, 2);
