
CREATE TABLE IF NOT EXISTS maestro.product (
    id uuid DEFAULT uuid_generate_v4() NOT NULL,
    name VARCHAR,
    price DECIMAL(6,2),
    category uuid NOT NULL REFERENCES maestro.category(id),
    preparation_time NUMERIC,
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (id)
);

INSERT INTO maestro.product (id, name, price, category, preparation_time) VALUES ('a7df6342-8443-4969-87ef-e4597038eb7a', 'X-Salada', '29.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', '30');

