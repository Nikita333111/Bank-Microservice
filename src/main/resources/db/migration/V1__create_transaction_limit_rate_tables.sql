CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,
    account_from BIGINT NOT NULL,
    account_to BIGINT NOT NULL,
    currency_shortname VARCHAR NOT NULL,
    amount DECIMAL NOT NULL,
    category VARCHAR NOT NULL,
    transaction_date TIMESTAMP WITH TIME ZONE NOT NULL,
    limit_exceeded BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE monthly_limit (
    id SERIAL PRIMARY KEY,
    limit_amount DECIMAL NOT NULL ,
    start_limit DECIMAL NOT NULL ,
    limit_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    limit_currency_shortname VARCHAR NOT NULL
);

CREATE TABLE currency_rate (
    id SERIAL PRIMARY KEY,
    rate_date DATE NOT NULL,
    currency_pair VARCHAR NOT NULL,
    close_rate DECIMAL NOT NULL
);

-- Устанавливаем дефолтный лимит на текущий месяц
INSERT INTO monthly_limit(limit_amount, start_limit, limit_datetime, limit_currency_shortname) VALUES (1000.0, 1000.0, DATE_TRUNC('month', now())::timestamptz(1), 'USD');