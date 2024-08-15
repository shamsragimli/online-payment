INSERT INTO user_authorities (user_id, authority_name)
VALUES ((SELECT id FROM users WHERE username = 'admin'), (SELECT name FROM authorities WHERE name = 'ADMIN'));

