CREATE TABLE motorcycle_position (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    motorcycle_id BIGINT NOT NULL UNIQUE,
    yard_id BIGINT NOT NULL,
    pos_x INT NOT NULL,
    pos_y INT NOT NULL,
    last_updated TIMESTAMP NOT NULL,
    FOREIGN KEY (motorcycle_id) REFERENCES motorcycle(id),
    FOREIGN KEY (yard_id) REFERENCES yard(id)
);