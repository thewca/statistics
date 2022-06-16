select
    result_type resultType,
    json_arrayagg(regions) regionGroups,
    (
        select
            json_arrayagg(
                json_object(
                    'id',
                    e.id,
                    'name',
                    e.name,
                    'rank',
                    e.`rank`
                )
            )
        from
            Events e
        where
            e.`rank` < 900
            and (
                -- Some events does not have average
                result_type != 'Average'
                or exists (
                    select
                        1
                    from
                        RanksAverage ra
                    where
                        ra.eventId = e.id
                )
            )
    ) availableEvents
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
order by
    result_type