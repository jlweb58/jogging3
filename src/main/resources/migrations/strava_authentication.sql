create table strava_authentication
(
    id             int unsigned auto_increment primary key,
    expirationDate datetime not null,
    accessToken varchar(255) not null,
    refreshToken varchar(255) not null,
    userId int not null,
    constraint fk_strava_authentication_user
        foreign key (userId) references user (id),
    constraint unique_userid unique (userId)
);
