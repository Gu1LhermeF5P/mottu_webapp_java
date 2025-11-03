-- Senhas (com hashes BCrypt válidos)
-- adminpass -> $2a$10$69pVellRb0.OhLDAlhboqOMhpMrp7VkxEdTKyGFG7OmWcsNCHZq7q
-- operatorpass -> $2a$10$tAV2koCawTRpHfLnU6jP1uDE5O1D5Arm0TMZls8CDCOxyPSCSrt1C

INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$69pVellRb0.OhLDAlhboqOMhpMrp7VkxEdTKyGFG7OmWcsNCHZq7q', 'ROLE_ADMIN'),
('operator', '$2a$10$tAV2koCawTRpHfLnU6jP1uDE5O1D5Arm0TMZls8CDCOxyPSCSrt1C', 'ROLE_OPERATOR');

INSERT INTO branch (name, city) VALUES ('Filial Lapa', 'São Paulo');
INSERT INTO yard (name, grid_width, grid_height, branch_id) VALUES ('Pátio Principal', 100, 100, 1);


INSERT INTO tracking_devices (uuid) VALUES
('a1b2c3d4-e5f6-7890-1234-567890abcdef'),
('b2c3d4e5-f6a7-8901-2345-67890abcdef1'),
('c3d4e5f6-a7b8-9012-3456-7890abcdef2');

