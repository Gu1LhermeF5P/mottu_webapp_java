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

-- CORREÇÃO: Placas de moto agora são únicas.
INSERT INTO motorcycle (model, license_plate, status) VALUES
('Honda CG 160', 'BRA1A00', 'AVAILABLE'),
('Yamaha Fazer 250', 'BRA2B11', 'IN_MAINTENANCE'),
('Honda PCX', 'MEX3C22', 'BLOCKED'),
('Yamaha NMAX', 'BRA4D33', 'AVAILABLE'); -- Moto adicional com placa única.

-- Associa o primeiro dispositivo à primeira moto, que está 'AVAILABLE'.
UPDATE motorcycle SET tracking_device_id = 1 WHERE license_plate = 'BRA1A00';

-- Associa o segundo dispositivo à segunda moto, que está 'IN_MAINTENANCE'.
UPDATE motorcycle SET tracking_device_id = 2 WHERE license_plate = 'BRA2B11';

-- Deixa a terceira moto com status 'BLOCKED' mas sem dispositivo ainda, para teste.