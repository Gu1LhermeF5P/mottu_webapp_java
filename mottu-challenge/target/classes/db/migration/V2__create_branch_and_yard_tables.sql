CREATE TABLE branch (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL
);

CREATE TABLE yard (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    grid_width INT NOT NULL,
    grid_height INT NOT NULL,
    branch_id BIGINT,
    FOREIGN KEY (branch_id) REFERENCES branch(id)
);