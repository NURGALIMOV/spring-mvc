CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    content TEXT,
    attachment TEXT,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
