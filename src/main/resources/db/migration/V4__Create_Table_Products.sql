CREATE TABLE IF NOT EXISTS product (
    id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    name VARCHAR,
    description VARCHAR,
    price DECIMAL(6,2),
    category_id UUID NOT NULL REFERENCES category(id),
    image_url VARCHAR NOT NULL,
    preparation_time NUMERIC,
    created_at TIMESTAMP DEFAULT NOW()
);

/* Lanches */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Salada', 'Hamburger 90g, alface, tomate, queijo, maionese da casa', '29.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', 'http://localhost:8080/images/x-salada.jpg', '30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Egg', 'Hamburger 90g, ovo, queijo, maionese da casa', '29.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', 'http://localhost:8080/images/x-egg.jpg', '30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Burger', 'Hamburger 90g, queijo, maionese da casa', '25.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', 'http://localhost:8080/images/x-burger.jpg','30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Tudo', 'Hamburger 90g, ovo, alface, tomate, queijo, bacon, maionese da casa', '49.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', 'http://localhost:8080/images/x-tudo.jpg', '30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Cachorro Quente', 'Pão de leite ou frances, 2 salsichas, vinagrete, repolho, milho, ervilha, purê, mostarda, catchup, catupiry, cheddar e queijo ralado', '19.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', 'http://localhost:8080/images/cachorro-quente.jpg', '30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Vegano', 'Hamburger de soja 90g, alface, tomate, milho, ervilha, maionese da casa', '39.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', 'http://localhost:8080/images/x-vegano.jpg', '30');

/* Bebidas */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Refrigerante (Lata)', 'Coca-cola, Guaraná Antartica, Fanta Laranja, Fanta Uva ou Sprite', '9.90', '2faf0f51-d925-4888-9230-d543d9ffe261', 'http://localhost:8080/images/refrigerante-lata.jpg', '1');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Suco Natural', 'Laranja, Manga, Uva, Abacaxi com Hortelã, Limão e Maracujá', '10.90', '2faf0f51-d925-4888-9230-d543d9ffe261', 'http://localhost:8080/images/suco-natural.jpg', '1');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Água', 'Água mineral 500ml', '5.90', '2faf0f51-d925-4888-9230-d543d9ffe261', '1');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Cerveja (Lata)', '13.90', '2faf0f51-d925-4888-9230-d543d9ffe261', 'http://localhost:8080/images/agua-mineral.jpg', '1');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Milkshake', 'Chocolate, Morango, Ovomaltine, Oreo','18.90', '2faf0f51-d925-4888-9230-d543d9ffe261', 'http://localhost:8080/images/milkshake.jpg' ,'1');

/* Acompanhamentos */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Batata Frita', 'Batata Frita simples', '9.90', 'abdf39f5-1b3c-46b1-8031-05267092129d', 'http://localhost:8080/images/batata-frita.jpg','15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Batata Frita com Bacon e Cheddar', 'Deliciosa Batata frita coberta com Bacon e Cheddar', '10.90', 'abdf39f5-1b3c-46b1-8031-05267092129d', 'http://localhost:8080/images/batata-frita.jpg', '15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Onion Rings', 'A nossa famosa cebola empanada frita', '5.90', 'abdf39f5-1b3c-46b1-8031-05267092129d', 'http://localhost:8080/images/onion-rings.jpg', '15');

/* Sobremesas */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Picolé', 'Chocolate, Morango, Uva, Limão, Abacaxi, Leite Condensado', '5.90', 'c27ea353-1068-42d0-8514-f57aac9965bd', 'http://localhost:8080/images/picole.jpg', '15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Casquinha', 'Chocolate, Creme ou Mista', '3.90', 'http://localhost:8080/images/picole.jpg', 'c27ea353-1068-42d0-8514-f57aac9965bd', '15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Sunday', 'Sorvete 150g com Calda de Chocolate, Caramelo ou Morango, com Sorvete de Creme com farelo de amendoin', '10.90', 'c27ea353-1068-42d0-8514-f57aac9965bd', 'http://localhost:8080/images/sunday.jpg', '15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Top Sunday', 'Sorvete 400g com Calda de Chocolate, Caramelo ou Morango, com Sorvete de Creme com farelo de amendoin''15.90', 'c27ea353-1068-42d0-8514-f57aac9965bd', 'http://localhost:8080/images/top-sunday.jpg', '15');

/* Combos */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Salada (Fritas + Bebida)', 'Combo X-Salada (Fritas + Bebida)', '55.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', '45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Egg (Fritas + Bebida)', 'Combo X-Egg (Fritas + Bebida)', '55.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', 'http://localhost:8080/images/sunday.jpg', '45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Tudo(Fritas + Bebida)', 'Combo X-Tudo(Fritas + Bebida)', '79.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', 'http://localhost:8080/images/sunday.jpg', '45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Burger (Fritas + Bebida)', 'Combo X-Burger (Fritas + Bebida)', '45.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', 'http://localhost:8080/images/sunday.jpg', '45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo Cachorro-quente (Fritas + Bebida)', 'Combo Cachorro-quente (Fritas + Bebida)', '55.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', 'http://localhost:8080/images/sunday.jpg','45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Vegano (Fritas + Bebida)', 'Combo X-Vegano (Fritas + Bebida)', '65.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', 'http://localhost:8080/images/sunday.jpg', '45');