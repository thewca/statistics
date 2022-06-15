select
    region_rank,
    wca_id,
    name,
    country_iso2,
    overall,
    events
from
    sum_of_ranks sor
where
    region = :REGION
    and region_type = :REGION_TYPE
    and result_type = :RESULT_TYPE
order by
    region_rank,
    wca_id
limit
    :PAGE_SIZE offset :OFFSET