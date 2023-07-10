CREATE TABLE IF NOT EXISTS "order_tracking" (
    id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    number BIGINT NOT NULL,
    status VARCHAR NOT NULL
    created_at TIMESTAMP DEFAULT NOW()
);