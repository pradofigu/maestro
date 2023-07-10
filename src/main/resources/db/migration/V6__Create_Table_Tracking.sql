CREATE TABLE IF NOT EXISTS "tracking" (
    id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    number BIGINT NOT NULL,
    status VARCHAR NOT NULL
);