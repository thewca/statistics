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
