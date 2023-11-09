CREATE TABLE IF NOT EXISTS address (
    id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    street VARCHAR,
    city VARCHAR,
    uf VARCHAR(2),
    postal_code VARCHAR,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

ALTER TABLE customer ADD COLUMN address_id UUID;

ALTER TABLE customer
ADD CONSTRAINT fk_customer_address
FOREIGN KEY (address_id) REFERENCES address(id);
