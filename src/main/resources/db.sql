DROP TABLE IF EXISTS company;

CREATE TABLE company (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    position VARCHAR(100),
    salary NUMERIC
);

INSERT INTO company (name, position, salary) VALUES
    ('Kononv Ilya', 'Developer', 50000),
    ('Savin Egor', 'Manager', 75000),
    ('Astafev Nikolay', 'Analyst', 60000),
    ('Apevalin Ivan', 'Developer', 50000);