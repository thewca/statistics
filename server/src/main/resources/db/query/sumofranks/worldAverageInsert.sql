insert into
    sum_of_ranks (
        region,
        region_type,
        wca_id,
        name,
        country_iso2,
        result_type,
        overall,
        events
    ) with default_ranks as (
        select
            e.id event_id,
            (
                select
                    coalesce(max(world_rank), 0)
                from
                    ranks_average r
                where
                    r.event_id = e.id
            ) + 1 default_rank
        from
            events e
        where
            e.`rank` < 900
    )
select
    (
        select
            'World'
    ) region,
    (
        select
            'World'
    ) region_type,
    wca_id,
    u.name,
    country_iso2,
    (
        select
            'Average'
    ) result_type,
    sum(coalesce(r.worldRank, default_rank)) overall,
    json_arrayagg(
        json_object(
            'event',
            json_object('id', e.id, 'name', e.name, 'rank', e.rank),
            'regionalRank',
            coalesce(r.worldRank, default_rank),
            'completed',
            r.worldRank is not null
        )
    ) events
from
    events e
    left join users u on e.`rank` < 900 -- Filter by active ranks
    left join ranks_average r on r.event_id = e.id
    and r.person_id = u.wca_id
    left join default_ranks dr on dr.event_id = e.id
where
    wca_id is not null
    and exists (
        -- Some events has no averages and this excludes them to avoid adding 1 into the sum
        select
            1
        from
            ranks_average ra2
        where
            ra2.event_id = e.id
    )
group by
    wca_id