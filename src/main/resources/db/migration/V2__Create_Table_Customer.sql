CREATE TABLE IF NOT EXISTS customer (
    id UUID /* [jooq ignore start] */ DEFAULT uuid_generate_v4() /* [jooq ignore stop] */ NOT NULL PRIMARY KEY,
    name VARCHAR,
    cpf VARCHAR UNIQUE,
    email VARCHAR,
    phone VARCHAR,
    birth_date DATE,
    created_at TIMESTAMP DEFAULT NOW()
);

/* [jooq ignore start] */
INSERT INTO customer (id, name, cpf, email, phone, birth_date) VALUES (uuid_generate_v4(),'Priscila Carvalho','74531863666','pri.carvalho86@gmail.com','(11)99999-8765', '1986-09-12' );
/* [jooq ignore stop] */