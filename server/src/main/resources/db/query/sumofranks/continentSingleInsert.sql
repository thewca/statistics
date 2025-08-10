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
            c2.name region,
            (
                select
                    coalesce(max(continent_rank), 0)
                from
                    ranks_single r
                    inner join persons p on p.wca_id = r.person_id
                    inner join countries c on c.iso2 = p.country_id
                where
                    c.continent_id = c2.id
                    and r.event_id = e.id
            ) + 1 default_rank
        from
            events e,
            continents c2
        where
            e.`rank` < 900
    )
select
    (
        select
            c2.name
        from
            countries c
            left join continents c2 on c.continent_id = c2.id
        where
            p.country_id = c.id
    ) region,
    (
        select
            'Continent'
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
            when continent_rank is null
            or continent_rank = 0 then default_rank
            else r.continent_rank
        end
    ) overall,
    json_arrayagg(
        json_object(
            'event',
            json_object('id', e.id, 'name', e.name, 'rank', e.rank),
            'regionalRank',
            case
                when continent_rank is null
                or continent_rank = 0 then default_rank
                else r.continent_rank
            end,
            'completed',
            continent_rank is not null
            and continent_rank > 0
        )
    ) events
from
    events e
    left join persons p on e.`rank` < 900 -- Filter by active ranks
    left join ranks_single r on r.event_id = e.id
    and r.person_id = p.wca_id
    left join countries c on c.iso2 = p.country_id
    left join continents c2 on c.continent_id = c2.id
    left join default_ranks dr on dr.event_id = e.id
    and dr.region = c2.name
where
    wca_id is not null
    and sub_id = 1
group by
    wca_id