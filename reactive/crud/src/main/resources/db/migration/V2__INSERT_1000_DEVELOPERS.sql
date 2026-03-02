-- Insert 1000 developers for testing
INSERT INTO developers (first_name, last_name, email, speciality, status)
SELECT
    'Developer',
    'User' || n,
    'developer' || n || '@mail.com',
    CASE (n % 5)
        WHEN 0 THEN 'Java Developer'
        WHEN 1 THEN 'JavaScript Developer'
        WHEN 2 THEN 'Python Developer'
        WHEN 3 THEN 'Go Developer'
        ELSE 'Rust Developer'
    END,
    'ACTIVE'
FROM generate_series(1, 1000) AS n;
