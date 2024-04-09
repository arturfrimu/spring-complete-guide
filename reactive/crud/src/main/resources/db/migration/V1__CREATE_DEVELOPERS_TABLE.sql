CREATE TABLE developers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    speciality VARCHAR(255),
    status VARCHAR(50) NOT NULL
);