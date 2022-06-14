use wca_development;

create table if not exists statistics (
    path varchar(100),
    title varchar(100) not null,
    explanation varchar(200),
    display_mode varchar(20) not null,
    group_name varchar(20) not null,
    statistics json not null,
    last_modified datetime not null,
    export_date datetime not null,
    primary key (path)
);

create table if not exists best_ever_rank (
    person_id varchar(10),
    best_ever_rank json not null,
    last_modified datetime not null,
    primary key (person_id)
);

create table if not exists record_evolution (
    event_id varchar(6) primary key,
    evolution json not null
);

create table if not exists sum_of_ranks (
  region_rank int default null,
  region varchar(100) not null,
  region_type varchar(20) not null,
  wca_id varchar(10) not null,
  result_type varchar(7) not null,
  overall int default null,
  events json not null,
  primary key (region, region_type, wca_id, result_type)
);

create table if not exists sum_of_ranks_meta (
  result_type varchar(7) not null,
  region_type varchar(20) not null,
  region varchar(100) not null,
  total_size int not null,
  primary key (result_type, region, region_type)
);

----------------------------------------------------------------------------------------------------
-- Person link

drop function if exists wca_statistics_person_link_format;

create function wca_statistics_person_link_format(
    person_id varchar(10),
    person_name varchar(200)
) returns varchar(300) deterministic reads sql data return concat(
    '<a href="https://www.worldcubeassociation.org/persons/',
    person_id,
    '">',
    coalesce(person_name, person_id),
    '</a>'
);

----------------------------------------------------------------------------------------------------
-- Competition link

drop function if exists wca_statistics_competition_link_format;

create function wca_statistics_competition_link_format(
    competition_id varchar(50),
    competition_name varchar(200)
) returns varchar(300) deterministic reads sql data return concat(
    '<a href="https://www.worldcubeassociation.org/competitions/',
    competition_id,
    '">',
    competition_name,
    '</a>'
);

----------------------------------------------------------------------------------------------------
-- Time format

drop function if exists wca_statistics_time_format;

create function wca_statistics_time_format(
    time_result bigint,
    event_id varchar(20),
    result_type varchar(20)
) returns varchar(20) deterministic reads sql data return case
    when time_result = 0 then '-'
    when time_result = -1 then 'DNF'
    when time_result = -2 then 'DNS'
    when event_id = '333fm'
    and result_type = 'average' then format(time_result / 100, 2)
    when event_id = '333fm' then time_result
    when event_id = '333mbf' then replace(
        replace(
            replace(
                concat(
                    99 - substring(time_result, 1, 2) + substring(time_result, 8, 2),
                    '/',
                    99 - substring(time_result, 1, 2) + 2 * substring(time_result, 8, 2),
                    ' (',
                    sec_to_time(substring(time_result, 3, 5)),
                    ')'
                ),
                '.000000',
                ''
            ),
            '0000',
            ''
        ),
        '(00:',
        '('
    )
    else substring(
        replace(
            replace(
                replace(
                    replace(
                        replace(
                            sec_to_time(time_result / 100),
                            '00:00:00.',
                            '0.'
                        ),
                        '00:00:0',
                        ''
                    ),
                    '00:00:',
                    ''
                ),
                '00:0',
                ''
            ),
            '00:',
            ''
        ),
        1,
        length(
            replace(
                replace(
                    replace(
                        replace(
                            replace(
                                sec_to_time(time_result / 100),
                                '00:00:00.',
                                '0.'
                            ),
                            '00:00:0',
                            ''
                        ),
                        '00:00:',
                        ''
                    ),
                    '00:0',
                    ''
                ),
                '00:',
                ''
            )
        ) -2
    )
end;
