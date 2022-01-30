drop function if exists wca_statistics_competition_link_format;

create function wca_statistics_competition_link_format(
    competition_id varchar(50),
    competition_name varchar(200)
) returns varchar(300) deterministic reads sql data return concat(
    '<a href="https://www.worldcubeassociation.org/competitions/',
    competition.id,
    '">',
    competition.cellName,
    '</a>'
);
