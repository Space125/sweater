delete from user_role;
delete from users;

insert into users(id, active, password, username) values
(1, true, '$2a$08$HXrIfhcHJR3y0z3EnTTORe9ijplaqv/3U5Yb0gTmNfsexy.4.RnnW', 'space'),
(2, true, '$2a$08$HXrIfhcHJR3y0z3EnTTORe9ijplaqv/3U5Yb0gTmNfsexy.4.RnnW', 'dru');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');