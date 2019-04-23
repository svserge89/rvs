DELETE FROM user;
INSERT INTO user (id, nick_name, first_name, last_name, email) VALUES
    (1, 'admin', NULL, NULL, 'admin@mail.com'),
    (2, 'user_1', 'first_name_1', 'last_name_1', 'user1@mail.com'),
    (3, 'user_2', 'first_name_2', 'last_name_2', 'user2@mail.com');

DELETE FROM user_role;
INSERT INTO user_role (user_id, role) VALUES
    (1, 'ROLE_ADMIN'),
    (1, 'ROLE_USER'),
    (2, 'ROLE_USER'),
    (3, 'ROLE_USER');

DELETE FROM restaurant;
INSERT INTO restaurant (id, name) VALUES
    (1, 'restaurant_1'),
    (2, 'restaurant_2'),
    (3, 'restaurant_3');

DELETE FROM menu_entry;
INSERT INTO menu_entry (id, name, price, restaurant_id) VALUES
    (1, 'menu_entry_1', 50.0, 1),
    (2, 'menu_entry_2', 10.50, 1),
    (3, 'menu_entry_3', 25.20, 1),
    (4, 'menu_entry_1', 20.20, 2),
    (5, 'menu_entry_2', 10.00, 2),
    (6, 'menu_entry_3', 40.00, 2),
    (7, 'menu_entry_1', 1.50, 3),
    (8, 'menu_entry_2', 30.00, 3),
    (9, 'menu_entry_3', 90.50, 3);

DELETE FROM vote_entry;
INSERT INTO vote_entry (user_id, restaurant_id) VALUES
    (1, 1),
    (2, 1),
    (3, 2);