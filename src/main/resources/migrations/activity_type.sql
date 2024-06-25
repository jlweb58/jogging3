
ALTER TABLE run
RENAME TO activity;

ALTER TABLE activity
ADD COLUMN activity_type VARCHAR(16);

UPDATE activity
SET activity_type = 'RUN'
WHERE course NOT IN ('Rad', 'Swim');

UPDATE activity
SET activity_type = 'SWIM'
where course = 'Swim';

UPDATE activity
SET activity_type = 'BIKE_RIDE'
where course = 'Rad';

