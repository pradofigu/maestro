CREATE TABLE IF NOT EXISTS product (
    id UUID /* [jooq ignore start] */ DEFAULT uuid_generate_v4() /* [jooq ignore stop] */ NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    price DECIMAL(6,2) NOT NULL,
    category_id UUID NOT NULL REFERENCES category(id),
    preparation_time NUMERIC NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

/* [jooq ignore start] */
/* Lanches */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Salada', '29.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', '30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Egg', '29.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', '30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Burger', '25.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', '30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Tudo', '49.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', '30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Cachorro Quente', '19.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', '30');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Vegano', '39.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', '30');

/* Bebidas */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Refrigerante (Lata)', '9.90', '2faf0f51-d925-4888-9230-d543d9ffe261', '1');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Suco Natural', '10.90', '2faf0f51-d925-4888-9230-d543d9ffe261', '1');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Água', '5.90', '2faf0f51-d925-4888-9230-d543d9ffe261', '1');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Cerveja (Lata)', '13.90', '2faf0f51-d925-4888-9230-d543d9ffe261', '1');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Milkshake', '18.90', '2faf0f51-d925-4888-9230-d543d9ffe261', '1');

/* Acompanhamentos */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Batata Frita', '9.90', 'abdf39f5-1b3c-46b1-8031-05267092129d', '15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Batata Frita com Bacon e Cheddar', '10.90', 'abdf39f5-1b3c-46b1-8031-05267092129d', '15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Onion Rings', '5.90', 'abdf39f5-1b3c-46b1-8031-05267092129d', '15');

/* Sobremesas */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Picolé', '5.90', 'c27ea353-1068-42d0-8514-f57aac9965bd', '15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Casquinha', '3.90', 'c27ea353-1068-42d0-8514-f57aac9965bd', '15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Sunday', '10.90', 'c27ea353-1068-42d0-8514-f57aac9965bd', '15');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Top Sunday', '15.90', 'c27ea353-1068-42d0-8514-f57aac9965bd', '15');

/* Combos */
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Salada (Fritas + Bebida)', '55.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', '45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Egg (Fritas + Bebida)', '55.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', '45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Tudo(Fritas + Bebida)', '79.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', '45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Burger (Fritas + Bebida)', '45.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', '45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo Cachorro-quente (Fritas + Bebida)', '55.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', '45');
INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'Combo X-Vegano (Fritas + Bebida)', '65.90', '507c1aeb-4aca-476f-a175-b41392ddc10a', '45');
/* [jooq ignore stop] */