select
    result_type resultType,
    json_arrayagg(regions) regionGroups
from
    (
        select
            result_type,
            region_type,
            json_object(
                'regionType',
                region_type,
                'regions',
                json_arrayagg(
                    json_object('region', region, 'totalSize', total_size)
                )
            ) regions
        from
            sum_of_ranks_meta sorm
        group by
            result_type,
            region_type
    ) r
group by
    result_type