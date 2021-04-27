use wca_development;

create table if not exists statistics (
    path varchar(50),
    title varchar(100),
    explanation varchar(200),
    display_mode varchar(20),
    `group` varchar(20),
    statistics json
);
