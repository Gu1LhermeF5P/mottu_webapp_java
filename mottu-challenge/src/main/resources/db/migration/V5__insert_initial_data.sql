-- Senhas: adminpass -> $2a$10$GiseH9DP3.t2i091A8Q.U.V0jWRGZTPx3V3y5o.L5u2k3O2UnVb8q
-- Senhas: operatorpass -> $2a$10$vpye.9w9h5oiqnyWHs2a8.runl0x8J2gWFbtr5k2y3w4dJg0yNJJ.
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$GiseH9DP3.t2i091A8Q.U.V0jWRGZTPx3V3y5o.L5u2k3O2UnVb8q', 'ROLE_ADMIN'),
('operator', '$2a$10$vpye.9w9h5oiqnyWHs2a8.runl0x8J2gWFbtr5k2y3w4dJg0yNJJ.', 'ROLE_OPERATOR');

INSERT INTO branch (name, city) VALUES ('Filial Lapa', 'São Paulo');
INSERT INTO yard (name, grid_width, grid_height, branch_id) VALUES ('Pátio Principal', 20, 10, 1);

INSERT INTO beacon (uuid) VALUES
('a1b2c3d4-e5f6-7890-1234-567890abcdef'),
('b2c3d4e5-f6a7-8901-2345-67890abcdef1'),
('c3d4e5f6-a7b8-9012-3456-7890abcdef2');

INSERT INTO motorcycle (model, license_plate, status) VALUES
('Honda CG 160', 'BRA1A00', 'AVAILABLE'),
('Yamaha Fazer 250', 'BRA2B11', 'IN_MAINTENANCE'),
('Honda PCX', 'MEX3C22', 'AVAILABLE');

-- Moto 1 já está no pátio com beacon associado para demonstração
UPDATE motorcycle SET beacon_id = 1 WHERE id = 1;
INSERT INTO motorcycle_position (motorcycle_id, yard_id, pos_x, pos_y, last_updated)
VALUES (1, 1, 3, 5, CURRENT_TIMESTAMP());