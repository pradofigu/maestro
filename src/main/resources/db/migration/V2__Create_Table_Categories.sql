CREATE TABLE IF NOT EXISTS maestro.category (
    id uuid DEFAULT uuid_generate_v4() NOT NULL,
    name VARCHAR UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (id)
);

INSERT INTO maestro.category (name) VALUES ('Lanche');
INSERT INTO maestro.category (name) VALUES ('Acompanhamento');
INSERT INTO maestro.category (name) VALUES ('Bebida');
INSERT INTO maestro.category (name) VALUES ('Sobremesa');
INSERT INTO maestro.category (name) VALUES ('Combo');