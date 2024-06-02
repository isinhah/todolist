CREATE TABLE IF NOT EXISTS tb_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    category VARCHAR(255),
    deadline DATE
);