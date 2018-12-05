insert into contact(contact_id, first_name, last_name, full_name, address, email, mobile)
values	(10, 'Tom', 'WHITE', 'Tom WHITE', 'Sion 1950', 'tom@gmail.com', '+41 78 555 55 55');

insert into skill(skill_id, name, level)
values	(1, 'Singing', 'GOOD');

insert into contact_skill(contact_id, skill_id)
values  (10, 1);