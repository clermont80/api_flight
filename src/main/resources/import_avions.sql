BEGIN;
INSERT INTO avions(id, operator, model, immatriculation, capacity) VALUES(NEXTVAL('avions_sequence_in_database'), 'AirbusIndustrie', 'AIRBUS A380','F-ABCD', 3);
INSERT INTO avions(id, operator, model, immatriculation, capacity) VALUES(NEXTVAL('avions_sequence_in_database'), 'Boeing', 'BOEING CV2', 'F-AZER', 3);
COMMIT;