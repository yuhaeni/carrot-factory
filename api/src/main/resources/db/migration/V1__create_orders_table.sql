CREATE TABLE orders (
    id           BIGSERIAL      PRIMARY KEY,
    product_id   BIGINT         NOT NULL,
    product_name VARCHAR(255)   NOT NULL,
    quantity     INT            NOT NULL,
    total_amount NUMERIC(19, 2) NOT NULL,
    customer_id  BIGINT         NOT NULL,
    status       VARCHAR(50)    NOT NULL DEFAULT 'CREATED',
    created_at   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);
