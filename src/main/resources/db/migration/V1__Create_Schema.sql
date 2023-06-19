CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.modified = now();
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE SCHEMA IF NOT EXISTS maestro;

CREATE TABLE IF NOT EXISTS maestro.customer (
    id uuid DEFAULT uuid_generate_v4() NOT NULL,
    name VARCHAR,
    cpf VARCHAR,
    email VARCHAR,
    phone VARCHAR,
    birth_date DATE,
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (id)
);

INSERT INTO maestro.customer (id, name, cpf, email, phone, birth_date) VALUES ('574e7d5d-bf46-449d-9307-4f263362dec8','Priscila Carvalho','12345678910','pri.carvalho86@gmail.com','(11)99999-8765', '1986-09-12' );