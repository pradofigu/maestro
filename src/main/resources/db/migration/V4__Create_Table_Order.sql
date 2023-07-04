CREATE TABLE IF NOT EXISTS maestro.order (
    id uuid DEFAULT uuid_generate_v4() NOT NULL,
    number SERIAL UNIQUE NOT NULL,
    customer_id uuid NULL REFERENCES maestro.customer(id),
    payment_status VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS maestro.order_product (
    order_id uuid REFERENCES maestro.order(id),
    product_id uuid REFERENCES maestro.product(id),
    PRIMARY KEY (order_id, product_id)
);