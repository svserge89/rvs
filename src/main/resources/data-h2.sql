INSERT INTO user (id, nick_name, first_name, last_name, email, encrypted_password) VALUES
    (1, 'admin', NULL, NULL, 'admin@mail.com',
     '{bcrypt}$2a$10$OMKFjezdspPGMIVkpFLf6.TulEAyk09yHg6sHnpXJJx0wtY0tgGQG'),
    (2, 'user_1', 'first_name_1', 'last_name_1', 'user1@mail.com',
     '{bcrypt}$2a$10$OMKFjezdspPGMIVkpFLf6.TulEAyk09yHg6sHnpXJJx0wtY0tgGQG'),
    (3, 'user_2', 'first_name_2', 'last_name_2', 'user2@mail.com',
     '{bcrypt}$2a$10$OMKFjezdspPGMIVkpFLf6.TulEAyk09yHg6sHnpXJJx0wtY0tgGQG'),
    (4, 'user_3', NULL, 'last_name_3', 'user3@mail.com',
     '{bcrypt}$2a$10$OMKFjezdspPGMIVkpFLf6.TulEAyk09yHg6sHnpXJJx0wtY0tgGQG'),
    (5, 'user_4', 'first_name_4', NULL, 'user4@mail.com',
     '{bcrypt}$2a$10$OMKFjezdspPGMIVkpFLf6.TulEAyk09yHg6sHnpXJJx0wtY0tgGQG'),
    (6, 'user_5', 'first_name_5', 'last_name_5', 'user5@mail.com',
     '{bcrypt}$2a$10$OMKFjezdspPGMIVkpFLf6.TulEAyk09yHg6sHnpXJJx0wtY0tgGQG');

INSERT INTO user_role (user_id, role) VALUES
    (1, 'ROLE_ADMIN'),
    (1, 'ROLE_USER'),
    (2, 'ROLE_USER'),
    (3, 'ROLE_USER'),
    (4, 'ROLE_USER'),
    (5, 'ROLE_USER');

INSERT INTO restaurant (id, name) VALUES
    (1, 'restaurant_1'),
    (2, 'restaurant_2'),
    (3, 'restaurant_3'),
    (4, 'restaurant_4'),
    (5, 'restaurant_5'),
    (6, 'restaurant_6');

