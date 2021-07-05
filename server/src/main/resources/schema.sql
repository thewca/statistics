use wca_development;

create table if not exists statistics (
    path varchar(100),
    title varchar(100) not null,
    explanation varchar(200),
    display_mode varchar(20) not null,
    group_name varchar(20) not null,
    statistics json not null,
    last_modified datetime not null,
    primary key (path)
);

create table if not exists best_ever_rank (
    person_id varchar(10),
    best_ever_rank json not null,
    last_modified datetime not null,
    primary key (person_id)
);