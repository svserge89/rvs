DROP TABLE IF EXISTS vote_entry;
DROP TABLE IF EXISTS menu_entry;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id LONG PRIMARY KEY AUTO_INCREMENT,
    nick_name VARCHAR(60) UNIQUE NOT NULL,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    email VARCHAR(60) UNIQUE NOT NULL,
    CHECK (TRIM(nick_name) <> ''),
    CHECK (email REGEXP '^[A-Za-z0-9._%\-+!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);

CREATE TABLE user_role (
    user_id LONG REFERENCES user(id) ON DELETE CASCADE,
    role VARCHAR(10) NOT NULL,
    UNIQUE (user_id, role),
    CHECK (role IN ('ROLE_USER', 'ROLE_ADMIN'))
);

CREATE TABLE restaurant (
    id LONG PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL UNIQUE,
    CHECK (TRIM(name) <> '')
);

CREATE TABLE menu_entry (
    id LONG PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    price DOUBLE NOT NULL,
    date DATE NOT NULL DEFAULT NOW(),
    restaurant_id LONG REFERENCES restaurant(id) ON DELETE CASCADE,
    UNIQUE (name, date, restaurant_id),
    CHECK (TRIM(name) <> '')
);

CREATE TABLE vote_entry (
    user_id LONG REFERENCES user(id) ON DELETE CASCADE,
    restaurant_id LONG REFERENCES restaurant(id) ON DELETE CASCADE,
    date DATE NOT NULL DEFAULT NOW(),
    time TIME NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, date)
);