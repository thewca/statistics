insert into
    sum_of_ranks_meta (result_type, region_type, region, total_size)
select
    result_type,
    'World' regionType,
    'World' regionName,
    count(1) totalSize
from
    sum_of_ranks
where
    region_type = 'World'
    and region = 'World'
group by
    result_type
union
all (
    select
        result_type resultType,
        'Continent' regionType,
        region regionName,
        count(1) totalSize
    from
        sum_of_ranks
    where
        region_type = 'Continent'
    group by
        result_type,
        region
)
union
all (
    select
        result_type resultType,
        'Continent' regionType,
        region regionName,
        count(1) totalSize
    from
        sum_of_ranks
    where
        region_type = 'Country'
    group by
        result_type,
        region
)