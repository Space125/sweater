delete from message;

insert into message(id, tag, text, user_id) values
(1, 'first', 'First Message', 1),
(2, 'second', 'Second Message', 1),
(3, 'third', 'Third Message', 1),
(4, 'first', 'Fourth Message', 1);

alter sequence hibernate_sequence restart with 10;