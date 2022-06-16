insert into
    sum_of_ranks_meta (result_type, region_type, region, total_size)
select
    result_type,
    region_type,
    region,
    count(1)
from
    sum_of_ranks
group by
    result_type,
    region_type,
    region