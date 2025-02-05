RENAME TABLE activity TO activities;

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

RENAME TABLE user TO app_users;

RENAME TABLE gpx_track TO gpx_tracks;

ALTER TABLE gpx_tracks CHANGE COLUMN gpxtrack gpx_track mediumtext NOT NULL;

ALTER TABLE gpx_tracks CHANGE COLUMN userid user_id int(11) NOT NULL ;

RENAME TABLE user_role TO users_roles;

ALTER TABLE users_roles CHANGE COLUMN rolename role_name varchar(100);

ALTER TABLE users_roles MODIFY role_name varchar(100) NOT NULL;


ALTER TABLE users_roles DROP CONSTRAINT fk_user_role_users;

ALTER TABLE users_roles DROP CONSTRAINT ix_user_role_users;

ALTER TABLE users_roles CHANGE COLUMN userid user_id int(11) NOT NULL;

ALTER TABLE users_roles ADD CONSTRAINT fk_user_role_users FOREIGN KEY (user_id) REFERENCES app_users(id);

ALTER TABLE users_roles ADD CONSTRAINT ix_user_role_users UNIQUE (user_id, role_name);

/*

 UNIQUE KEY `ix_user_role_users` (`userid`,`role_name`),
  CONSTRAINT `fk_user_role_users` FOREIGN KEY (`userid`) REFERENCES `app_users` (`id`)
 */

