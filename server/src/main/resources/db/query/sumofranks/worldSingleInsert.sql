insert into
    sum_of_ranks (
        region,
        region_type,
        wca_id,
        result_type,
        overall,
        events
    ) with default_ranks as (
        select
            e.id event_id,
            (
                select
                    count(1) + 1
                from
                    RanksSingle rs
                where
                    rs.eventId = e.id
            ) default_rank
        from
            Events e
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
    (
        select
            'single'
    ) result_type,
    sum(coalesce(rs.worldRank, default_rank)) overall,
    json_arrayagg(
        json_object(
            'event',
            json_object('id', e.id, 'name', e.name, 'rank', e.rank),
            'rank',
            coalesce(rs.worldRank, default_rank),
            'completed',
            rs.worldRank is not null
        )
    ) events
from
    Events e
    left join users u on e.`rank` < 900 -- Filter by active ranks
    left join RanksSingle rs on rs.eventId = e.id
    and rs.personId = u.wca_id
    left join default_ranks dr on dr.event_id = e.id
where
    wca_id is not null
group by
    wca_id