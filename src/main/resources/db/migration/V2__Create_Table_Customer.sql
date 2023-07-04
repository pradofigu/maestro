CREATE TABLE IF NOT EXISTS customer (
    id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    name VARCHAR,
    cpf VARCHAR UNIQUE,
    email VARCHAR,
    phone VARCHAR,
    birth_date DATE,
    created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO customer (id, name, cpf, email, phone, birth_date) VALUES (uuid_generate_v4(),'Priscila Carvalho','74531863666','pri.carvalho86@gmail.com','(11)99999-8765', '1986-09-12' );