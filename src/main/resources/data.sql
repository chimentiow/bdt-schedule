-- Керівники секцій
INSERT INTO leader (last_name, first_name, middle_name, phone, specialization, hire_date) VALUES ('Ковальчук', 'Олена', 'Петрівна', '+380671112233', 'Хореограф', DATE '2015-09-01');
INSERT INTO leader (last_name, first_name, middle_name, phone, specialization, hire_date) VALUES ('Мельник', 'Андрій', 'Васильович', '+380502223344', 'Викладач гри на гітарі', DATE '2018-09-01');
INSERT INTO leader (last_name, first_name, middle_name, phone, specialization, hire_date) VALUES ('Шевченко', 'Ірина', 'Миколаївна', '+380933334455', 'Художник-педагог', DATE '2012-09-03');
INSERT INTO leader (last_name, first_name, middle_name, phone, specialization, hire_date) VALUES ('Бондаренко', 'Сергій', 'Олегович', '+380974445566', 'Інженер-педагог (робототехніка)', DATE '2020-09-01');
INSERT INTO leader (last_name, first_name, middle_name, phone, specialization, hire_date) VALUES ('Ткаченко', 'Наталія', 'Іванівна', '+380665556677', 'Керівник вокальної студії', DATE '2016-09-01');
INSERT INTO leader (last_name, first_name, middle_name, phone, specialization, hire_date) VALUES ('Кравець', 'Дмитро', 'Юрійович', '+380686667788', 'Тренер з шахів', DATE '2019-02-01');
INSERT INTO leader (last_name, first_name, middle_name, phone, specialization, hire_date) VALUES ('Литвиненко', 'Оксана', 'Сергіївна', '+380637778899', 'Майстер декоративного розпису', DATE '2014-09-01');
INSERT INTO leader (last_name, first_name, middle_name, phone, specialization, hire_date) VALUES ('Савченко', 'Віктор', 'Анатолійович', '+380958889900', 'Тренер з карате', DATE '2021-09-01');

-- Секції (гуртки)
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Ансамбль народного танцю «Барвінок»', 'Хореографічна', 1, 7, 14, 15, 450.00);
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Студія сучасного танцю «Рух»', 'Хореографічна', 1, 10, 17, 12, 500.00);
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Гітарна студія «Акорд»', 'Музична', 2, 9, 17, 8, 600.00);
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Вокальна студія «Соловейко»', 'Музична', 5, 6, 12, 12, 400.00);
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Образотворча студія «Палітра»', 'Художня', 3, 6, 14, 12, 350.00);
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Гурток розпису «Петриківка»', 'Художня', 7, 8, 15, 10, 380.00);
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Гурток робототехніки «RoboKids»', 'Технічна', 4, 9, 15, 10, 700.00);
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Гурток 3D-моделювання', 'Технічна', 4, 12, 17, 8, 650.00);
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Шаховий клуб «Гамбіт»', 'Спортивна', 6, 6, 17, 16, 300.00);
INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) VALUES ('Секція карате «Сокіл»', 'Спортивна', 8, 7, 16, 15, 450.00);

-- Приміщення
INSERT INTO room (room_number, room_type, capacity) VALUES ('101', 'Навчальний клас', 16);
INSERT INTO room (room_number, room_type, capacity) VALUES ('102', 'Навчальний клас', 12);
INSERT INTO room (room_number, room_type, capacity) VALUES ('105', 'Майстерня', 10);
INSERT INTO room (room_number, room_type, capacity) VALUES ('201', 'Музичний клас', 10);
INSERT INTO room (room_number, room_type, capacity) VALUES ('202', 'Музичний клас', 14);
INSERT INTO room (room_number, room_type, capacity) VALUES ('204', 'Комп’ютерний клас', 10);
INSERT INTO room (room_number, room_type, capacity) VALUES ('ТЗ-1', 'Танцювальний зал', 20);
INSERT INTO room (room_number, room_type, capacity) VALUES ('СЗ-1', 'Спортивний зал', 25);

