INSERT INTO tb_user (name, email, password) VALUES ('Bob Brown', 'bob@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, password) VALUES ('Ana Maria', 'ana@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_VISITOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_MEMBER');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_genre (name) VALUES ('Terror');
INSERT INTO tb_genre (name) VALUES ('Ação');
INSERT INTO tb_genre (name) VALUES ('Drama');
INSERT INTO tb_genre (name) VALUES ('Suspense');

INSERT INTO tb_movie (title, sub_title, year, img_url, synopsis, genre_id) VALUES ('Homem-Aranha', 'Através do Aranhaverso', 2023, 'https://cdn.pixabay.com/photo/2018/03/22/10/55/training-course-3250007_1280.jpg', 'Miles Morales tenta redefinir o que é ser Homem-Aranha quando uma nova ameaça surge no Aranhaverso', 2);
INSERT INTO tb_movie (title, sub_title, year, img_url, synopsis, genre_id) VALUES ('A Noite das Bruxas', 'A Haunting in Venice ', 2023, 'https://cdn.pixabay.com/photo/2018/03/22/10/55/training-course-3250007_1280.jpg', 'O detetive aposentado Poirot acaba em uma investigação sobrenatural em Veneza', 1);
INSERT INTO tb_movie (title, sub_title, year, img_url, synopsis, genre_id) VALUES ('Assassinos da Lua das Flores', 'AKillers of the Flower Moon', 2023, 'https://cdn.pixabay.com/photo/2018/03/22/10/55/training-course-3250007_1280.jpg', 'Durante os anos de 1920, nas terras ricas em petróleo da Nação Osage, no estado norte-americano', 3);
INSERT INTO tb_movie (title, sub_title, year, img_url, synopsis, genre_id) VALUES ('Ilha do Medo', 'Shutter Island', 2010, 'https://cdn.pixabay.com/photo/2018/03/22/10/55/training-course-3250007_1280.jpg', '1954, o pico da Guerra Fria, os agentes Teddy Daniels e Chuck Aule são convocados a "Shutter Island" para investigar', 4);

INSERT INTO tb_review (user_id, movie_id, text) VALUES (1, 1, 'Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá');
INSERT INTO tb_review (user_id, movie_id, text) VALUES (1, 2, 'Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá');
INSERT INTO tb_review (user_id, movie_id, text) VALUES (2, 3, 'Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá');
INSERT INTO tb_review (user_id, movie_id, text) VALUES (2, 4, 'Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá Blá');

