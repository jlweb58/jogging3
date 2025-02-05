RENAME TABLE activity to activities;

ALTER TABLE activities CHANGE COLUMN Date activity_date date;

ALTER TABLE activities CHANGE COLUMN Distance distance double;

ALTER TABLE activities MODIFY activity_date date not null;

ALTER TABLE activities MODIFY course varchar(45) not null;

ALTER TABLE activities CHANGE COLUMN Time time time;

ALTER TABLE activities CHANGE COLUMN Weather weather varchar(255);

ALTER TABLE activities CHANGE COLUMN Comments comments varchar(255);

ALTER TABLE activities CHANGE COLUMN AvgHeartRate avg_heart_rate int(10) unsigned;

ALTER TABLE activities CHANGE COLUMN activitytype activity_type varchar(16);

ALTER TABLE activities CHANGE COLUMN ID id int(10) unsigned;

ALTER TABLE activities CHANGE COLUMN userId user_id int(11);

ALTER TABLE activities CHANGE COLUMN gearId gear_id int(11);