-- Розклад занять (1 = понеділок ... 7 = неділя)
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (1, 7, 1, TIME '15:00:00', TIME '16:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (1, 7, 3, TIME '15:00:00', TIME '16:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (2, 7, 1, TIME '17:00:00', TIME '18:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (2, 7, 4, TIME '17:00:00', TIME '18:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (3, 4, 2, TIME '16:00:00', TIME '17:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (3, 4, 5, TIME '16:00:00', TIME '17:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (4, 5, 2, TIME '15:00:00', TIME '16:00:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (4, 5, 4, TIME '15:00:00', TIME '16:00:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (5, 1, 1, TIME '14:00:00', TIME '15:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (5, 1, 3, TIME '14:00:00', TIME '15:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (6, 3, 2, TIME '14:00:00', TIME '15:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (6, 3, 5, TIME '14:00:00', TIME '15:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (7, 6, 3, TIME '16:00:00', TIME '17:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (7, 6, 6, TIME '10:00:00', TIME '11:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (8, 6, 3, TIME '18:00:00', TIME '19:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (8, 6, 6, TIME '12:00:00', TIME '13:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (9, 1, 2, TIME '17:00:00', TIME '18:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (9, 1, 4, TIME '17:00:00', TIME '18:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (10, 8, 1, TIME '18:00:00', TIME '19:30:00');
INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) VALUES (10, 8, 5, TIME '18:00:00', TIME '19:30:00');

-- Учасники (діти)
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Бабенко', 'Ярослав', DATE '2012-05-15', 'Бабенко Людмила Вікторівна', '+380938411144', 'Пільгова');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Захарченко', 'Яна', DATE '2019-02-14', 'Захарченко Світлана Олегівна', '+380666747766', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Тимошенко', 'Христина', DATE '2013-10-16', 'Тимошенко Світлана Олегівна', '+380974970660', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Білоус', 'Ярослав', DATE '2015-09-07', 'Білоус Галина Степанівна', '+380974161034', 'Багатодітна');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Антоненко', 'Мілана', DATE '2012-06-09', 'Антоненко Світлана Олегівна', '+380500348423', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Вовк', 'Марк', DATE '2016-06-02', 'Вовк Марина Юріївна', '+380632835763', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Науменко', 'Марк', DATE '2013-01-08', 'Науменко Ірина Андріївна', '+380975141230', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Головко', 'Злата', DATE '2012-09-13', 'Головко Наталія Петрівна', '+380935866273', 'Багатодітна');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Федоренко', 'Іван', DATE '2017-04-21', 'Федоренко Сергій Миколайович', '+380668096747', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Гончаренко', 'Єва', DATE '2020-02-28', 'Гончаренко Наталія Петрівна', '+380672104026', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Карпенко', 'Ірина', DATE '2013-08-23', 'Карпенко Ольга Миколаївна', '+380674349808', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Усенко', 'Анна', DATE '2015-02-13', 'Усенко Ольга Миколаївна', '+380935042494', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Дорошенко', 'Тимофій', DATE '2020-02-15', 'Дорошенко Ірина Андріївна', '+380666463067', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Костенко', 'Дмитро', DATE '2017-04-09', 'Костенко Олег Петрович', '+380501780039', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Коваленко', 'Захар', DATE '2012-12-03', 'Коваленко Світлана Олегівна', '+380635736195', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Вакуленко', 'Данило', DATE '2014-05-02', 'Вакуленко Людмила Вікторівна', '+380504748020', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Павленко', 'Ярослав', DATE '2015-06-22', 'Павленко Василь Іванович', '+380500381293', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Олійник', 'Поліна', DATE '2010-10-26', 'Олійник Юлія Сергіївна', '+380508558610', 'Багатодітна');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Кравченко', 'Тарас', DATE '2017-04-04', 'Кравченко Тетяна Василівна', '+380974971617', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Бойко', 'Артем', DATE '2012-04-02', 'Бойко Тетяна Василівна', '+380668963812', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Демченко', 'Ярослав', DATE '2018-12-03', 'Демченко Роман Андрійович', '+380503127875', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Марченко', 'Єва', DATE '2019-06-28', 'Марченко Людмила Вікторівна', '+380971508750', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Власенко', 'Назар', DATE '2016-06-17', 'Власенко Світлана Олегівна', '+380936021657', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Левченко', 'Захар', DATE '2018-07-10', 'Левченко Ольга Миколаївна', '+380502143803', 'Пільгова');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Грищенко', 'Дарина', DATE '2016-04-24', 'Грищенко Ольга Миколаївна', '+380674833523', 'Багатодітна');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Поліщук', 'Анастасія', DATE '2019-07-21', 'Поліщук Людмила Вікторівна', '+380973072734', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Семенко', 'Назар', DATE '2011-11-10', 'Семенко Ігор Володимирович', '+380636078565', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Максименко', 'Марія', DATE '2009-06-03', 'Максименко Тетяна Василівна', '+380974776606', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Ющенко', 'Артем', DATE '2015-10-19', 'Ющенко Галина Степанівна', '+380976755697', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Тарасенко', 'Злата', DATE '2014-03-24', 'Тарасенко Олег Петрович', '+380977037002', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Юрченко', 'Іван', DATE '2010-12-06', 'Юрченко Тетяна Василівна', '+380932055183', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Пономаренко', 'Марія', DATE '2012-01-25', 'Пономаренко Ірина Андріївна', '+380500559969', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Романюк', 'Ярослав', DATE '2020-01-01', 'Романюк Ігор Володимирович', '+380509902145', 'Багатодітна');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Кузьменко', 'Назар', DATE '2013-06-06', 'Кузьменко Ольга Миколаївна', '+380976373829', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Яковенко', 'Христина', DATE '2017-11-13', 'Яковенко Світлана Олегівна', '+380509865468', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Петренко', 'Софія', DATE '2011-04-01', 'Петренко Оксана Іванівна', '+380931825976', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Дяченко', 'Марк', DATE '2013-03-26', 'Дяченко Ігор Володимирович', '+380973415194', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Руденко', 'Матвій', DATE '2012-12-20', 'Руденко Наталія Петрівна', '+380971665151', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Ткачук', 'Матвій', DATE '2017-06-16', 'Ткачук Оксана Іванівна', '+380938484825', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Зінченко', 'Матвій', DATE '2016-02-04', 'Зінченко Ольга Миколаївна', '+380674645221', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Сидоренко', 'Іван', DATE '2017-04-23', 'Сидоренко Галина Степанівна', '+380974665118', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Савчук', 'Катерина', DATE '2014-07-15', 'Савчук Марина Юріївна', '+380634594766', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Прокопенко', 'Поліна', DATE '2017-04-15', 'Прокопенко Ірина Андріївна', '+380638974207', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Онищенко', 'Богдан', DATE '2020-01-24', 'Онищенко Наталія Петрівна', '+380662183739', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Шевчук', 'Максим', DATE '2013-03-01', 'Шевчук Ірина Андріївна', '+380979121732', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Остапчук', 'Христина', DATE '2014-06-09', 'Остапчук Галина Степанівна', '+380506434588', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Мороз', 'Софія', DATE '2016-10-10', 'Мороз Оксана Іванівна', '+380638077733', 'Багатодітна');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Єременко', 'Анна', DATE '2017-07-10', 'Єременко Ольга Миколаївна', '+380970006007', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Гаврилюк', 'Максим', DATE '2011-08-05', 'Гаврилюк Світлана Олегівна', '+380634815341', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Іваненко', 'Поліна', DATE '2012-01-10', 'Іваненко Марина Юріївна', '+380971984102', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Харченко', 'Софія', DATE '2012-12-14', 'Харченко Роман Андрійович', '+380509261991', 'Багатодітна');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Мартинюк', 'Богдан', DATE '2014-04-05', 'Мартинюк Тетяна Василівна', '+380503925497', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Чорновол', 'Софія', DATE '2013-05-24', 'Чорновол Ірина Андріївна', '+380977505380', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Гуменюк', 'Соломія', DATE '2019-09-13', 'Гуменюк Ольга Миколаївна', '+380500033118', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Нестеренко', 'Дмитро', DATE '2016-04-11', 'Нестеренко Тетяна Василівна', '+380639717711', 'Немає');
INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) VALUES ('Лисенко', 'Анна', DATE '2018-01-28', 'Лисенко Оксана Іванівна', '+380976927906', 'Немає');

-- Записи учасників до секцій
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 19, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 47, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 43, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 52, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 39, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 11, DATE '2026-10-11', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 4, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 26, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 53, DATE '2026-09-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (1, 3, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (2, 37, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (2, 18, DATE '2026-10-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (2, 8, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (2, 55, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (2, 34, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (2, 31, DATE '2026-09-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (2, 6, DATE '2026-09-02', 'Вибув');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (2, 23, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (3, 38, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (3, 1, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (3, 49, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (3, 12, DATE '2026-09-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (3, 48, DATE '2026-09-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (3, 5, DATE '2026-09-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (4, 24, DATE '2026-09-04', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (4, 41, DATE '2026-09-06', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (4, 30, DATE '2026-09-06', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (4, 40, DATE '2026-09-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (4, 9, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (4, 33, DATE '2026-09-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (4, 25, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (4, 46, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (4, 29, DATE '2026-09-06', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 42, DATE '2026-09-01', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 50, DATE '2026-10-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 44, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 27, DATE '2026-09-04', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 16, DATE '2026-09-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 22, DATE '2026-10-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 35, DATE '2026-09-04', 'Вибув');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 17, DATE '2026-09-04', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 2, DATE '2026-09-01', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (6, 14, DATE '2026-09-06', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (6, 15, DATE '2026-09-01', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (6, 36, DATE '2026-10-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (6, 51, DATE '2026-09-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (6, 7, DATE '2026-09-01', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (6, 20, DATE '2026-10-01', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (6, 32, DATE '2026-09-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (7, 45, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (7, 23, DATE '2026-09-01', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (7, 1, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (7, 48, DATE '2026-10-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (7, 11, DATE '2026-09-04', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (7, 8, DATE '2026-09-06', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (7, 12, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (7, 32, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (8, 28, DATE '2026-09-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (8, 5, DATE '2026-09-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (8, 3, DATE '2026-09-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (8, 16, DATE '2026-09-04', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (8, 52, DATE '2026-09-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 56, DATE '2026-09-01', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 13, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 21, DATE '2026-09-04', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 54, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 10, DATE '2026-09-04', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 36, DATE '2026-09-08', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 29, DATE '2026-09-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 40, DATE '2026-09-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 47, DATE '2026-09-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (9, 28, DATE '2026-09-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (10, 35, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (10, 49, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (10, 37, DATE '2026-09-02', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (10, 27, DATE '2026-10-09', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (10, 55, DATE '2026-10-07', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (10, 53, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (10, 4, DATE '2026-09-01', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (10, 42, DATE '2026-09-03', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (10, 24, DATE '2026-09-01', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (2, 7, DATE '2026-10-05', 'Активний');
INSERT INTO enrollment (section_id, participant_id, enroll_date, status) VALUES (5, 6, DATE '2026-10-05', 'Активний');

-- Фактичні заняття
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-09-01', TIME '16:00:00', TIME '17:30:00', 'Постановка дихання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-09-01', TIME '15:00:00', TIME '16:00:00', 'Постановка дихання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-09-01', TIME '14:00:00', TIME '15:30:00', 'Кольорове коло та змішування фарб');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-09-01', TIME '17:00:00', TIME '18:30:00', 'Загальна фізична підготовка');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 2, 7, 1, DATE '2026-09-02', TIME '15:00:00', TIME '16:30:00', 'Розминка та партерна гімнастика');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 10, 1, 3, DATE '2026-09-02', TIME '14:00:00', TIME '15:30:00', 'Кольорове коло та змішування фарб');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 13, 6, 4, DATE '2026-09-02', TIME '16:00:00', TIME '17:30:00', 'Знайомство з контролером');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 15, 6, 4, DATE '2026-09-02', TIME '18:00:00', TIME '19:30:00', 'Знайомство з контролером');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-09-03', TIME '17:00:00', TIME '18:30:00', 'Розминка та партерна гімнастика');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-09-03', TIME '15:00:00', TIME '16:00:00', 'Вправи на інтонування');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-09-03', TIME '17:00:00', TIME '18:30:00', 'Відпрацювання базової техніки');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-09-04', TIME '16:00:00', TIME '17:30:00', 'Вправи на інтонування');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-09-04', TIME '14:00:00', TIME '15:30:00', 'Натюрморт з овочів');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 20, 8, 8, DATE '2026-09-04', TIME '18:00:00', TIME '19:30:00', 'Загальна фізична підготовка');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-09-05', TIME '10:00:00', TIME '11:30:00', 'Датчики та сенсори');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-09-05', TIME '12:00:00', TIME '13:30:00', 'Датчики та сенсори');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 1, 7, 1, DATE '2026-09-07', TIME '15:00:00', TIME '16:30:00', 'Елементи народного танцю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 3, 7, 1, DATE '2026-09-07', TIME '17:00:00', TIME '18:30:00', 'Елементи народного танцю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 9, 1, 3, DATE '2026-09-07', TIME '14:00:00', TIME '15:30:00', 'Натюрморт з овочів');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 19, 8, 8, DATE '2026-09-07', TIME '18:00:00', TIME '19:30:00', 'Відпрацювання базової техніки');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-09-08', TIME '16:00:00', TIME '17:30:00', 'Вивчення нового твору');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-09-08', TIME '15:00:00', TIME '16:00:00', 'Вивчення нового твору');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-09-08', TIME '14:00:00', TIME '15:30:00', 'Осінній пейзаж');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-09-08', TIME '17:00:00', TIME '18:30:00', 'Тактичні прийоми');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 2, 7, 1, DATE '2026-09-09', TIME '15:00:00', TIME '16:30:00', 'Вивчення комбінації «Гопак»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 10, 1, 3, DATE '2026-09-09', TIME '14:00:00', TIME '15:30:00', 'Осінній пейзаж');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 13, 6, 4, DATE '2026-09-09', TIME '16:00:00', TIME '17:30:00', 'Програмування руху робота');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 15, 6, 4, DATE '2026-09-09', TIME '18:00:00', TIME '19:30:00', 'Програмування руху робота');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-09-10', TIME '17:00:00', TIME '18:30:00', 'Вивчення комбінації «Гопак»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-09-10', TIME '15:00:00', TIME '16:00:00', 'Робота над ритмом');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-09-10', TIME '17:00:00', TIME '18:30:00', 'Розбір партій / спаринг');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-09-11', TIME '16:00:00', TIME '17:30:00', 'Робота над ритмом');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-09-11', TIME '14:00:00', TIME '15:30:00', 'Елементи петриківського розпису');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 20, 8, 8, DATE '2026-09-11', TIME '18:00:00', TIME '19:30:00', 'Тактичні прийоми');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-09-12', TIME '10:00:00', TIME '11:30:00', 'Збирання моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-09-12', TIME '12:00:00', TIME '13:30:00', 'Збирання моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 1, 7, 1, DATE '2026-09-14', TIME '15:00:00', TIME '16:30:00', 'Робота над синхронністю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 3, 7, 1, DATE '2026-09-14', TIME '17:00:00', TIME '18:30:00', 'Робота над синхронністю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 9, 1, 3, DATE '2026-09-14', TIME '14:00:00', TIME '15:30:00', 'Елементи петриківського розпису');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 19, 8, 8, DATE '2026-09-14', TIME '18:00:00', TIME '19:30:00', 'Розбір партій / спаринг');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-09-15', TIME '16:00:00', TIME '17:30:00', 'Ансамблеве виконання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-09-15', TIME '15:00:00', TIME '16:00:00', 'Ансамблеве виконання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-09-15', TIME '14:00:00', TIME '15:30:00', 'Композиція «Квітковий орнамент»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-09-15', TIME '17:00:00', TIME '18:30:00', 'Навчальний турнір');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 2, 7, 1, DATE '2026-09-16', TIME '15:00:00', TIME '16:30:00', 'Постановка концертного номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 10, 1, 3, DATE '2026-09-16', TIME '14:00:00', TIME '15:30:00', 'Композиція «Квітковий орнамент»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 13, 6, 4, DATE '2026-09-16', TIME '16:00:00', TIME '17:30:00', 'Побудова 3D-моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 15, 6, 4, DATE '2026-09-16', TIME '18:00:00', TIME '19:30:00', 'Побудова 3D-моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-09-17', TIME '17:00:00', TIME '18:30:00', 'Постановка концертного номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-09-17', TIME '15:00:00', TIME '16:00:00', 'Підготовка до концерту');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-09-17', TIME '17:00:00', TIME '18:30:00', 'Контрольне заняття');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-09-18', TIME '16:00:00', TIME '17:30:00', 'Підготовка до концерту');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-09-18', TIME '14:00:00', TIME '15:30:00', 'Робота над виставковою роботою');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 20, 8, 8, DATE '2026-09-18', TIME '18:00:00', TIME '19:30:00', 'Навчальний турнір');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-09-19', TIME '10:00:00', TIME '11:30:00', 'Підготовка проєкту до конкурсу');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-09-19', TIME '12:00:00', TIME '13:30:00', 'Підготовка проєкту до конкурсу');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 1, 7, 1, DATE '2026-09-21', TIME '15:00:00', TIME '16:30:00', 'Репетиція номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 3, 7, 1, DATE '2026-09-21', TIME '17:00:00', TIME '18:30:00', 'Репетиція номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 9, 1, 3, DATE '2026-09-21', TIME '14:00:00', TIME '15:30:00', 'Робота над виставковою роботою');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 19, 8, 8, DATE '2026-09-21', TIME '18:00:00', TIME '19:30:00', 'Контрольне заняття');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-09-22', TIME '16:00:00', TIME '17:30:00', 'Постановка дихання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-09-22', TIME '15:00:00', TIME '16:00:00', 'Постановка дихання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-09-22', TIME '14:00:00', TIME '15:30:00', 'Кольорове коло та змішування фарб');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-09-22', TIME '17:00:00', TIME '18:30:00', 'Загальна фізична підготовка');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 2, 7, 1, DATE '2026-09-23', TIME '15:00:00', TIME '16:30:00', 'Розминка та партерна гімнастика');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 10, 1, 3, DATE '2026-09-23', TIME '14:00:00', TIME '15:30:00', 'Кольорове коло та змішування фарб');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 13, 6, 4, DATE '2026-09-23', TIME '16:00:00', TIME '17:30:00', 'Знайомство з контролером');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 15, 6, 4, DATE '2026-09-23', TIME '18:00:00', TIME '19:30:00', 'Знайомство з контролером');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-09-24', TIME '17:00:00', TIME '18:30:00', 'Розминка та партерна гімнастика');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-09-24', TIME '15:00:00', TIME '16:00:00', 'Вправи на інтонування');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-09-24', TIME '17:00:00', TIME '18:30:00', 'Відпрацювання базової техніки');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-09-25', TIME '16:00:00', TIME '17:30:00', 'Вправи на інтонування');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-09-25', TIME '14:00:00', TIME '15:30:00', 'Натюрморт з овочів');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 20, 8, 8, DATE '2026-09-25', TIME '18:00:00', TIME '19:30:00', 'Загальна фізична підготовка');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-09-26', TIME '10:00:00', TIME '11:30:00', 'Датчики та сенсори');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-09-26', TIME '12:00:00', TIME '13:30:00', 'Датчики та сенсори');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 1, 7, 1, DATE '2026-09-28', TIME '15:00:00', TIME '16:30:00', 'Елементи народного танцю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 3, 7, 1, DATE '2026-09-28', TIME '17:00:00', TIME '18:30:00', 'Елементи народного танцю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 9, 1, 3, DATE '2026-09-28', TIME '14:00:00', TIME '15:30:00', 'Натюрморт з овочів');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 19, 8, 8, DATE '2026-09-28', TIME '18:00:00', TIME '19:30:00', 'Відпрацювання базової техніки');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-09-29', TIME '16:00:00', TIME '17:30:00', 'Вивчення нового твору');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-09-29', TIME '15:00:00', TIME '16:00:00', 'Вивчення нового твору');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-09-29', TIME '14:00:00', TIME '15:30:00', 'Осінній пейзаж');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-09-29', TIME '17:00:00', TIME '18:30:00', 'Тактичні прийоми');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 2, 7, 1, DATE '2026-09-30', TIME '15:00:00', TIME '16:30:00', 'Вивчення комбінації «Гопак»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 10, 1, 3, DATE '2026-09-30', TIME '14:00:00', TIME '15:30:00', 'Осінній пейзаж');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 13, 6, 4, DATE '2026-09-30', TIME '16:00:00', TIME '17:30:00', 'Програмування руху робота');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 15, 6, 4, DATE '2026-09-30', TIME '18:00:00', TIME '19:30:00', 'Програмування руху робота');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-10-01', TIME '17:00:00', TIME '18:30:00', 'Вивчення комбінації «Гопак»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-10-01', TIME '15:00:00', TIME '16:00:00', 'Робота над ритмом');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-10-01', TIME '17:00:00', TIME '18:30:00', 'Розбір партій / спаринг');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-10-02', TIME '16:00:00', TIME '17:30:00', 'Робота над ритмом');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-10-02', TIME '14:00:00', TIME '15:30:00', 'Елементи петриківського розпису');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 20, 8, 8, DATE '2026-10-02', TIME '18:00:00', TIME '19:30:00', 'Тактичні прийоми');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-10-03', TIME '10:00:00', TIME '11:30:00', 'Збирання моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-10-03', TIME '12:00:00', TIME '13:30:00', 'Збирання моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 1, 7, 1, DATE '2026-10-05', TIME '15:00:00', TIME '16:30:00', 'Робота над синхронністю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 3, 7, 1, DATE '2026-10-05', TIME '17:00:00', TIME '18:30:00', 'Робота над синхронністю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 9, 1, 3, DATE '2026-10-05', TIME '14:00:00', TIME '15:30:00', 'Елементи петриківського розпису');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 19, 8, 8, DATE '2026-10-05', TIME '18:00:00', TIME '19:30:00', 'Розбір партій / спаринг');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-10-06', TIME '16:00:00', TIME '17:30:00', 'Ансамблеве виконання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-10-06', TIME '15:00:00', TIME '16:00:00', 'Ансамблеве виконання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-10-06', TIME '14:00:00', TIME '15:30:00', 'Композиція «Квітковий орнамент»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-10-06', TIME '17:00:00', TIME '18:30:00', 'Навчальний турнір');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 2, 7, 1, DATE '2026-10-07', TIME '15:00:00', TIME '16:30:00', 'Постановка концертного номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 10, 1, 3, DATE '2026-10-07', TIME '14:00:00', TIME '15:30:00', 'Композиція «Квітковий орнамент»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 13, 6, 4, DATE '2026-10-07', TIME '16:00:00', TIME '17:30:00', 'Побудова 3D-моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 15, 6, 4, DATE '2026-10-07', TIME '18:00:00', TIME '19:30:00', 'Побудова 3D-моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-10-08', TIME '17:00:00', TIME '18:30:00', 'Постановка концертного номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-10-08', TIME '15:00:00', TIME '16:00:00', 'Підготовка до концерту');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-10-08', TIME '17:00:00', TIME '18:30:00', 'Контрольне заняття');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-10-09', TIME '16:00:00', TIME '17:30:00', 'Підготовка до концерту');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-10-09', TIME '14:00:00', TIME '15:30:00', 'Робота над виставковою роботою');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 20, 8, 8, DATE '2026-10-09', TIME '18:00:00', TIME '19:30:00', 'Навчальний турнір');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-10-10', TIME '10:00:00', TIME '11:30:00', 'Підготовка проєкту до конкурсу');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-10-10', TIME '12:00:00', TIME '13:30:00', 'Підготовка проєкту до конкурсу');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 1, 7, 1, DATE '2026-10-12', TIME '15:00:00', TIME '16:30:00', 'Репетиція номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 3, 7, 1, DATE '2026-10-12', TIME '17:00:00', TIME '18:30:00', 'Репетиція номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 9, 1, 3, DATE '2026-10-12', TIME '14:00:00', TIME '15:30:00', 'Робота над виставковою роботою');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 19, 8, 8, DATE '2026-10-12', TIME '18:00:00', TIME '19:30:00', 'Контрольне заняття');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-10-13', TIME '16:00:00', TIME '17:30:00', 'Постановка дихання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-10-13', TIME '15:00:00', TIME '16:00:00', 'Постановка дихання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-10-13', TIME '14:00:00', TIME '15:30:00', 'Кольорове коло та змішування фарб');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-10-13', TIME '17:00:00', TIME '18:30:00', 'Загальна фізична підготовка');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-10-15', TIME '17:00:00', TIME '18:30:00', 'Розминка та партерна гімнастика');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-10-15', TIME '15:00:00', TIME '16:00:00', 'Вправи на інтонування');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-10-15', TIME '17:00:00', TIME '18:30:00', 'Відпрацювання базової техніки');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-10-16', TIME '16:00:00', TIME '17:30:00', 'Вправи на інтонування');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-10-16', TIME '14:00:00', TIME '15:30:00', 'Натюрморт з овочів');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 20, 8, 8, DATE '2026-10-16', TIME '18:00:00', TIME '19:30:00', 'Загальна фізична підготовка');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-10-17', TIME '10:00:00', TIME '11:30:00', 'Знайомство з контролером');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-10-17', TIME '12:00:00', TIME '13:30:00', 'Знайомство з контролером');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 1, 7, 1, DATE '2026-10-19', TIME '15:00:00', TIME '16:30:00', 'Розминка та партерна гімнастика');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 3, 7, 1, DATE '2026-10-19', TIME '17:00:00', TIME '18:30:00', 'Елементи народного танцю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 9, 1, 3, DATE '2026-10-19', TIME '14:00:00', TIME '15:30:00', 'Кольорове коло та змішування фарб');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 19, 8, 8, DATE '2026-10-19', TIME '18:00:00', TIME '19:30:00', 'Відпрацювання базової техніки');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-10-20', TIME '16:00:00', TIME '17:30:00', 'Вивчення нового твору');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-10-20', TIME '15:00:00', TIME '16:00:00', 'Вивчення нового твору');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-10-20', TIME '14:00:00', TIME '15:30:00', 'Осінній пейзаж');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-10-20', TIME '17:00:00', TIME '18:30:00', 'Тактичні прийоми');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 2, 7, 1, DATE '2026-10-21', TIME '15:00:00', TIME '16:30:00', 'Елементи народного танцю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 10, 1, 3, DATE '2026-10-21', TIME '14:00:00', TIME '15:30:00', 'Натюрморт з овочів');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 13, 6, 4, DATE '2026-10-21', TIME '16:00:00', TIME '17:30:00', 'Датчики та сенсори');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 15, 6, 4, DATE '2026-10-21', TIME '18:00:00', TIME '19:30:00', 'Датчики та сенсори');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-10-22', TIME '17:00:00', TIME '18:30:00', 'Вивчення комбінації «Гопак»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-10-22', TIME '15:00:00', TIME '16:00:00', 'Робота над ритмом');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-10-22', TIME '17:00:00', TIME '18:30:00', 'Розбір партій / спаринг');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-10-23', TIME '16:00:00', TIME '17:30:00', 'Робота над ритмом');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-10-23', TIME '14:00:00', TIME '15:30:00', 'Елементи петриківського розпису');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 20, 8, 8, DATE '2026-10-23', TIME '18:00:00', TIME '19:30:00', 'Тактичні прийоми');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-10-24', TIME '10:00:00', TIME '11:30:00', 'Програмування руху робота');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-10-24', TIME '12:00:00', TIME '13:30:00', 'Програмування руху робота');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 1, 7, 1, DATE '2026-10-26', TIME '15:00:00', TIME '16:30:00', 'Вивчення комбінації «Гопак»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 3, 7, 1, DATE '2026-10-26', TIME '17:00:00', TIME '18:30:00', 'Робота над синхронністю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 9, 1, 3, DATE '2026-10-26', TIME '14:00:00', TIME '15:30:00', 'Осінній пейзаж');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 19, 8, 8, DATE '2026-10-26', TIME '18:00:00', TIME '19:30:00', 'Розбір партій / спаринг');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-10-27', TIME '16:00:00', TIME '17:30:00', 'Ансамблеве виконання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-10-27', TIME '15:00:00', TIME '16:00:00', 'Ансамблеве виконання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-10-27', TIME '14:00:00', TIME '15:30:00', 'Композиція «Квітковий орнамент»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-10-27', TIME '17:00:00', TIME '18:30:00', 'Навчальний турнір');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 2, 7, 1, DATE '2026-10-28', TIME '15:00:00', TIME '16:30:00', 'Робота над синхронністю');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 10, 1, 3, DATE '2026-10-28', TIME '14:00:00', TIME '15:30:00', 'Елементи петриківського розпису');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 13, 6, 4, DATE '2026-10-28', TIME '16:00:00', TIME '17:30:00', 'Збирання моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 15, 6, 4, DATE '2026-10-28', TIME '18:00:00', TIME '19:30:00', 'Збирання моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-10-29', TIME '17:00:00', TIME '18:30:00', 'Постановка концертного номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-10-29', TIME '15:00:00', TIME '16:00:00', 'Підготовка до концерту');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-10-29', TIME '17:00:00', TIME '18:30:00', 'Контрольне заняття');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-10-30', TIME '16:00:00', TIME '17:30:00', 'Підготовка до концерту');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-10-30', TIME '14:00:00', TIME '15:30:00', 'Робота над виставковою роботою');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (10, 20, 8, 8, DATE '2026-10-30', TIME '18:00:00', TIME '19:30:00', 'Навчальний турнір');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-10-31', TIME '10:00:00', TIME '11:30:00', 'Побудова 3D-моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-10-31', TIME '12:00:00', TIME '13:30:00', 'Побудова 3D-моделі');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 1, 7, 1, DATE '2026-11-02', TIME '15:00:00', TIME '16:30:00', 'Постановка концертного номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 3, 7, 1, DATE '2026-11-02', TIME '17:00:00', TIME '18:30:00', 'Репетиція номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 9, 1, 3, DATE '2026-11-02', TIME '14:00:00', TIME '15:30:00', 'Композиція «Квітковий орнамент»');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 5, 4, 2, DATE '2026-11-03', TIME '16:00:00', TIME '17:30:00', 'Постановка дихання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 7, 5, 5, DATE '2026-11-03', TIME '15:00:00', TIME '16:00:00', 'Постановка дихання');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 11, 3, 7, DATE '2026-11-03', TIME '14:00:00', TIME '15:30:00', 'Кольорове коло та змішування фарб');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 17, 1, 6, DATE '2026-11-03', TIME '17:00:00', TIME '18:30:00', 'Загальна фізична підготовка');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (1, 2, 7, 1, DATE '2026-11-04', TIME '15:00:00', TIME '16:30:00', 'Репетиція номера');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (5, 10, 1, 3, DATE '2026-11-04', TIME '14:00:00', TIME '15:30:00', 'Робота над виставковою роботою');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 13, 6, 4, DATE '2026-11-04', TIME '16:00:00', TIME '17:30:00', 'Підготовка проєкту до конкурсу');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 15, 6, 4, DATE '2026-11-04', TIME '18:00:00', TIME '19:30:00', 'Підготовка проєкту до конкурсу');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (2, 4, 7, 1, DATE '2026-11-05', TIME '17:00:00', TIME '18:30:00', 'Розминка та партерна гімнастика');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (4, 8, 5, 5, DATE '2026-11-05', TIME '15:00:00', TIME '16:00:00', 'Вправи на інтонування');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (9, 18, 1, 6, DATE '2026-11-05', TIME '17:00:00', TIME '18:30:00', 'Відпрацювання базової техніки');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (3, 6, 4, 2, DATE '2026-11-06', TIME '16:00:00', TIME '17:30:00', 'Вправи на інтонування');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (6, 12, 3, 7, DATE '2026-11-06', TIME '14:00:00', TIME '15:30:00', 'Натюрморт з овочів');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (7, 14, 6, 4, DATE '2026-11-07', TIME '10:00:00', TIME '11:30:00', 'Знайомство з контролером');
INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, start_time, end_time, topic) VALUES (8, 16, 6, 4, DATE '2026-11-07', TIME '12:00:00', TIME '13:30:00', 'Знайомство з контролером');

