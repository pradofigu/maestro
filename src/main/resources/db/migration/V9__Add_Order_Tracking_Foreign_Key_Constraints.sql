ALTER TABLE order_tracking
ADD CONSTRAINT fk_order_tracking_order
FOREIGN KEY (order_id) REFERENCES "order"(id);