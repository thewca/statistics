use wca_development;

create table if not exists statistics (
    path varchar(50),
    title varchar(100) not null,
    explanation varchar(200),
    display_mode varchar(20) not null,
    group_name varchar(20) not null,
    statistics json not null,
    primary key (path)
);
