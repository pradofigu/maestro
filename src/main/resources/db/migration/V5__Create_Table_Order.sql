CREATE TABLE IF NOT EXISTS "order" (
    id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    number BIGSERIAL UNIQUE NOT NULL,
    customer_id UUID NULL REFERENCES customer(id) ON DELETE CASCADE,
    payment_status VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS order_product (
    order_id UUID REFERENCES "order"(id),
    product_id UUID REFERENCES product(id),
    PRIMARY KEY (order_id, product_id)
);