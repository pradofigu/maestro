CREATE TABLE IF NOT EXISTS maestro.category (
    id uuid DEFAULT uuid_generate_v4() NOT NULL,
    name VARCHAR UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (id)
);

INSERT INTO maestro.category (id, name) VALUES ('c85b8201-29c4-495a-be86-7dd3a1d16b81', 'Lanche');
INSERT INTO maestro.category (id, name) VALUES ('abdf39f5-1b3c-46b1-8031-05267092129d', 'Acompanhamento');
INSERT INTO maestro.category (id, name) VALUES ('2faf0f51-d925-4888-9230-d543d9ffe261', 'Bebida');
INSERT INTO maestro.category (id, name) VALUES ('c27ea353-1068-42d0-8514-f57aac9965bd', 'Sobremesa');
INSERT INTO maestro.category (id, name) VALUES ('507c1aeb-4aca-476f-a175-b41392ddc10a', 'Combo');