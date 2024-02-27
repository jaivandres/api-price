CREATE TABLE prices (
    id SERIAL PRIMARY KEY,
    brand_id VARCHAR NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list NUMERIC NOT NULL,
    product_id INT NOT NULL,
    priority INT NOT NULL,
    price NUMERIC NOT NULL,
    curr VARCHAR NOT NULL
);

INSERT INTO prices
(brand_id, start_date, end_date, price_list, product_id, priority, price, curr, id)
VALUES('1', '2020-06-14 00:00:00.000', '2020-12-31 23:59:59.000', 1, 35455, 0, 35.50, 'EUR', 1);

INSERT INTO prices
(brand_id, start_date, end_date, price_list, product_id, priority, price, curr, id)
VALUES('1', '2020-06-14 15:00:00.000', '2020-06-14 18:59:30.000', 1, 35455, 1, 25.45, 'EUR', 2);

INSERT INTO prices
(brand_id, start_date, end_date, price_list, product_id, priority, price, curr, id)
VALUES('1', '2020-06-15 00:00:00.000', '2020-06-15 11:00:00.000', 1, 35455, 1, 30.50, 'EUR', 3);

INSERT INTO prices
(brand_id, start_date, end_date, price_list, product_id, priority, price, curr, id)
VALUES('1', '2020-06-15 16:00:00.000', '2020-12-31 23:59:59.000', 1, 35455, 1, 38.95, 'EUR', 4);

INSERT INTO prices
(brand_id, start_date, end_date, price_list, product_id, priority, price, curr, id)
VALUES('1', '2010-06-15 16:00:00.000', '2024-01-29 23:59:59.000', 1, 35455, 1, 38.95, 'EUR', 5);
