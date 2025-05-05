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
                    ranks_single r
                where
                    r.eventId = e.id
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
            'Single'
    ) result_type,
    sum(coalesce(r.world_rank, default_rank)) overall,
    json_arrayagg(
        json_object(
            'event',
            json_object('id', e.id, 'name', e.name, 'rank', e.rank),
            'regionalRank',
            coalesce(r.world_rank, default_rank),
            'completed',
            r.world_rank is not null
        )
    ) events
from
    events e
    left join users u on e.`rank` < 900 -- Filter by active ranks
    left join ranks_single r on r.event_id = e.id
    and r.person_id = u.wca_id
    left join default_ranks dr on dr.event_id = e.id
where
    wca_id is not null
group by
    wca_id