INSERT INTO menu_entry (id, name, price, date, restaurant_id) VALUES
    (111, 'r1_menu_entry_1', 50.0, DATEADD('DAY', -2, CURRENT_DATE), 1),
    (112, 'r1_menu_entry_2', 10.5, DATEADD('DAY', -2, CURRENT_DATE), 1),
    (113, 'r1_menu_entry_3', 25.2, DATEADD('DAY', -2, CURRENT_DATE), 1),
    (114, 'r1_menu_entry_4', 15.4, DATEADD('DAY', -2, CURRENT_DATE), 1),
    (115, 'r1_menu_entry_5', 5.25, DATEADD('DAY', -2, CURRENT_DATE), 1),
    (121, 'r2_menu_entry_1', 20.2, DATEADD('DAY', -2, CURRENT_DATE), 2),
    (122, 'r2_menu_entry_2', 10, DATEADD('DAY', -2, CURRENT_DATE), 2),
    (123, 'r2_menu_entry_3', 40, DATEADD('DAY', -2, CURRENT_DATE), 2),
    (124, 'r2_menu_entry_4', 4.05, DATEADD('DAY', -2, CURRENT_DATE), 2),
    (125, 'r2_menu_entry_5', 35.5, DATEADD('DAY', -2, CURRENT_DATE), 2),
    (131, 'r3_menu_entry_1', 11.2, DATEADD('DAY', -2, CURRENT_DATE), 3),
    (132, 'r3_menu_entry_2', 15.4, DATEADD('DAY', -2, CURRENT_DATE), 3),
    (133, 'r3_menu_entry_3', 99, DATEADD('DAY', -2, CURRENT_DATE), 3),
    (134, 'r3_menu_entry_4', 54.5, DATEADD('DAY', -2, CURRENT_DATE), 3),
    (135, 'r3_menu_entry_5', 22, DATEADD('DAY', -2, CURRENT_DATE), 3),
    (141, 'r4_menu_entry_1', 10.2, DATEADD('DAY', -2, CURRENT_DATE), 4),
    (142, 'r4_menu_entry_2', 87.1, DATEADD('DAY', -2, CURRENT_DATE), 4),
    (143, 'r4_menu_entry_3', 44, DATEADD('DAY', -2, CURRENT_DATE), 4),
    (144, 'r4_menu_entry_4', 43.7, DATEADD('DAY', -2, CURRENT_DATE), 4),
    (145, 'r4_menu_entry_5', 55.4, DATEADD('DAY', -2, CURRENT_DATE), 4),
    (151, 'r5_menu_entry_1', 45.7, DATEADD('DAY', -2, CURRENT_DATE), 5),
    (152, 'r5_menu_entry_2', 66, DATEADD('DAY', -2, CURRENT_DATE), 5),
    (153, 'r5_menu_entry_3', 80.1, DATEADD('DAY', -2, CURRENT_DATE), 5),
    (154, 'r5_menu_entry_4', 22.7, DATEADD('DAY', -2, CURRENT_DATE), 5),
    (155, 'r5_menu_entry_5', 99.9, DATEADD('DAY', -2, CURRENT_DATE), 5),

    (211, 'r1_menu_entry_1', 50.0, DATEADD('DAY', -1, CURRENT_DATE), 1),
    (212, 'r1_menu_entry_2', 10.5, DATEADD('DAY', -1, CURRENT_DATE), 1),
    (213, 'r1_menu_entry_3', 25.2, DATEADD('DAY', -1, CURRENT_DATE), 1),
    (214, 'r1_menu_entry_4', 15.4, DATEADD('DAY', -1, CURRENT_DATE), 1),
    (215, 'r1_menu_entry_5', 5.25, DATEADD('DAY', -1, CURRENT_DATE), 1),
    (221, 'r2_menu_entry_1', 20.2, DATEADD('DAY', -1, CURRENT_DATE), 2),
    (222, 'r2_menu_entry_2', 10, DATEADD('DAY', -1, CURRENT_DATE), 2),
    (223, 'r2_menu_entry_3', 40, DATEADD('DAY', -1, CURRENT_DATE), 2),
    (224, 'r2_menu_entry_4', 4.05, DATEADD('DAY', -1, CURRENT_DATE), 2),
    (225, 'r2_menu_entry_5', 35.5, DATEADD('DAY', -1, CURRENT_DATE), 2),
    (231, 'r3_menu_entry_1', 11.2, DATEADD('DAY', -1, CURRENT_DATE), 3),
    (232, 'r3_menu_entry_2', 15.4, DATEADD('DAY', -1, CURRENT_DATE), 3),
    (233, 'r3_menu_entry_3', 99, DATEADD('DAY', -1, CURRENT_DATE), 3),
    (234, 'r3_menu_entry_4', 54.5, DATEADD('DAY', -1, CURRENT_DATE), 3),
    (235, 'r3_menu_entry_5', 22, DATEADD('DAY', -1, CURRENT_DATE), 3),
    (241, 'r4_menu_entry_1', 10.2, DATEADD('DAY', -1, CURRENT_DATE), 4),
    (242, 'r4_menu_entry_2', 87.1, DATEADD('DAY', -1, CURRENT_DATE), 4),
    (243, 'r4_menu_entry_3', 44, DATEADD('DAY', -1, CURRENT_DATE), 4),
    (244, 'r4_menu_entry_4', 43.7, DATEADD('DAY', -1, CURRENT_DATE), 4),
    (245, 'r4_menu_entry_5', 55.4, DATEADD('DAY', -1, CURRENT_DATE), 4),
    (251, 'r5_menu_entry_1', 45.7, DATEADD('DAY', -1, CURRENT_DATE), 5),
    (252, 'r5_menu_entry_2', 66, DATEADD('DAY', -1, CURRENT_DATE), 5),
    (253, 'r5_menu_entry_3', 80.1, DATEADD('DAY', -1, CURRENT_DATE), 5),
    (254, 'r5_menu_entry_4', 22.7, DATEADD('DAY', -1, CURRENT_DATE), 5),
    (255, 'r5_menu_entry_5', 99.9, DATEADD('DAY', -1, CURRENT_DATE), 5),

    (311, 'r1_menu_entry_1', 50.0, CURRENT_DATE, 1),
    (312, 'r1_menu_entry_2', 10.5, CURRENT_DATE, 1),
    (313, 'r1_menu_entry_3', 25.2, CURRENT_DATE, 1),
    (314, 'r1_menu_entry_4', 15.4, CURRENT_DATE, 1),
    (315, 'r1_menu_entry_5', 5.25, CURRENT_DATE, 1),
    (321, 'r2_menu_entry_1', 20.2, CURRENT_DATE, 2),
    (322, 'r2_menu_entry_2', 10, CURRENT_DATE, 2),
    (323, 'r2_menu_entry_3', 40, CURRENT_DATE, 2),
    (324, 'r2_menu_entry_4', 4.05, CURRENT_DATE, 2),
    (325, 'r2_menu_entry_5', 35.5, CURRENT_DATE, 2),
    (331, 'r3_menu_entry_1', 11.2, CURRENT_DATE, 3),
    (332, 'r3_menu_entry_2', 15.4, CURRENT_DATE, 3),
    (333, 'r3_menu_entry_3', 99, CURRENT_DATE, 3),
    (334, 'r3_menu_entry_4', 54.5, CURRENT_DATE, 3),
    (335, 'r3_menu_entry_5', 22, CURRENT_DATE, 3),
    (341, 'r4_menu_entry_1', 10.2, CURRENT_DATE, 4),
    (342, 'r4_menu_entry_2', 87.1, CURRENT_DATE, 4),
    (343, 'r4_menu_entry_3', 44, CURRENT_DATE, 4),
    (344, 'r4_menu_entry_4', 43.7, CURRENT_DATE, 4),
    (345, 'r4_menu_entry_5', 55.4, CURRENT_DATE, 4),
    (351, 'r5_menu_entry_1', 45.7, CURRENT_DATE, 5),
    (352, 'r5_menu_entry_2', 66, CURRENT_DATE, 5),
    (353, 'r5_menu_entry_3', 80.1, CURRENT_DATE, 5),
    (354, 'r5_menu_entry_4', 22.7, CURRENT_DATE, 5),
    (355, 'r5_menu_entry_5', 99.9, CURRENT_DATE, 5);

INSERT INTO vote_entry (user_id, restaurant_id, date) VALUES
    (1, 1, DATEADD('DAY', -2, CURRENT_DATE)),
    (2, 1, DATEADD('DAY', -2, CURRENT_DATE)),
    (3, 5, DATEADD('DAY', -2, CURRENT_DATE)),
    (4, 2, DATEADD('DAY', -2, CURRENT_DATE)),
    (5, 2, DATEADD('DAY', -2, CURRENT_DATE)),

    (1, 5, DATEADD('DAY', -1, CURRENT_DATE)),
    (2, 3, DATEADD('DAY', -1, CURRENT_DATE)),
    (3, 3, DATEADD('DAY', -1, CURRENT_DATE)),
    (4, 3, DATEADD('DAY', -1, CURRENT_DATE)),
    (5, 5, DATEADD('DAY', -1, CURRENT_DATE)),

    (1, 1, CURRENT_DATE),
    (2, 1, CURRENT_DATE),
    (3, 2, CURRENT_DATE),
    (4, 3, CURRENT_DATE),
    (5, 5, CURRENT_DATE);