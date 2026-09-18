--liquibase formatted sql

--changeset pablo:001

CREATE TABLE documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100),
    file_path VARCHAR(500) NOT NULL,
    size BIGINT,
    status VARCHAR(30) NOT NULL,
    created_at DATETIME NOT NULL
);

CREATE INDEX idx_documents_user_id
ON documents(user_id);

CREATE INDEX idx_documents_status
ON documents(status);