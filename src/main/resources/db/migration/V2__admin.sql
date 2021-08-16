CREATE TABLE IF NOT EXISTS site_admin
(
    id       SERIAL PRIMARY KEY,
    login     VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

INSERT INTO site_admin (login, password)
VALUES ('admin', '$2y$12$5zd6jp0zTVm/YWFbZrANk.UmJNZP4ClUHOnJxtywaWFoTngUTSoyq')