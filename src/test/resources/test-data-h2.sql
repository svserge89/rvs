INSERT INTO user (id, nick_name, first_name, last_name, email, encrypted_password) VALUES
    (1, 'admin', NULL, NULL, 'admin@mail.com',
     '$2a$10$OMKFjezdspPGMIVkpFLf6.TulEAyk09yHg6sHnpXJJx0wtY0tgGQG'),
    (2, 'user_1', 'first_name_1', 'last_name_1', 'user1@mail.com',
     '$2a$10$OMKFjezdspPGMIVkpFLf6.TulEAyk09yHg6sHnpXJJx0wtY0tgGQG'),
    (3, 'user_2', 'first_name_2', 'last_name_2', 'user2@mail.com',
     '$2a$10$OMKFjezdspPGMIVkpFLf6.TulEAyk09yHg6sHnpXJJx0wtY0tgGQG');

INSERT INTO user_role (user_id, role) VALUES
    (1, 'ROLE_ADMIN'),
    (1, 'ROLE_USER'),
    (2, 'ROLE_USER'),
    (3, 'ROLE_USER');

INSERT INTO restaurant (id, name) VALUES
    (1, 'restaurant_1'),
    (2, 'restaurant_2'),
    (3, 'restaurant_3');

INSERT INTO menu_entry (id, name, price, date, restaurant_id) VALUES
    (111, 'r1_menu_entry_1', 50.0, DATEADD('DAY', -2, CURRENT_DATE), 1),
    (112, 'r1_menu_entry_2', 10.5, DATEADD('DAY', -2, CURRENT_DATE), 1),
    (113, 'r1_menu_entry_3', 50.0, DATEADD('DAY', -2, CURRENT_DATE), 1),
    (121, 'r2_menu_entry_1', 50.0, DATEADD('DAY', -2, CURRENT_DATE), 2),
    (122, 'r2_menu_entry_2', 10.5, DATEADD('DAY', -2, CURRENT_DATE), 2),
    (123, 'r2_menu_entry_3', 50.0, DATEADD('DAY', -2, CURRENT_DATE), 2),

    (211, 'r1_menu_entry_1', 50.0, DATEADD('DAY', -1, CURRENT_DATE), 1),
    (212, 'r1_menu_entry_2', 10.5, DATEADD('DAY', -1, CURRENT_DATE), 1),
    (213, 'r1_menu_entry_3', 50.0, DATEADD('DAY', -1, CURRENT_DATE), 1),
    (221, 'r2_menu_entry_1', 50.0, DATEADD('DAY', -1, CURRENT_DATE), 2),
    (222, 'r2_menu_entry_2', 10.5, DATEADD('DAY', -1, CURRENT_DATE), 2),
    (223, 'r2_menu_entry_3', 50.0, DATEADD('DAY', -1, CURRENT_DATE), 2),

    (311, 'r1_menu_entry_1', 50.0, CURRENT_DATE, 1),
    (312, 'r1_menu_entry_2', 10.5, CURRENT_DATE, 1),
    (313, 'r1_menu_entry_3', 50.0, CURRENT_DATE, 1),
    (321, 'r2_menu_entry_1', 50.0, CURRENT_DATE, 2),
    (322, 'r2_menu_entry_2', 10.5, CURRENT_DATE, 2),
    (323, 'r2_menu_entry_3', 50.0, CURRENT_DATE, 2);

INSERT INTO vote_entry (user_id, restaurant_id, date, time) VALUES
    (1, 1, DATEADD('DAY', -2, CURRENT_DATE), '14:00'),
    (2, 1, DATEADD('DAY', -2, CURRENT_DATE), '15:30'),

    (2, 3, DATEADD('DAY', -1, CURRENT_DATE), '12:00'),
    (3, 3, DATEADD('DAY', -1, CURRENT_DATE), '12:30'),

    (1, 1, CURRENT_DATE, '8:00'),
    (2, 2, CURRENT_DATE, '9:00');