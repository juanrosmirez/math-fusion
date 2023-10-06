-- CREATE DATABASE order_managment;

CREATE TABLE call_history (
    id serial PRIMARY KEY,
    timestamp timestamp NOT NULL,
    endpoint text NOT NULL,
    number1 double precision NOT NULL,
    number2 double precision NOT NULL,
    result double precision NOT NULL
);