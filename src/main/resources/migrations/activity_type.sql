
ALTER TABLE run
RENAME TO activity;

ALTER TABLE activity
ADD COLUMN activitytype VARCHAR(16);

UPDATE activity
SET activitytype = 'RUN'
WHERE course NOT IN ('Rad', 'Swim');

UPDATE activity
SET activitytype = 'SWIM'
where course = 'Swim';

UPDATE activity
SET activitytype = 'BIKE'
where course = 'Rad';

ALTER TABLE shoes
RENAME TO gear;

ALTER TABLE activity
CHANGE shoesId gearId INT(11);
