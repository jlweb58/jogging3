-- Convert from TIME to VARCHAR
ALTER TABLE activities ADD COLUMN duration_str VARCHAR(8);
UPDATE activities
SET duration_str = TIME_FORMAT(ABS(time), '%H:%i:%s')
WHERE time IS NOT NULL;

ALTER TABLE activities DROP COLUMN time;
ALTER TABLE activities CHANGE COLUMN duration_str duration VARCHAR(8);
