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
            c2.iso2 region,
            (
                select
                    coalesce(max(country_rank), 0)
                from
                    ranks_single r
                    inner join persons p on p.wca_id = r.person_id
                where
                    r.event_id = e.id
                    and p.country_id = c2.iso2
            ) + 1 default_rank
        from
            events e,
            countries c2
        where
            e.`rank` < 900
    )
select
    (
        select
            c.name
        from
            countries c
        where
            p.country_id = c.id
    ) region,
    (
        select
            'Country'
    ) region_type,
    wca_id,
    p.name,
    country_id,
    (
        select
            'Single'
    ) result_type,
    sum(
        case
            when country_rank is null
            or country_rank = 0 then default_rank
            else r.country_rank
        end
    ) overall,
    json_arrayagg(
        json_object(
            'event',
            json_object('id', e.id, 'name', e.name, 'rank', e.rank),
            'regionalRank',
            case
                when country_rank is null
                or country_rank = 0 then default_rank
                else r.country_rank
            end,
            'completed',
            country_rank is not null
            and country_rank > 0
        )
    ) events
from
    events e
    left join persons p on e.`rank` < 900 -- Filter by active ranks
    left join ranks_single r on r.event_id = e.id
    and r.person_id = p.wca_id
    left join countries c on c.id = p.country_id
    left join default_ranks dr on dr.event_id = e.id
    and dr.region = c.id
where
    wca_id is not null
    and sub_id = 1
group by
    wca_id,
    country_id,
    name