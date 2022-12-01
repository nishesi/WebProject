insert into users (username, password, first_name, last_name, birthdate, country)
values ('NishEsI', 'b62b9cc8893ad5f1e04ffc0f7f36410e', 'Nurislam', 'Zaripov', '2003-03-22', 'Russia'),
       ('MEDICHI', 'b62b9cc8893ad5f1e04ffc0f7f36410e', 'Saydash', 'Gilyazov', '2003-09-27', 'Russia'),
       ('KishMishKuraga', 'b62b9cc8893ad5f1e04ffc0f7f36410e', 'Farit', 'Ibragimov', '2003-07-25', 'Russia');

insert into channels (id, name, info, owner_id)
values (1, 'channel number one', 'BEST OF THE BEST', 'NishEsI'),
       (2, 'channel number two', 'SECOND OF THE SECOND', 'MEDICHI'),
       (3, 'channel number thr', 'THIRD OF THE THIRD', 'KishMishKuraga');

insert into videos (uuid, name, added_date, channel_id, duration, info)
values ('d1ee017c-e45c-43c3-ad09-299509a504a3', 'video from fisrt channe name', '2022-11-2', 1, '00:11:10',
        'best video from channel one'),
       ('01eca157-f7d7-43d8-9228-587804d6434e', 'video from second channel name', '2022-11-2', 2, '00:12:10',
        'best video from channel two'),
       ('5483d754-540c-4acf-99cb-cc32653f256d', 'video from third channel name', '2022-11-2', 3, '00:13:10',
        'best video from channel thr');

insert into users_subscriptions (username, channel_id)
values ('NishEsI', 2),
       ('NishEsI', 3),
       ('MEDICHI', 1),
       ('MEDICHI', 3),
       ('KishMishKuraga', 1),
       ('KishMishKuraga', 2);

insert into viewing (username, video_uuid, reaction)
values ('NishEsI', '01eca157-f7d7-43d8-9228-587804d6434e', 1),
       ('NishEsI', '5483d754-540c-4acf-99cb-cc32653f256d', 0),
       ('MEDICHI', 'd1ee017c-e45c-43c3-ad09-299509a504a3', -1),
       ('MEDICHI', '5483d754-540c-4acf-99cb-cc32653f256d', 0),
       ('KishMishKuraga', 'd1ee017c-e45c-43c3-ad09-299509a504a3', -1),
       ('KishMishKuraga', '01eca157-f7d7-43d8-9228-587804d6434e', 1);


update users set channel_id = 1 where username = 'NishEsI';
update users set channel_id = 2 where username = 'MEDICHI';
update users set channel_id = 3 where username = 'KishMishKuraga';
