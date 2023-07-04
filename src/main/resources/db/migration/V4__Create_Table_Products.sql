CREATE TABLE IF NOT EXISTS product (
    id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    name VARCHAR,
    price DECIMAL(6,2),
    category_id UUID NOT NULL REFERENCES category(id),
    preparation_time NUMERIC,
    created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO product (id, name, price, category_id, preparation_time) VALUES (uuid_generate_v4(), 'X-Salada', '29.90', 'c85b8201-29c4-495a-be86-7dd3a1d16b81', '30');

