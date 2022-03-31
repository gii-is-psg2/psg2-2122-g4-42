-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('dueno1','du3n0',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'dueno1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('veterinario1','v3t3rinario',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'veterinario1','veterinarian');

INSERT INTO vets(id, first_name,last_name) VALUES (1, 'Jaime', 'Carteras');
INSERT INTO vets(id, first_name,last_name) VALUES (2, 'Helena', 'Lara');
INSERT INTO vets(id, first_name,last_name) VALUES (3, 'Linda', 'Dominguez');
INSERT INTO vets(id, first_name,last_name) VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets(id, first_name,last_name) VALUES (5, 'Enrique', 'Esteban');
INSERT INTO vets(id, first_name,last_name) VALUES (6, 'Aurora', 'Jerez');

INSERT INTO specialties VALUES (1, 'radiologia');
INSERT INTO specialties VALUES (2, 'cirugia');
INSERT INTO specialties VALUES (3, 'dentista');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'gato');
INSERT INTO types VALUES (2, 'perro');
INSERT INTO types VALUES (3, 'lagarto');
INSERT INTO types VALUES (4, 'serpiente');
INSERT INTO types VALUES (5, 'pajaro');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO rooms VALUES (1, 'Habitacion_1');
INSERT INTO rooms VALUES (2, 'Habitacion_2');
INSERT INTO rooms VALUES (3, 'Habitacion_3');
INSERT INTO rooms VALUES (4, 'Habitacion_4');
INSERT INTO rooms VALUES (5, 'Habitacion_5');
INSERT INTO rooms VALUES (6, 'Habitacion_6');

INSERT INTO owners VALUES (1, 'Jorge', 'Francisco', 'Calle Castillo.', 'Caceres', '6085551023', 'dueno1');
INSERT INTO owners VALUES (2, 'Beatriz', 'David', 'Avenida Cardenal', 'Badajoz', '6085551749', 'dueno1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriguez', 'Calle Espinosa', 'Murcia', '6085558763', 'dueno1');
INSERT INTO owners VALUES (4, 'Carola', 'Fernandez', 'Avenida Flores', 'Bilbao', '6085553198', 'dueno1');
INSERT INTO owners VALUES (5, 'Pedro', 'Tavarez', 'Calle Hada', 'Madrid', '6085552765', 'dueno1');
INSERT INTO owners VALUES (6, 'Juan', 'Calero', 'Calle Lagos', 'Sevilla', '6085552654', 'dueno1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', 'Paseo Oscuro', 'Sevilla', '6085555387', 'dueno1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', 'Calle Escobas', 'Madrid', '6085557683', 'dueno1');
INSERT INTO owners VALUES (9, 'David', 'Estevez', 'Avenida Cisne', 'Madrid', '6085559435', 'dueno1');
INSERT INTO owners VALUES (10, 'Carlos', 'Esteban', 'Avenida Independencia', 'Cadiz', '6085555487', 'dueno1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leonardo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basilea', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosario', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Joyita', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Manuel', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'Jorgito', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samuel', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Poncho', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Manfredi', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sky', '2012-06-08', 1, 10);

INSERT INTO hotels(id,name,date1,date2,pet_id,room_id) VALUES (1,'5EStrellas','2012-06-08','2012-07-25', 1,1);
INSERT INTO hotels(id,name,date1,date2,pet_id,room_id) VALUES (2,'Alborán','2012-06-08','2012-07-25', 10,2);
INSERT INTO hotels(id,name,date1,date2,pet_id,room_id) VALUES (3,'NH','2012-06-08','2012-07-25', 6,3);
INSERT INTO hotels(id,name,date1,date2,pet_id,room_id) VALUES (4,'Caserio','2012-06-08','2012-07-25', 7,4);
INSERT INTO hotels(id,name,date1,date2,pet_id,room_id) VALUES (5,'xx','2012-08-20','2012-09-25', 7,6);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'vacuna de la rabia');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'vacuna de la rabia');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'castracion');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'esterilizacion');

INSERT INTO causes(id,name,description,money_raised,budget_target,organization) VALUES (1, 'Parvovirosis', 'Esta ayuda va destinada a curar a perros con la enfermedad de parvovirosis', 0, 2000, 'Arca de Noe');
INSERT INTO causes(id,name,description,money_raised,budget_target,organization) VALUES (2, 'Refugio de perros y gatos', 'Esta ayuda va destinada a la asociacion El Trasgu, la cual trabaja para evitar que mas perros y gatos sean abandonados en Espana.', 0, 3000, 'El Trasgu');
INSERT INTO causes(id,name,description,money_raised,budget_target,organization) VALUES (3, 'Fondos para APAP', 'ASAP se trata de un centro de acogida y adopcion donde trabajan voluntarios que procuran comida, refugio, limpieza, atencion sanitaria y ejercicio a los perros rescatados.', 0, 1500, 'Asociacion Protectora de Animales y Plantas (APAP Alcala)');

INSERT INTO donations(id, donation_date, amount, client, cause_id) VALUES (1, '2014-05-03', 30.0, 'Antonio Cortes', 1);
INSERT INTO donations(id, donation_date, amount, client, cause_id) VALUES (2, '2013-01-05', 40.0, 'Manuel Escobas', 3);
INSERT INTO donations(id, donation_date, amount, client, cause_id) VALUES (3, '2013-01-07', 15.0, 'Antonio Banderas', 2);