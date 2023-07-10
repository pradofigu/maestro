CREATE TABLE IF NOT EXISTS "order_tracking" (
    id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    order_id UUID NOT NULL REFERENCES "order"(id),
    status VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);