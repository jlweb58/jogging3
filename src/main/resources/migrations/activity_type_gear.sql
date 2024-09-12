ALTER TABLE gear
    ADD COLUMN geartype VARCHAR(16);

UPDATE gear SET geartype = 'SHOES' WHERE id < 14;
UPDATE gear SET geartype = 'BIKE' WHERE id >= 14;

UPDATE gear SET preferred = 1 WHERE id = 12;