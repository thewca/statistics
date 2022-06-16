select
    floor(region_rank / :PAGE_SIZE)
from
    sum_of_ranks sor
where
    result_type = :RESULT_TYPE
    and region_type = :REGION_TYPE
    and region = :REGION
    and wca_id = :WCA_ID