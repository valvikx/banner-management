INSERT INTO category (name, req_name, deleted)
VALUES ('Humor', 'humor', false),
       ('Music', 'music', false),
       ('Arcade games', 'arcade-games', false);

INSERT INTO banner (name, price, category_id, content, deleted)
VALUES ('First humor banner', 150.00, (SELECT id FROM category WHERE name = 'Humor'),
        'first humor banner content 150.00', false),
       ('Second humor banner', 150.00, (SELECT id FROM category WHERE name = 'Humor'),
        'second humor banner content 150.00', false),
       ('Third humor banner', 70.00, (SELECT id FROM category WHERE name = 'Humor'),
        'third humor banner content 70.00', false),

       ('First music banner', 100.00, (SELECT id FROM category WHERE name = 'Music'),
        'first music banner content 100.00', false),
       ('Second music banner', 70.00, (SELECT id FROM category WHERE name = 'Music'),
        'second music banner content 70.00', false),
       ('Third music banner', 50.00, (SELECT id FROM category WHERE name = 'Music'),
        'third music banner content 50.00', false),
       ('Fourth music banner', 50.00, (SELECT id FROM category WHERE name = 'Music'),
        'fourth music banner content 50.00', false),

       ('First arcade games banner', 250.00, (SELECT id FROM category WHERE name = 'Arcade games'),
       'first arcade games banner 250.00', false);

INSERT INTO request (banner_id, user_agent, ip_address, date)
VALUES ((SELECT id FROM banner WHERE name = 'First music banner'),
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.67',
        '127.0.0.1',
        now());

INSERT INTO request (banner_id, user_agent, ip_address, date)
VALUES ((SELECT id FROM banner WHERE name = 'Second music banner'),
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.67',
        '127.0.0.1',
        now() + interval '1 hour');

INSERT INTO site_admin (login, password)
VALUES ('admin', '$2y$12$5zd6jp0zTVm/YWFbZrANk.UmJNZP4ClUHOnJxtywaWFoTngUTSoyq')
