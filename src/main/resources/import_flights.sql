BEGIN;
INSERT INTO vols(id, number, origin, destination, departure_date,departure_time,arrival_date,arrival_time,plane_id) VALUES(NEXTVAL('vols_sequence_in_database'), 1452, 'France','Espagne', '2023-11-12','08:20:10','2023-11-12','10:00:00',1);
INSERT INTO vols(id, number, origin, destination, departure_date,departure_time,arrival_date,arrival_time,plane_id) VALUES(NEXTVAL('vols_sequence_in_database'), 1453, 'France','Maroc', '2023-11-12','08:30:00','2023-11-12','11:00:00',2);
COMMIT;