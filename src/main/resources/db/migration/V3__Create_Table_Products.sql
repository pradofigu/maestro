
CREATE TABLE IF NOT EXISTS maestro.product (
    id uuid DEFAULT uuid_generate_v4() NOT NULL,
    name VARCHAR,
    price DECIMAL(6,2),
    category VARCHAR,
    preparation_time NUMERIC,
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (id),
    CONSTRAINT fk_category
        FOREIGN KEY(category)
            REFERENCES maestro.category(name)
);

INSERT INTO maestro.product (id, name, price, category, preparation_time) VALUES ('a7df6342-8443-4969-87ef-e4597038eb7a', 'X-Salada', '29.90', 'Lanche', '30');

