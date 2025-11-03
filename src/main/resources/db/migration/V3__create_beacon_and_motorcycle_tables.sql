
CREATE TABLE tracking_devices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE
);

CREATE TABLE motorcycle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(100) NOT NULL,
    license_plate VARCHAR(10) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    tracking_device_id BIGINT UNIQUE,
    FOREIGN KEY (tracking_device_id) REFERENCES tracking_devices(id)
);