-- Відвідування занять
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (3, 15, FALSE),
  (3, 7, TRUE),
  (4, 56, TRUE),
  (5, 53, TRUE),
  (6, 42, TRUE),
  (6, 16, TRUE),
  (6, 2, TRUE),
  (7, 23, TRUE),
  (8, 28, TRUE),
  (8, 5, TRUE),
  (8, 52, TRUE),
  (9, 37, TRUE),
  (9, 8, FALSE),
  (9, 6, TRUE),
  (9, 23, TRUE),
  (10, 40, TRUE),
  (11, 56, TRUE),
  (11, 28, FALSE),
  (12, 38, TRUE),
  (13, 15, TRUE),
  (13, 7, TRUE),
  (13, 32, TRUE),
  (14, 35, TRUE),
  (14, 49, TRUE),
  (14, 37, TRUE),
  (14, 53, TRUE),
  (14, 4, TRUE),
  (14, 42, TRUE),
  (14, 24, TRUE),
  (15, 23, TRUE),
  (15, 11, TRUE),
  (16, 28, TRUE),
  (16, 5, TRUE),
  (16, 3, TRUE),
  (16, 16, TRUE),
  (16, 52, TRUE),
  (17, 43, TRUE),
  (17, 52, TRUE),
  (17, 39, TRUE),
  (17, 26, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (17, 53, TRUE),
  (18, 37, TRUE),
  (18, 8, TRUE),
  (18, 55, TRUE),
  (18, 31, TRUE),
  (18, 6, FALSE),
  (18, 23, TRUE),
  (19, 42, TRUE),
  (19, 27, TRUE),
  (19, 16, TRUE),
  (19, 35, TRUE),
  (19, 17, TRUE),
  (19, 2, TRUE),
  (20, 35, TRUE),
  (20, 49, TRUE),
  (20, 37, TRUE),
  (20, 53, TRUE),
  (20, 4, TRUE),
  (20, 42, TRUE),
  (20, 24, TRUE),
  (21, 38, FALSE),
  (21, 1, TRUE),
  (21, 49, TRUE),
  (21, 12, TRUE),
  (21, 48, TRUE),
  (21, 5, FALSE),
  (22, 24, TRUE),
  (22, 41, TRUE),
  (22, 30, FALSE),
  (22, 40, TRUE),
  (22, 9, TRUE),
  (22, 33, FALSE),
  (22, 25, TRUE),
  (22, 46, TRUE),
  (22, 29, TRUE),
  (23, 14, TRUE),
  (23, 15, TRUE),
  (23, 51, TRUE),
  (23, 7, TRUE),
  (23, 32, FALSE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (24, 56, TRUE),
  (24, 13, TRUE),
  (24, 21, TRUE),
  (24, 54, FALSE),
  (24, 10, TRUE),
  (24, 36, FALSE),
  (24, 29, TRUE),
  (24, 40, TRUE),
  (24, 47, TRUE),
  (24, 28, TRUE),
  (25, 19, TRUE),
  (25, 47, TRUE),
  (25, 43, TRUE),
  (25, 52, TRUE),
  (25, 39, TRUE),
  (25, 4, TRUE),
  (25, 26, TRUE),
  (25, 53, TRUE),
  (25, 3, FALSE),
  (26, 42, TRUE),
  (26, 44, TRUE),
  (26, 27, TRUE),
  (26, 16, TRUE),
  (26, 35, FALSE),
  (26, 17, FALSE),
  (26, 2, TRUE),
  (27, 45, TRUE),
  (27, 23, TRUE),
  (27, 1, TRUE),
  (27, 11, TRUE),
  (27, 8, TRUE),
  (27, 12, TRUE),
  (27, 32, TRUE),
  (28, 28, TRUE),
  (28, 5, FALSE),
  (28, 3, FALSE),
  (28, 16, TRUE),
  (28, 52, TRUE),
  (29, 37, TRUE),
  (29, 8, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (29, 55, FALSE),
  (29, 34, TRUE),
  (29, 31, FALSE),
  (29, 6, TRUE),
  (29, 23, TRUE),
  (30, 24, TRUE),
  (30, 41, TRUE),
  (30, 30, TRUE),
  (30, 40, TRUE),
  (30, 9, TRUE),
  (30, 33, TRUE),
  (30, 25, TRUE),
  (30, 46, FALSE),
  (30, 29, TRUE),
  (31, 56, TRUE),
  (31, 13, TRUE),
  (31, 21, TRUE),
  (31, 54, TRUE),
  (31, 10, TRUE),
  (31, 36, TRUE),
  (31, 29, TRUE),
  (31, 40, TRUE),
  (31, 47, TRUE),
  (31, 28, TRUE),
  (32, 38, TRUE),
  (32, 1, TRUE),
  (32, 49, TRUE),
  (32, 12, TRUE),
  (32, 48, TRUE),
  (32, 5, TRUE),
  (33, 14, TRUE),
  (33, 15, FALSE),
  (33, 51, FALSE),
  (33, 7, FALSE),
  (33, 32, TRUE),
  (34, 35, TRUE),
  (34, 49, TRUE),
  (34, 37, TRUE),
  (34, 53, TRUE),
  (34, 4, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (34, 42, TRUE),
  (34, 24, TRUE),
  (35, 45, TRUE),
  (35, 23, FALSE),
  (35, 1, TRUE),
  (35, 11, TRUE),
  (35, 8, FALSE),
  (35, 12, TRUE),
  (35, 32, FALSE),
  (36, 28, TRUE),
  (36, 5, FALSE),
  (36, 3, TRUE),
  (36, 16, TRUE),
  (36, 52, TRUE),
  (37, 19, TRUE),
  (37, 47, TRUE),
  (37, 43, TRUE),
  (37, 52, FALSE),
  (37, 39, TRUE),
  (37, 4, TRUE),
  (37, 26, TRUE),
  (37, 53, TRUE),
  (37, 3, TRUE),
  (38, 37, TRUE),
  (38, 8, TRUE),
  (38, 55, TRUE),
  (38, 34, TRUE),
  (38, 31, TRUE),
  (38, 6, TRUE),
  (38, 23, TRUE),
  (39, 42, FALSE),
  (39, 44, TRUE),
  (39, 27, TRUE),
  (39, 16, FALSE),
  (39, 35, TRUE),
  (39, 17, TRUE),
  (39, 2, TRUE),
  (40, 35, TRUE),
  (40, 49, TRUE),
  (40, 37, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (40, 53, FALSE),
  (40, 4, TRUE),
  (40, 42, TRUE),
  (40, 24, FALSE),
  (41, 38, TRUE),
  (41, 1, TRUE),
  (41, 49, TRUE),
  (41, 12, TRUE),
  (41, 48, TRUE),
  (41, 5, TRUE),
  (42, 24, TRUE),
  (42, 41, TRUE),
  (42, 30, TRUE),
  (42, 40, TRUE),
  (42, 9, TRUE),
  (42, 33, TRUE),
  (42, 25, TRUE),
  (42, 46, TRUE),
  (42, 29, TRUE),
  (43, 14, TRUE),
  (43, 15, TRUE),
  (43, 51, TRUE),
  (43, 7, TRUE),
  (43, 32, TRUE),
  (44, 56, FALSE),
  (44, 13, TRUE),
  (44, 21, TRUE),
  (44, 54, TRUE),
  (44, 10, TRUE),
  (44, 36, TRUE),
  (44, 29, TRUE),
  (44, 40, TRUE),
  (44, 47, TRUE),
  (44, 28, TRUE),
  (45, 19, TRUE),
  (45, 47, TRUE),
  (45, 43, TRUE),
  (45, 52, FALSE),
  (45, 39, TRUE),
  (45, 4, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (45, 26, TRUE),
  (45, 53, TRUE),
  (45, 3, TRUE),
  (46, 42, TRUE),
  (46, 44, TRUE),
  (46, 27, TRUE),
  (46, 16, TRUE),
  (46, 35, TRUE),
  (46, 17, TRUE),
  (46, 2, TRUE),
  (47, 45, TRUE),
  (47, 23, TRUE),
  (47, 1, TRUE),
  (47, 11, TRUE),
  (47, 8, TRUE),
  (47, 12, TRUE),
  (47, 32, TRUE),
  (48, 28, TRUE),
  (48, 5, TRUE),
  (48, 3, TRUE),
  (48, 16, TRUE),
  (48, 52, TRUE),
  (49, 37, TRUE),
  (49, 8, TRUE),
  (49, 55, TRUE),
  (49, 34, TRUE),
  (49, 31, TRUE),
  (49, 6, TRUE),
  (49, 23, TRUE),
  (50, 24, TRUE),
  (50, 41, TRUE),
  (50, 30, TRUE),
  (50, 40, TRUE),
  (50, 9, TRUE),
  (50, 33, TRUE),
  (50, 25, FALSE),
  (50, 46, TRUE),
  (50, 29, TRUE),
  (51, 56, FALSE),
  (51, 13, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (51, 21, FALSE),
  (51, 54, TRUE),
  (51, 10, FALSE),
  (51, 36, TRUE),
  (51, 29, TRUE),
  (51, 40, TRUE),
  (51, 47, FALSE),
  (51, 28, FALSE),
  (52, 38, TRUE),
  (52, 1, TRUE),
  (52, 49, TRUE),
  (52, 12, FALSE),
  (52, 48, FALSE),
  (52, 5, TRUE),
  (53, 14, TRUE),
  (53, 15, TRUE),
  (53, 51, TRUE),
  (53, 7, TRUE),
  (53, 32, TRUE),
  (54, 35, TRUE),
  (54, 49, TRUE),
  (54, 37, TRUE),
  (54, 53, TRUE),
  (54, 4, TRUE),
  (54, 42, TRUE),
  (54, 24, TRUE),
  (55, 45, TRUE),
  (55, 23, TRUE),
  (55, 1, TRUE),
  (55, 11, TRUE),
  (55, 8, TRUE),
  (55, 12, TRUE),
  (55, 32, TRUE),
  (56, 28, TRUE),
  (56, 5, TRUE),
  (56, 3, TRUE),
  (56, 16, TRUE),
  (56, 52, TRUE),
  (57, 19, TRUE),
  (57, 47, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (57, 43, TRUE),
  (57, 52, TRUE),
  (57, 39, TRUE),
  (57, 4, TRUE),
  (57, 26, FALSE),
  (57, 53, TRUE),
  (57, 3, TRUE),
  (58, 37, TRUE),
  (58, 8, TRUE),
  (58, 55, TRUE),
  (58, 34, TRUE),
  (58, 31, TRUE),
  (58, 6, FALSE),
  (58, 23, TRUE),
  (59, 42, TRUE),
  (59, 44, FALSE),
  (59, 27, TRUE),
  (59, 16, TRUE),
  (59, 35, TRUE),
  (59, 17, TRUE),
  (59, 2, TRUE),
  (60, 35, TRUE),
  (60, 49, TRUE),
  (60, 37, TRUE),
  (60, 53, TRUE),
  (60, 4, TRUE),
  (60, 42, TRUE),
  (60, 24, FALSE),
  (61, 38, TRUE),
  (61, 1, TRUE),
  (61, 49, TRUE),
  (61, 12, TRUE),
  (61, 48, TRUE),
  (61, 5, TRUE),
  (62, 24, TRUE),
  (62, 41, TRUE),
  (62, 30, FALSE),
  (62, 40, TRUE),
  (62, 9, TRUE),
  (62, 33, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (62, 25, TRUE),
  (62, 46, TRUE),
  (62, 29, TRUE),
  (63, 14, TRUE),
  (63, 15, TRUE),
  (63, 51, FALSE),
  (63, 7, TRUE),
  (63, 32, FALSE),
  (64, 56, TRUE),
  (64, 13, TRUE),
  (64, 21, TRUE),
  (64, 54, TRUE),
  (64, 10, TRUE),
  (64, 36, TRUE),
  (64, 29, TRUE),
  (64, 40, TRUE),
  (64, 47, TRUE),
  (64, 28, TRUE),
  (65, 19, TRUE),
  (65, 47, TRUE),
  (65, 43, FALSE),
  (65, 52, FALSE),
  (65, 39, TRUE),
  (65, 4, TRUE),
  (65, 26, TRUE),
  (65, 53, TRUE),
  (65, 3, FALSE),
  (66, 42, TRUE),
  (66, 44, TRUE),
  (66, 27, TRUE),
  (66, 16, TRUE),
  (66, 35, TRUE),
  (66, 17, TRUE),
  (66, 2, TRUE),
  (67, 45, TRUE),
  (67, 23, TRUE),
  (67, 1, TRUE),
  (67, 11, TRUE),
  (67, 8, TRUE),
  (67, 12, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (67, 32, TRUE),
  (68, 28, TRUE),
  (68, 5, TRUE),
  (68, 3, TRUE),
  (68, 16, TRUE),
  (68, 52, TRUE),
  (69, 37, TRUE),
  (69, 8, TRUE),
  (69, 55, TRUE),
  (69, 34, TRUE),
  (69, 31, TRUE),
  (69, 6, TRUE),
  (69, 23, TRUE),
  (70, 24, TRUE),
  (70, 41, TRUE),
  (70, 30, TRUE),
  (70, 40, TRUE),
  (70, 9, TRUE),
  (70, 33, TRUE),
  (70, 25, TRUE),
  (70, 46, TRUE),
  (70, 29, TRUE),
  (71, 56, TRUE),
  (71, 13, FALSE),
  (71, 21, TRUE),
  (71, 54, TRUE),
  (71, 10, TRUE),
  (71, 36, TRUE),
  (71, 29, TRUE),
  (71, 40, FALSE),
  (71, 47, TRUE),
  (71, 28, TRUE),
  (72, 38, TRUE),
  (72, 1, TRUE),
  (72, 49, TRUE),
  (72, 12, TRUE),
  (72, 48, FALSE),
  (72, 5, TRUE),
  (73, 14, TRUE),
  (73, 15, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (73, 51, TRUE),
  (73, 7, TRUE),
  (73, 32, TRUE),
  (74, 35, TRUE),
  (74, 49, TRUE),
  (74, 37, TRUE),
  (74, 53, TRUE),
  (74, 4, TRUE),
  (74, 42, TRUE),
  (74, 24, TRUE),
  (75, 45, FALSE),
  (75, 23, TRUE),
  (75, 1, FALSE),
  (75, 11, TRUE),
  (75, 8, TRUE),
  (75, 12, TRUE),
  (75, 32, TRUE),
  (76, 28, TRUE),
  (76, 5, TRUE),
  (76, 3, TRUE),
  (76, 16, TRUE),
  (76, 52, TRUE),
  (77, 19, TRUE),
  (77, 47, TRUE),
  (77, 43, FALSE),
  (77, 52, TRUE),
  (77, 39, FALSE),
  (77, 4, FALSE),
  (77, 26, TRUE),
  (77, 53, TRUE),
  (77, 3, TRUE),
  (78, 37, TRUE),
  (78, 8, TRUE),
  (78, 55, FALSE),
  (78, 34, TRUE),
  (78, 31, TRUE),
  (78, 6, TRUE),
  (78, 23, TRUE),
  (79, 42, TRUE),
  (79, 44, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (79, 27, TRUE),
  (79, 16, TRUE),
  (79, 35, TRUE),
  (79, 17, FALSE),
  (79, 2, FALSE),
  (80, 35, TRUE),
  (80, 49, TRUE),
  (80, 37, FALSE),
  (80, 53, TRUE),
  (80, 4, TRUE),
  (80, 42, TRUE),
  (80, 24, FALSE),
  (81, 38, TRUE),
  (81, 1, FALSE),
  (81, 49, TRUE),
  (81, 12, TRUE),
  (81, 48, TRUE),
  (81, 5, TRUE),
  (82, 24, TRUE),
  (82, 41, TRUE),
  (82, 30, TRUE),
  (82, 40, TRUE),
  (82, 9, TRUE),
  (82, 33, TRUE),
  (82, 25, FALSE),
  (82, 46, FALSE),
  (82, 29, TRUE),
  (83, 14, TRUE),
  (83, 15, TRUE),
  (83, 51, TRUE),
  (83, 7, TRUE),
  (83, 32, FALSE),
  (84, 56, TRUE),
  (84, 13, TRUE),
  (84, 21, TRUE),
  (84, 54, FALSE),
  (84, 10, TRUE),
  (84, 36, TRUE),
  (84, 29, FALSE),
  (84, 40, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (84, 47, FALSE),
  (84, 28, TRUE),
  (85, 19, TRUE),
  (85, 47, TRUE),
  (85, 43, TRUE),
  (85, 52, TRUE),
  (85, 39, TRUE),
  (85, 4, TRUE),
  (85, 26, TRUE),
  (85, 53, TRUE),
  (85, 3, TRUE),
  (86, 42, TRUE),
  (86, 44, TRUE),
  (86, 27, TRUE),
  (86, 16, TRUE),
  (86, 35, TRUE),
  (86, 17, TRUE),
  (86, 2, TRUE),
  (87, 45, TRUE),
  (87, 23, TRUE),
  (87, 1, TRUE),
  (87, 11, TRUE),
  (87, 8, TRUE),
  (87, 12, TRUE),
  (87, 32, TRUE),
  (88, 28, TRUE),
  (88, 5, TRUE),
  (88, 3, FALSE),
  (88, 16, FALSE),
  (88, 52, TRUE),
  (89, 37, TRUE),
  (89, 8, TRUE),
  (89, 55, TRUE),
  (89, 34, TRUE),
  (89, 31, TRUE),
  (89, 23, TRUE),
  (90, 24, TRUE),
  (90, 41, TRUE),
  (90, 30, TRUE),
  (90, 40, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (90, 9, TRUE),
  (90, 33, TRUE),
  (90, 25, TRUE),
  (90, 46, TRUE),
  (90, 29, TRUE),
  (91, 56, TRUE),
  (91, 13, TRUE),
  (91, 21, TRUE),
  (91, 54, TRUE),
  (91, 10, TRUE),
  (91, 36, TRUE),
  (91, 29, TRUE),
  (91, 40, TRUE),
  (91, 47, TRUE),
  (91, 28, TRUE),
  (92, 38, TRUE),
  (92, 1, TRUE),
  (92, 49, TRUE),
  (92, 12, FALSE),
  (92, 48, TRUE),
  (92, 5, TRUE),
  (93, 14, TRUE),
  (93, 15, FALSE),
  (93, 51, TRUE),
  (93, 7, TRUE),
  (93, 20, TRUE),
  (93, 32, TRUE),
  (94, 35, FALSE),
  (94, 49, TRUE),
  (94, 37, TRUE),
  (94, 53, TRUE),
  (94, 4, TRUE),
  (94, 42, TRUE),
  (94, 24, TRUE),
  (95, 45, TRUE),
  (95, 23, FALSE),
  (95, 1, TRUE),
  (95, 48, TRUE),
  (95, 11, TRUE),
  (95, 8, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (95, 12, TRUE),
  (95, 32, TRUE),
  (96, 28, TRUE),
  (96, 5, TRUE),
  (96, 3, TRUE),
  (96, 16, TRUE),
  (96, 52, TRUE),
  (97, 19, FALSE),
  (97, 47, TRUE),
  (97, 43, FALSE),
  (97, 52, TRUE),
  (97, 39, FALSE),
  (97, 4, TRUE),
  (97, 26, TRUE),
  (97, 53, FALSE),
  (97, 3, TRUE),
  (98, 37, TRUE),
  (98, 8, TRUE),
  (98, 55, TRUE),
  (98, 34, TRUE),
  (98, 31, TRUE),
  (98, 23, TRUE),
  (98, 7, TRUE),
  (99, 42, TRUE),
  (99, 50, FALSE),
  (99, 44, TRUE),
  (99, 27, FALSE),
  (99, 16, TRUE),
  (99, 22, TRUE),
  (99, 17, TRUE),
  (99, 2, TRUE),
  (99, 6, FALSE),
  (100, 35, TRUE),
  (100, 49, TRUE),
  (100, 37, TRUE),
  (100, 53, TRUE),
  (100, 4, TRUE),
  (100, 42, TRUE),
  (100, 24, TRUE),
  (101, 38, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (101, 1, TRUE),
  (101, 49, TRUE),
  (101, 12, FALSE),
  (101, 48, TRUE),
  (101, 5, TRUE),
  (102, 24, TRUE),
  (102, 41, TRUE),
  (102, 30, TRUE),
  (102, 40, TRUE),
  (102, 9, TRUE),
  (102, 33, TRUE),
  (102, 25, TRUE),
  (102, 46, TRUE),
  (102, 29, FALSE),
  (103, 14, TRUE),
  (103, 15, TRUE),
  (103, 51, TRUE),
  (103, 7, TRUE),
  (103, 20, FALSE),
  (103, 32, TRUE),
  (104, 56, TRUE),
  (104, 13, TRUE),
  (104, 21, TRUE),
  (104, 54, TRUE),
  (104, 10, FALSE),
  (104, 36, TRUE),
  (104, 29, TRUE),
  (104, 40, TRUE),
  (104, 47, TRUE),
  (104, 28, TRUE),
  (105, 19, TRUE),
  (105, 47, TRUE),
  (105, 43, TRUE),
  (105, 52, TRUE),
  (105, 39, TRUE),
  (105, 4, TRUE),
  (105, 26, TRUE),
  (105, 53, TRUE),
  (105, 3, TRUE),
  (106, 42, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (106, 50, TRUE),
  (106, 44, TRUE),
  (106, 27, TRUE),
  (106, 16, TRUE),
  (106, 22, TRUE),
  (106, 17, FALSE),
  (106, 2, TRUE),
  (106, 6, TRUE),
  (107, 45, TRUE),
  (107, 23, TRUE),
  (107, 1, TRUE),
  (107, 48, TRUE),
  (107, 11, FALSE),
  (107, 8, TRUE),
  (107, 12, TRUE),
  (107, 32, TRUE),
  (108, 28, TRUE),
  (108, 5, TRUE),
  (108, 3, TRUE),
  (108, 16, TRUE),
  (108, 52, TRUE),
  (109, 37, TRUE),
  (109, 18, TRUE),
  (109, 8, TRUE),
  (109, 55, TRUE),
  (109, 34, TRUE),
  (109, 31, TRUE),
  (109, 23, TRUE),
  (109, 7, FALSE),
  (110, 24, TRUE),
  (110, 41, TRUE),
  (110, 30, TRUE),
  (110, 40, TRUE),
  (110, 9, TRUE),
  (110, 33, TRUE),
  (110, 25, TRUE),
  (110, 46, FALSE),
  (110, 29, TRUE),
  (111, 56, TRUE),
  (111, 13, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (111, 21, FALSE),
  (111, 54, TRUE),
  (111, 10, TRUE),
  (111, 36, FALSE),
  (111, 29, FALSE),
  (111, 40, TRUE),
  (111, 47, TRUE),
  (111, 28, TRUE),
  (112, 38, TRUE),
  (112, 1, TRUE),
  (112, 49, TRUE),
  (112, 12, FALSE),
  (112, 48, TRUE),
  (112, 5, TRUE),
  (113, 14, TRUE),
  (113, 15, TRUE),
  (113, 36, TRUE),
  (113, 51, FALSE),
  (113, 7, TRUE),
  (113, 20, TRUE),
  (113, 32, FALSE),
  (114, 35, TRUE),
  (114, 49, TRUE),
  (114, 37, TRUE),
  (114, 27, TRUE),
  (114, 55, TRUE),
  (114, 53, TRUE),
  (114, 4, TRUE),
  (114, 42, TRUE),
  (114, 24, FALSE),
  (115, 45, TRUE),
  (115, 23, TRUE),
  (115, 1, TRUE),
  (115, 48, TRUE),
  (115, 11, FALSE),
  (115, 8, TRUE),
  (115, 12, TRUE),
  (115, 32, TRUE),
  (116, 28, TRUE),
  (116, 5, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (116, 3, TRUE),
  (116, 16, TRUE),
  (116, 52, TRUE),
  (117, 19, TRUE),
  (117, 47, TRUE),
  (117, 43, TRUE),
  (117, 52, TRUE),
  (117, 39, FALSE),
  (117, 11, TRUE),
  (117, 4, TRUE),
  (117, 26, TRUE),
  (117, 53, TRUE),
  (117, 3, TRUE),
  (118, 37, TRUE),
  (118, 18, TRUE),
  (118, 8, FALSE),
  (118, 55, TRUE),
  (118, 34, TRUE),
  (118, 31, TRUE),
  (118, 23, TRUE),
  (118, 7, TRUE),
  (119, 42, TRUE),
  (119, 50, TRUE),
  (119, 44, FALSE),
  (119, 27, TRUE),
  (119, 16, TRUE),
  (119, 22, TRUE),
  (119, 17, TRUE),
  (119, 2, TRUE),
  (119, 6, TRUE),
  (120, 35, TRUE),
  (120, 49, TRUE),
  (120, 37, TRUE),
  (120, 27, TRUE),
  (120, 55, TRUE),
  (120, 53, TRUE),
  (120, 4, TRUE),
  (120, 42, TRUE),
  (120, 24, TRUE),
  (121, 38, FALSE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (121, 1, TRUE),
  (121, 49, TRUE),
  (121, 12, FALSE),
  (121, 48, TRUE),
  (121, 5, TRUE),
  (122, 24, TRUE),
  (122, 41, TRUE),
  (122, 30, TRUE),
  (122, 40, TRUE),
  (122, 9, TRUE),
  (122, 33, TRUE),
  (122, 25, TRUE),
  (122, 46, TRUE),
  (122, 29, TRUE),
  (123, 14, TRUE),
  (123, 15, TRUE),
  (123, 36, TRUE),
  (123, 51, TRUE),
  (123, 7, TRUE),
  (123, 20, TRUE),
  (123, 32, TRUE),
  (124, 56, TRUE),
  (124, 13, TRUE),
  (124, 21, TRUE),
  (124, 54, TRUE),
  (124, 10, TRUE),
  (124, 36, TRUE),
  (124, 29, TRUE),
  (124, 40, TRUE),
  (124, 47, TRUE),
  (124, 28, TRUE),
  (125, 37, TRUE),
  (125, 18, TRUE),
  (125, 8, TRUE),
  (125, 55, TRUE),
  (125, 34, TRUE),
  (125, 31, TRUE),
  (125, 23, TRUE),
  (125, 7, TRUE),
  (126, 24, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (126, 41, TRUE),
  (126, 30, TRUE),
  (126, 40, TRUE),
  (126, 9, TRUE),
  (126, 33, TRUE),
  (126, 25, TRUE),
  (126, 46, FALSE),
  (126, 29, TRUE),
  (127, 56, TRUE),
  (127, 13, TRUE),
  (127, 21, TRUE),
  (127, 54, TRUE),
  (127, 10, TRUE),
  (127, 36, TRUE),
  (127, 29, TRUE),
  (127, 40, TRUE),
  (127, 47, TRUE),
  (127, 28, TRUE),
  (128, 38, TRUE),
  (128, 1, TRUE),
  (128, 49, TRUE),
  (128, 12, TRUE),
  (128, 48, TRUE),
  (128, 5, FALSE),
  (129, 14, TRUE),
  (129, 15, TRUE),
  (129, 36, TRUE),
  (129, 51, FALSE),
  (129, 7, TRUE),
  (129, 20, TRUE),
  (129, 32, FALSE),
  (130, 35, TRUE),
  (130, 49, TRUE),
  (130, 37, FALSE),
  (130, 27, TRUE),
  (130, 55, TRUE),
  (130, 53, TRUE),
  (130, 4, TRUE),
  (130, 42, FALSE),
  (130, 24, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (131, 45, FALSE),
  (131, 23, TRUE),
  (131, 1, TRUE),
  (131, 48, TRUE),
  (131, 11, TRUE),
  (131, 8, TRUE),
  (131, 12, TRUE),
  (131, 32, FALSE),
  (132, 28, TRUE),
  (132, 5, FALSE),
  (132, 3, TRUE),
  (132, 16, TRUE),
  (132, 52, FALSE),
  (133, 19, TRUE),
  (133, 47, TRUE),
  (133, 43, TRUE),
  (133, 52, TRUE),
  (133, 39, TRUE),
  (133, 11, TRUE),
  (133, 4, TRUE),
  (133, 26, FALSE),
  (133, 53, TRUE),
  (133, 3, TRUE),
  (134, 37, TRUE),
  (134, 18, FALSE),
  (134, 8, TRUE),
  (134, 55, FALSE),
  (134, 34, TRUE),
  (134, 31, FALSE),
  (134, 23, TRUE),
  (134, 7, TRUE),
  (135, 42, TRUE),
  (135, 50, TRUE),
  (135, 44, FALSE),
  (135, 27, TRUE),
  (135, 16, TRUE),
  (135, 22, TRUE),
  (135, 17, TRUE),
  (135, 2, TRUE),
  (135, 6, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (136, 35, TRUE),
  (136, 49, TRUE),
  (136, 37, TRUE),
  (136, 27, TRUE),
  (136, 55, TRUE),
  (136, 53, TRUE),
  (136, 4, TRUE),
  (136, 42, FALSE),
  (136, 24, TRUE),
  (137, 38, FALSE),
  (137, 1, TRUE),
  (137, 49, TRUE),
  (137, 12, TRUE),
  (137, 48, TRUE),
  (137, 5, TRUE),
  (138, 24, TRUE),
  (138, 41, FALSE),
  (138, 30, TRUE),
  (138, 40, TRUE),
  (138, 9, TRUE),
  (138, 33, FALSE),
  (138, 25, TRUE),
  (138, 46, TRUE),
  (138, 29, FALSE),
  (139, 14, TRUE),
  (139, 15, TRUE),
  (139, 36, TRUE),
  (139, 51, TRUE),
  (139, 7, TRUE),
  (139, 20, TRUE),
  (139, 32, TRUE),
  (140, 56, TRUE),
  (140, 13, TRUE),
  (140, 21, TRUE),
  (140, 54, TRUE),
  (140, 10, TRUE),
  (140, 36, TRUE),
  (140, 29, TRUE),
  (140, 40, TRUE),
  (140, 47, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (140, 28, TRUE),
  (141, 19, TRUE),
  (141, 47, TRUE),
  (141, 43, TRUE),
  (141, 52, TRUE),
  (141, 39, TRUE),
  (141, 11, TRUE),
  (141, 4, TRUE),
  (141, 26, TRUE),
  (141, 53, TRUE),
  (141, 3, TRUE),
  (142, 42, TRUE),
  (142, 50, FALSE),
  (142, 44, TRUE),
  (142, 27, TRUE),
  (142, 16, TRUE),
  (142, 22, TRUE),
  (142, 17, TRUE),
  (142, 2, TRUE),
  (142, 6, TRUE),
  (143, 45, FALSE),
  (143, 23, TRUE),
  (143, 1, TRUE),
  (143, 48, TRUE),
  (143, 11, TRUE),
  (143, 8, TRUE),
  (143, 12, TRUE),
  (143, 32, TRUE),
  (144, 28, TRUE),
  (144, 5, FALSE),
  (144, 3, TRUE),
  (144, 16, TRUE),
  (144, 52, TRUE),
  (145, 37, TRUE),
  (145, 18, TRUE),
  (145, 8, TRUE),
  (145, 55, TRUE),
  (145, 34, TRUE),
  (145, 31, TRUE),
  (145, 23, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (145, 7, TRUE),
  (146, 24, TRUE),
  (146, 41, TRUE),
  (146, 30, TRUE),
  (146, 40, TRUE),
  (146, 9, FALSE),
  (146, 33, TRUE),
  (146, 25, FALSE),
  (146, 46, TRUE),
  (146, 29, FALSE),
  (147, 56, TRUE),
  (147, 13, TRUE),
  (147, 21, TRUE),
  (147, 54, TRUE),
  (147, 10, TRUE),
  (147, 36, TRUE),
  (147, 29, TRUE),
  (147, 40, TRUE),
  (147, 47, FALSE),
  (147, 28, TRUE),
  (148, 38, TRUE),
  (148, 1, TRUE),
  (148, 49, TRUE),
  (148, 12, TRUE),
  (148, 48, TRUE),
  (148, 5, TRUE),
  (149, 14, TRUE),
  (149, 15, TRUE),
  (149, 36, TRUE),
  (149, 51, TRUE),
  (149, 7, FALSE),
  (149, 20, FALSE),
  (149, 32, TRUE),
  (150, 35, TRUE),
  (150, 49, TRUE),
  (150, 37, FALSE),
  (150, 27, FALSE),
  (150, 55, FALSE),
  (150, 53, TRUE),
  (150, 4, FALSE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (150, 42, FALSE),
  (150, 24, TRUE),
  (151, 45, TRUE),
  (151, 23, TRUE),
  (151, 1, TRUE),
  (151, 48, TRUE),
  (151, 11, TRUE),
  (151, 8, FALSE),
  (151, 12, TRUE),
  (151, 32, TRUE),
  (152, 28, TRUE),
  (152, 5, TRUE),
  (152, 3, TRUE),
  (152, 16, TRUE),
  (152, 52, TRUE),
  (153, 19, TRUE),
  (153, 47, FALSE),
  (153, 43, TRUE),
  (153, 52, TRUE),
  (153, 39, FALSE),
  (153, 11, TRUE),
  (153, 4, TRUE),
  (153, 26, TRUE),
  (153, 53, TRUE),
  (153, 3, TRUE),
  (154, 37, TRUE),
  (154, 18, TRUE),
  (154, 8, TRUE),
  (154, 55, TRUE),
  (154, 34, FALSE),
  (154, 31, TRUE),
  (154, 23, TRUE),
  (154, 7, TRUE),
  (155, 42, TRUE),
  (155, 50, TRUE),
  (155, 44, FALSE),
  (155, 27, TRUE),
  (155, 16, TRUE),
  (155, 22, TRUE),
  (155, 17, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (155, 2, TRUE),
  (155, 6, TRUE),
  (156, 35, FALSE),
  (156, 49, TRUE),
  (156, 37, TRUE),
  (156, 27, TRUE),
  (156, 55, TRUE),
  (156, 53, TRUE),
  (156, 4, TRUE),
  (156, 42, TRUE),
  (156, 24, TRUE),
  (157, 38, TRUE),
  (157, 1, TRUE),
  (157, 49, TRUE),
  (157, 12, TRUE),
  (157, 48, TRUE),
  (157, 5, TRUE),
  (158, 24, TRUE),
  (158, 41, TRUE),
  (158, 30, TRUE),
  (158, 40, TRUE),
  (158, 9, TRUE),
  (158, 33, TRUE),
  (158, 25, TRUE),
  (158, 46, TRUE),
  (158, 29, TRUE),
  (159, 14, FALSE),
  (159, 15, TRUE),
  (159, 36, TRUE),
  (159, 51, FALSE),
  (159, 7, TRUE),
  (159, 20, TRUE),
  (159, 32, TRUE),
  (160, 56, TRUE),
  (160, 13, TRUE),
  (160, 21, TRUE),
  (160, 54, FALSE),
  (160, 10, TRUE),
  (160, 36, TRUE),
  (160, 29, FALSE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (160, 40, FALSE),
  (160, 47, TRUE),
  (160, 28, TRUE),
  (161, 19, FALSE),
  (161, 47, TRUE),
  (161, 43, FALSE),
  (161, 52, TRUE),
  (161, 39, TRUE),
  (161, 11, TRUE),
  (161, 4, TRUE),
  (161, 26, FALSE),
  (161, 53, TRUE),
  (161, 3, TRUE),
  (162, 42, FALSE),
  (162, 50, TRUE),
  (162, 44, TRUE),
  (162, 27, TRUE),
  (162, 16, TRUE),
  (162, 22, TRUE),
  (162, 17, TRUE),
  (162, 2, TRUE),
  (162, 6, TRUE),
  (163, 45, TRUE),
  (163, 23, TRUE),
  (163, 1, TRUE),
  (163, 48, FALSE),
  (163, 11, TRUE),
  (163, 8, TRUE),
  (163, 12, FALSE),
  (163, 32, TRUE),
  (164, 28, TRUE),
  (164, 5, TRUE),
  (164, 3, TRUE),
  (164, 16, TRUE),
  (164, 52, TRUE),
  (165, 37, TRUE),
  (165, 18, TRUE),
  (165, 8, FALSE),
  (165, 55, TRUE),
  (165, 34, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (165, 31, TRUE),
  (165, 23, TRUE),
  (165, 7, TRUE),
  (166, 24, TRUE),
  (166, 41, TRUE),
  (166, 30, TRUE),
  (166, 40, FALSE),
  (166, 9, TRUE),
  (166, 33, TRUE),
  (166, 25, TRUE),
  (166, 46, TRUE),
  (166, 29, TRUE),
  (167, 56, TRUE),
  (167, 13, TRUE),
  (167, 21, TRUE),
  (167, 54, TRUE),
  (167, 10, TRUE),
  (167, 36, TRUE),
  (167, 29, TRUE),
  (167, 40, TRUE),
  (167, 47, TRUE),
  (167, 28, TRUE),
  (168, 38, TRUE),
  (168, 1, TRUE),
  (168, 49, TRUE),
  (168, 12, TRUE),
  (168, 48, TRUE),
  (168, 5, TRUE),
  (169, 14, TRUE),
  (169, 15, FALSE),
  (169, 36, FALSE),
  (169, 51, TRUE),
  (169, 7, TRUE),
  (169, 20, TRUE),
  (169, 32, TRUE),
  (170, 35, TRUE),
  (170, 49, TRUE),
  (170, 37, TRUE),
  (170, 27, TRUE),
  (170, 55, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (170, 53, TRUE),
  (170, 4, TRUE),
  (170, 42, TRUE),
  (170, 24, TRUE),
  (171, 45, TRUE),
  (171, 23, TRUE),
  (171, 1, TRUE),
  (171, 48, FALSE),
  (171, 11, TRUE),
  (171, 8, TRUE),
  (171, 12, TRUE),
  (171, 32, TRUE),
  (172, 28, TRUE),
  (172, 5, TRUE),
  (172, 3, TRUE),
  (172, 16, TRUE),
  (172, 52, TRUE),
  (173, 19, TRUE),
  (173, 47, TRUE),
  (173, 43, TRUE),
  (173, 52, TRUE),
  (173, 39, TRUE),
  (173, 11, TRUE),
  (173, 4, TRUE),
  (173, 26, TRUE),
  (173, 53, TRUE),
  (173, 3, TRUE),
  (174, 37, TRUE),
  (174, 18, TRUE),
  (174, 8, TRUE),
  (174, 55, TRUE),
  (174, 34, TRUE),
  (174, 31, TRUE),
  (174, 23, TRUE),
  (174, 7, FALSE),
  (175, 42, TRUE),
  (175, 50, TRUE),
  (175, 44, TRUE),
  (175, 27, TRUE),
  (175, 16, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (175, 22, TRUE),
  (175, 17, FALSE),
  (175, 2, TRUE),
  (175, 6, TRUE),
  (176, 38, TRUE),
  (176, 1, TRUE),
  (176, 49, TRUE),
  (176, 12, TRUE),
  (176, 48, TRUE),
  (176, 5, TRUE),
  (177, 24, TRUE),
  (177, 41, TRUE),
  (177, 30, TRUE),
  (177, 40, TRUE),
  (177, 9, TRUE),
  (177, 33, TRUE),
  (177, 25, TRUE),
  (177, 46, TRUE),
  (177, 29, TRUE),
  (178, 14, TRUE),
  (178, 15, TRUE),
  (178, 36, TRUE),
  (178, 51, TRUE),
  (178, 7, TRUE),
  (178, 20, TRUE),
  (178, 32, TRUE),
  (179, 56, TRUE),
  (179, 13, TRUE),
  (179, 21, TRUE),
  (179, 54, TRUE),
  (179, 10, FALSE),
  (179, 36, TRUE),
  (179, 29, TRUE),
  (179, 40, TRUE),
  (179, 47, TRUE),
  (179, 28, TRUE),
  (180, 19, TRUE),
  (180, 47, TRUE),
  (180, 43, TRUE),
  (180, 52, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (180, 39, TRUE),
  (180, 11, TRUE),
  (180, 4, TRUE),
  (180, 26, TRUE),
  (180, 53, FALSE),
  (180, 3, TRUE),
  (181, 42, FALSE),
  (181, 50, TRUE),
  (181, 44, TRUE),
  (181, 27, TRUE),
  (181, 16, TRUE),
  (181, 22, TRUE),
  (181, 17, TRUE),
  (181, 2, TRUE),
  (181, 6, TRUE),
  (182, 45, TRUE),
  (182, 23, TRUE),
  (182, 1, TRUE),
  (182, 48, TRUE),
  (182, 11, TRUE),
  (182, 8, TRUE),
  (182, 12, TRUE),
  (182, 32, TRUE),
  (183, 28, TRUE),
  (183, 5, TRUE),
  (183, 3, TRUE),
  (183, 16, TRUE),
  (183, 52, TRUE),
  (184, 37, TRUE),
  (184, 18, FALSE),
  (184, 8, TRUE),
  (184, 55, FALSE),
  (184, 34, TRUE),
  (184, 31, TRUE),
  (184, 23, TRUE),
  (184, 7, TRUE),
  (185, 24, TRUE),
  (185, 41, TRUE),
  (185, 30, TRUE),
  (185, 40, FALSE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (185, 9, TRUE),
  (185, 33, TRUE),
  (185, 25, TRUE),
  (185, 46, FALSE),
  (185, 29, TRUE),
  (186, 56, TRUE),
  (186, 13, TRUE),
  (186, 21, TRUE),
  (186, 54, TRUE),
  (186, 10, FALSE),
  (186, 36, TRUE),
  (186, 29, TRUE),
  (186, 40, TRUE),
  (186, 47, TRUE),
  (186, 28, TRUE),
  (187, 38, TRUE),
  (187, 1, FALSE),
  (187, 49, TRUE),
  (187, 12, TRUE),
  (187, 48, TRUE),
  (187, 5, FALSE),
  (188, 14, FALSE),
  (188, 15, TRUE),
  (188, 36, TRUE),
  (188, 51, TRUE),
  (188, 7, TRUE),
  (188, 20, FALSE),
  (188, 32, TRUE),
  (189, 45, TRUE),
  (189, 23, FALSE),
  (189, 1, TRUE),
  (189, 48, TRUE),
  (189, 11, TRUE),
  (189, 8, TRUE),
  (189, 12, TRUE),
  (189, 32, TRUE),
  (190, 28, TRUE),
  (190, 5, TRUE),
  (190, 3, TRUE),
  (190, 16, TRUE);
INSERT INTO attendance (lesson_id, participant_id, present) VALUES
  (190, 52, TRUE);

-- Заходи
INSERT INTO event (title, event_type, event_date, place, description) VALUES ('Концерт до Дня знань «Здрастуй, БДТ!»', 'Концерт', DATE '2026-09-01', 'Актова зала БДТ', 'Святковий концерт для учасників та батьків');
INSERT INTO event (title, event_type, event_date, place, description) VALUES ('Виставка дитячих робіт «Барви осені»', 'Виставка', DATE '2026-10-16', 'Хол другого поверху БДТ', 'Виставка малюнків та виробів');
INSERT INTO event (title, event_type, event_date, place, description) VALUES ('Міський турнір «Юний гросмейстер»', 'Змагання', DATE '2026-10-24', 'Шаховий клуб м. Одеси', 'Турнір серед юнаків до 16 років');
INSERT INTO event (title, event_type, event_date, place, description) VALUES ('Фестиваль технічної творчості «Техно-Старт»', 'Конкурс', DATE '2026-11-14', 'Палац молоді', 'Конкурс робототехнічних проєктів');
INSERT INTO event (title, event_type, event_date, place, description) VALUES ('Новорічний концерт «Зимова казка»', 'Концерт', DATE '2026-12-26', 'Актова зала БДТ', 'Звітний концерт творчих колективів');

-- Участь секцій у заходах
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (1, 1, 'Танець «Віночок»', 'Учасник');
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (1, 3, 'Пісня «Мій рідний край» під гітару', 'Учасник');
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (1, 4, 'Пісня «Осінь»', 'Учасник');
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (2, 5, '12 малюнків «Осінній пейзаж»', 'Диплом I ступеня');
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (2, 6, '8 розписних дощечок', 'Диплом II ступеня');
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (3, 9, 'Команда з 4 шахістів', '2 місце');
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (4, 7, 'Робот-сортувальник', NULL);
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (4, 8, '3D-модель «Місто майбутнього»', NULL);
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (5, 1, 'Танець «Метелиця»', NULL);
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (5, 2, 'Сучасна хореографічна композиція', NULL);
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (5, 4, 'Новорічне попурі', NULL);
INSERT INTO event_section (event_id, section_id, performance, result) VALUES (5, 10, 'Показовий виступ', NULL);

-- Оплата занять
INSERT INTO payment (enrollment_id, pay_date, period, amount, method) VALUES
  (1, DATE '2026-09-07', '2026-09', 450.00, 'Картка'),
  (1, DATE '2026-10-03', '2026-10', 450.00, 'Картка'),
  (2, DATE '2026-09-09', '2026-09', 315.00, 'Картка'),
  (2, DATE '2026-10-09', '2026-10', 315.00, 'Картка'),
  (2, DATE '2026-11-06', '2026-11', 315.00, 'Готівка'),
  (3, DATE '2026-09-04', '2026-09', 450.00, 'Готівка'),
  (3, DATE '2026-10-01', '2026-10', 450.00, 'Картка'),
  (3, DATE '2026-11-07', '2026-11', 450.00, 'Готівка'),
  (4, DATE '2026-09-04', '2026-09', 450.00, 'Картка'),
  (4, DATE '2026-10-07', '2026-10', 450.00, 'Картка'),
  (4, DATE '2026-11-06', '2026-11', 450.00, 'Картка'),
  (5, DATE '2026-09-02', '2026-09', 450.00, 'Готівка'),
  (5, DATE '2026-10-04', '2026-10', 450.00, 'Картка'),
  (6, DATE '2026-10-11', '2026-10', 450.00, 'Картка'),
  (6, DATE '2026-11-07', '2026-11', 450.00, 'Картка'),
  (7, DATE '2026-09-05', '2026-09', 315.00, 'Готівка'),
  (7, DATE '2026-10-02', '2026-10', 315.00, 'Картка'),
  (7, DATE '2026-11-03', '2026-11', 315.00, 'Картка'),
  (8, DATE '2026-09-10', '2026-09', 450.00, 'Картка'),
  (8, DATE '2026-10-06', '2026-10', 450.00, 'Готівка'),
  (8, DATE '2026-11-02', '2026-11', 450.00, 'Картка'),
  (9, DATE '2026-09-10', '2026-09', 450.00, 'Готівка'),
  (9, DATE '2026-10-04', '2026-10', 450.00, 'Картка'),
  (9, DATE '2026-11-03', '2026-11', 450.00, 'Картка'),
  (10, DATE '2026-09-01', '2026-09', 450.00, 'Картка'),
  (10, DATE '2026-10-12', '2026-10', 450.00, 'Картка'),
  (11, DATE '2026-09-04', '2026-09', 500.00, 'Картка'),
  (11, DATE '2026-10-07', '2026-10', 500.00, 'Картка'),
  (11, DATE '2026-11-03', '2026-11', 500.00, 'Картка'),
  (12, DATE '2026-10-07', '2026-10', 350.00, 'Готівка'),
  (12, DATE '2026-11-04', '2026-11', 350.00, 'Готівка'),
  (13, DATE '2026-09-07', '2026-09', 350.00, 'Картка'),
  (13, DATE '2026-10-08', '2026-10', 350.00, 'Картка'),
  (13, DATE '2026-11-02', '2026-11', 350.00, 'Готівка'),
  (14, DATE '2026-09-08', '2026-09', 500.00, 'Картка'),
  (14, DATE '2026-10-09', '2026-10', 500.00, 'Картка'),
  (14, DATE '2026-11-07', '2026-11', 500.00, 'Картка'),
  (15, DATE '2026-09-03', '2026-09', 500.00, 'Готівка'),
  (15, DATE '2026-10-05', '2026-10', 500.00, 'Готівка'),
  (15, DATE '2026-11-02', '2026-11', 500.00, 'Готівка');
INSERT INTO payment (enrollment_id, pay_date, period, amount, method) VALUES
  (16, DATE '2026-09-10', '2026-09', 500.00, 'Картка'),
  (16, DATE '2026-10-02', '2026-10', 500.00, 'Готівка'),
  (16, DATE '2026-11-02', '2026-11', 500.00, 'Картка'),
  (17, DATE '2026-09-08', '2026-09', 500.00, 'Картка'),
  (18, DATE '2026-09-04', '2026-09', 500.00, 'Картка'),
  (18, DATE '2026-10-05', '2026-10', 500.00, 'Готівка'),
  (18, DATE '2026-11-02', '2026-11', 500.00, 'Готівка'),
  (19, DATE '2026-09-09', '2026-09', 600.00, 'Готівка'),
  (19, DATE '2026-10-01', '2026-10', 600.00, 'Картка'),
  (20, DATE '2026-09-02', '2026-09', 300.00, 'Картка'),
  (20, DATE '2026-10-05', '2026-10', 300.00, 'Картка'),
  (20, DATE '2026-11-05', '2026-11', 300.00, 'Картка'),
  (21, DATE '2026-09-09', '2026-09', 600.00, 'Картка'),
  (21, DATE '2026-10-04', '2026-10', 600.00, 'Картка'),
  (22, DATE '2026-09-02', '2026-09', 600.00, 'Картка'),
  (22, DATE '2026-10-01', '2026-10', 600.00, 'Готівка'),
  (22, DATE '2026-11-05', '2026-11', 600.00, 'Картка'),
  (23, DATE '2026-09-02', '2026-09', 600.00, 'Готівка'),
  (23, DATE '2026-10-09', '2026-10', 600.00, 'Картка'),
  (23, DATE '2026-11-06', '2026-11', 600.00, 'Картка'),
  (24, DATE '2026-09-03', '2026-09', 600.00, 'Готівка'),
  (24, DATE '2026-10-05', '2026-10', 600.00, 'Картка'),
  (24, DATE '2026-11-03', '2026-11', 600.00, 'Готівка'),
  (25, DATE '2026-09-03', '2026-09', 200.00, 'Картка'),
  (25, DATE '2026-10-09', '2026-10', 200.00, 'Готівка'),
  (25, DATE '2026-11-07', '2026-11', 200.00, 'Картка'),
  (26, DATE '2026-09-05', '2026-09', 400.00, 'Готівка'),
  (26, DATE '2026-10-09', '2026-10', 400.00, 'Готівка'),
  (26, DATE '2026-11-07', '2026-11', 400.00, 'Картка'),
  (27, DATE '2026-09-03', '2026-09', 400.00, 'Готівка'),
  (27, DATE '2026-10-01', '2026-10', 400.00, 'Готівка'),
  (27, DATE '2026-11-02', '2026-11', 400.00, 'Картка'),
  (28, DATE '2026-09-07', '2026-09', 400.00, 'Готівка'),
  (28, DATE '2026-10-11', '2026-10', 400.00, 'Готівка'),
  (28, DATE '2026-11-08', '2026-11', 400.00, 'Картка'),
  (29, DATE '2026-09-10', '2026-09', 400.00, 'Картка'),
  (29, DATE '2026-10-09', '2026-10', 400.00, 'Картка'),
  (29, DATE '2026-11-03', '2026-11', 400.00, 'Картка'),
  (30, DATE '2026-09-02', '2026-09', 280.00, 'Готівка'),
  (30, DATE '2026-10-06', '2026-10', 280.00, 'Картка');
INSERT INTO payment (enrollment_id, pay_date, period, amount, method) VALUES
  (30, DATE '2026-11-06', '2026-11', 280.00, 'Картка'),
  (31, DATE '2026-09-07', '2026-09', 280.00, 'Готівка'),
  (31, DATE '2026-10-03', '2026-10', 280.00, 'Готівка'),
  (31, DATE '2026-11-05', '2026-11', 280.00, 'Картка'),
  (32, DATE '2026-09-02', '2026-09', 400.00, 'Картка'),
  (32, DATE '2026-10-12', '2026-10', 400.00, 'Картка'),
  (32, DATE '2026-11-06', '2026-11', 400.00, 'Картка'),
  (33, DATE '2026-09-03', '2026-09', 400.00, 'Картка'),
  (33, DATE '2026-10-02', '2026-10', 400.00, 'Картка'),
  (33, DATE '2026-11-08', '2026-11', 400.00, 'Картка'),
  (34, DATE '2026-09-06', '2026-09', 350.00, 'Готівка'),
  (34, DATE '2026-10-02', '2026-10', 350.00, 'Картка'),
  (34, DATE '2026-11-08', '2026-11', 350.00, 'Картка'),
  (35, DATE '2026-10-05', '2026-10', 350.00, 'Готівка'),
  (35, DATE '2026-11-03', '2026-11', 350.00, 'Картка'),
  (36, DATE '2026-09-06', '2026-09', 350.00, 'Картка'),
  (36, DATE '2026-10-02', '2026-10', 350.00, 'Готівка'),
  (36, DATE '2026-11-03', '2026-11', 350.00, 'Готівка'),
  (37, DATE '2026-09-02', '2026-09', 350.00, 'Картка'),
  (37, DATE '2026-10-12', '2026-10', 350.00, 'Картка'),
  (37, DATE '2026-11-02', '2026-11', 350.00, 'Картка'),
  (38, DATE '2026-09-05', '2026-09', 350.00, 'Картка'),
  (38, DATE '2026-10-10', '2026-10', 350.00, 'Картка'),
  (39, DATE '2026-10-02', '2026-10', 350.00, 'Готівка'),
  (39, DATE '2026-11-06', '2026-11', 350.00, 'Картка'),
  (40, DATE '2026-09-07', '2026-09', 350.00, 'Картка'),
  (41, DATE '2026-09-10', '2026-09', 350.00, 'Картка'),
  (41, DATE '2026-10-11', '2026-10', 350.00, 'Картка'),
  (41, DATE '2026-11-03', '2026-11', 350.00, 'Картка'),
  (42, DATE '2026-09-02', '2026-09', 350.00, 'Готівка'),
  (42, DATE '2026-10-07', '2026-10', 350.00, 'Готівка'),
  (42, DATE '2026-11-08', '2026-11', 350.00, 'Готівка'),
  (43, DATE '2026-09-07', '2026-09', 380.00, 'Картка'),
  (43, DATE '2026-10-03', '2026-10', 380.00, 'Картка'),
  (43, DATE '2026-11-05', '2026-11', 380.00, 'Картка'),
  (44, DATE '2026-09-03', '2026-09', 380.00, 'Картка'),
  (44, DATE '2026-10-04', '2026-10', 380.00, 'Картка'),
  (44, DATE '2026-11-03', '2026-11', 380.00, 'Картка'),
  (45, DATE '2026-10-07', '2026-10', 380.00, 'Картка'),
  (45, DATE '2026-11-06', '2026-11', 380.00, 'Готівка');
INSERT INTO payment (enrollment_id, pay_date, period, amount, method) VALUES
  (46, DATE '2026-09-04', '2026-09', 266.00, 'Готівка'),
  (46, DATE '2026-10-02', '2026-10', 266.00, 'Картка'),
  (46, DATE '2026-11-08', '2026-11', 266.00, 'Картка'),
  (47, DATE '2026-09-08', '2026-09', 380.00, 'Картка'),
  (47, DATE '2026-10-08', '2026-10', 380.00, 'Картка'),
  (47, DATE '2026-11-02', '2026-11', 380.00, 'Готівка'),
  (48, DATE '2026-10-01', '2026-10', 380.00, 'Готівка'),
  (48, DATE '2026-11-06', '2026-11', 380.00, 'Картка'),
  (49, DATE '2026-09-01', '2026-09', 380.00, 'Готівка'),
  (49, DATE '2026-10-09', '2026-10', 380.00, 'Картка'),
  (49, DATE '2026-11-09', '2026-11', 380.00, 'Готівка'),
  (50, DATE '2026-09-05', '2026-09', 700.00, 'Готівка'),
  (50, DATE '2026-10-09', '2026-10', 700.00, 'Картка'),
  (50, DATE '2026-11-09', '2026-11', 700.00, 'Картка'),
  (51, DATE '2026-09-07', '2026-09', 700.00, 'Готівка'),
  (51, DATE '2026-10-06', '2026-10', 700.00, 'Готівка'),
  (51, DATE '2026-11-05', '2026-11', 700.00, 'Картка'),
  (52, DATE '2026-09-05', '2026-09', 350.00, 'Готівка'),
  (52, DATE '2026-10-03', '2026-10', 350.00, 'Картка'),
  (52, DATE '2026-11-05', '2026-11', 350.00, 'Картка'),
  (53, DATE '2026-10-02', '2026-10', 700.00, 'Картка'),
  (54, DATE '2026-09-01', '2026-09', 700.00, 'Готівка'),
  (54, DATE '2026-10-09', '2026-10', 700.00, 'Картка'),
  (54, DATE '2026-11-09', '2026-11', 700.00, 'Картка'),
  (55, DATE '2026-09-01', '2026-09', 490.00, 'Готівка'),
  (55, DATE '2026-10-12', '2026-10', 490.00, 'Готівка'),
  (55, DATE '2026-11-07', '2026-11', 490.00, 'Готівка'),
  (56, DATE '2026-09-06', '2026-09', 700.00, 'Готівка'),
  (56, DATE '2026-10-01', '2026-10', 700.00, 'Картка'),
  (56, DATE '2026-11-09', '2026-11', 700.00, 'Картка'),
  (57, DATE '2026-09-04', '2026-09', 700.00, 'Картка'),
  (57, DATE '2026-10-06', '2026-10', 700.00, 'Готівка'),
  (57, DATE '2026-11-05', '2026-11', 700.00, 'Картка'),
  (58, DATE '2026-09-08', '2026-09', 650.00, 'Картка'),
  (58, DATE '2026-10-04', '2026-10', 650.00, 'Картка'),
  (58, DATE '2026-11-07', '2026-11', 650.00, 'Картка'),
  (59, DATE '2026-09-09', '2026-09', 650.00, 'Готівка'),
  (59, DATE '2026-10-03', '2026-10', 650.00, 'Картка'),
  (59, DATE '2026-11-07', '2026-11', 650.00, 'Готівка'),
  (60, DATE '2026-09-10', '2026-09', 650.00, 'Готівка');
INSERT INTO payment (enrollment_id, pay_date, period, amount, method) VALUES
  (60, DATE '2026-10-03', '2026-10', 650.00, 'Картка'),
  (60, DATE '2026-11-06', '2026-11', 650.00, 'Картка'),
  (61, DATE '2026-09-05', '2026-09', 650.00, 'Картка'),
  (61, DATE '2026-10-04', '2026-10', 650.00, 'Готівка'),
  (61, DATE '2026-11-05', '2026-11', 650.00, 'Картка'),
  (62, DATE '2026-09-05', '2026-09', 650.00, 'Готівка'),
  (62, DATE '2026-10-08', '2026-10', 650.00, 'Картка'),
  (62, DATE '2026-11-04', '2026-11', 650.00, 'Картка'),
  (63, DATE '2026-09-02', '2026-09', 300.00, 'Готівка'),
  (63, DATE '2026-10-06', '2026-10', 300.00, 'Картка'),
  (63, DATE '2026-11-07', '2026-11', 300.00, 'Картка'),
  (64, DATE '2026-09-05', '2026-09', 300.00, 'Готівка'),
  (64, DATE '2026-10-08', '2026-10', 300.00, 'Картка'),
  (64, DATE '2026-11-08', '2026-11', 300.00, 'Картка'),
  (65, DATE '2026-09-10', '2026-09', 300.00, 'Картка'),
  (65, DATE '2026-10-08', '2026-10', 300.00, 'Готівка'),
  (65, DATE '2026-11-07', '2026-11', 300.00, 'Картка'),
  (66, DATE '2026-09-02', '2026-09', 300.00, 'Картка'),
  (66, DATE '2026-10-09', '2026-10', 300.00, 'Картка'),
  (66, DATE '2026-11-08', '2026-11', 300.00, 'Картка'),
  (67, DATE '2026-09-09', '2026-09', 300.00, 'Готівка'),
  (67, DATE '2026-10-07', '2026-10', 300.00, 'Картка'),
  (67, DATE '2026-11-03', '2026-11', 300.00, 'Готівка'),
  (68, DATE '2026-09-03', '2026-09', 300.00, 'Картка'),
  (68, DATE '2026-10-11', '2026-10', 300.00, 'Картка'),
  (68, DATE '2026-11-02', '2026-11', 300.00, 'Картка'),
  (69, DATE '2026-09-08', '2026-09', 300.00, 'Картка'),
  (69, DATE '2026-10-01', '2026-10', 300.00, 'Картка'),
  (69, DATE '2026-11-08', '2026-11', 300.00, 'Готівка'),
  (70, DATE '2026-09-09', '2026-09', 300.00, 'Картка'),
  (70, DATE '2026-10-05', '2026-10', 300.00, 'Картка'),
  (70, DATE '2026-11-09', '2026-11', 300.00, 'Картка'),
  (71, DATE '2026-09-09', '2026-09', 210.00, 'Картка'),
  (71, DATE '2026-10-09', '2026-10', 210.00, 'Готівка'),
  (71, DATE '2026-11-07', '2026-11', 210.00, 'Готівка'),
  (72, DATE '2026-09-06', '2026-09', 300.00, 'Картка'),
  (72, DATE '2026-10-12', '2026-10', 300.00, 'Картка'),
  (72, DATE '2026-11-09', '2026-11', 300.00, 'Картка'),
  (73, DATE '2026-09-10', '2026-09', 450.00, 'Картка'),
  (73, DATE '2026-10-04', '2026-10', 450.00, 'Готівка');
INSERT INTO payment (enrollment_id, pay_date, period, amount, method) VALUES
  (73, DATE '2026-11-03', '2026-11', 450.00, 'Картка'),
  (74, DATE '2026-09-01', '2026-09', 450.00, 'Готівка'),
  (74, DATE '2026-10-10', '2026-10', 450.00, 'Картка'),
  (74, DATE '2026-11-08', '2026-11', 450.00, 'Картка'),
  (75, DATE '2026-09-01', '2026-09', 450.00, 'Готівка'),
  (75, DATE '2026-10-07', '2026-10', 450.00, 'Готівка'),
  (75, DATE '2026-11-05', '2026-11', 450.00, 'Картка'),
  (76, DATE '2026-10-09', '2026-10', 450.00, 'Картка'),
  (77, DATE '2026-10-07', '2026-10', 450.00, 'Картка'),
  (77, DATE '2026-11-03', '2026-11', 450.00, 'Готівка'),
  (78, DATE '2026-09-04', '2026-09', 450.00, 'Картка'),
  (78, DATE '2026-10-01', '2026-10', 450.00, 'Картка'),
  (78, DATE '2026-11-07', '2026-11', 450.00, 'Картка'),
  (79, DATE '2026-09-06', '2026-09', 315.00, 'Картка'),
  (79, DATE '2026-10-12', '2026-10', 315.00, 'Картка'),
  (79, DATE '2026-11-07', '2026-11', 315.00, 'Картка'),
  (80, DATE '2026-09-04', '2026-09', 450.00, 'Картка'),
  (80, DATE '2026-10-11', '2026-10', 450.00, 'Картка'),
  (80, DATE '2026-11-07', '2026-11', 450.00, 'Картка'),
  (81, DATE '2026-09-05', '2026-09', 225.00, 'Готівка'),
  (81, DATE '2026-10-03', '2026-10', 225.00, 'Картка'),
  (81, DATE '2026-11-02', '2026-11', 225.00, 'Картка'),
  (82, DATE '2026-10-05', '2026-10', 500.00, 'Картка'),
  (82, DATE '2026-11-03', '2026-11', 500.00, 'Картка'),
  (83, DATE '2026-10-05', '2026-10', 350.00, 'Картка'),
  (83, DATE '2026-11-04', '2026-11', 350.00, 'Картка');
