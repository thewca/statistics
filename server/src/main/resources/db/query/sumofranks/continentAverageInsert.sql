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
                    ranks_average r
                    inner join users u on u.wca_id = r.person_id
                    inner join countries c on c.iso2 = u.country_iso2
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
            u.country_iso2 = c.iso2
    ) region,
    (
        select
            'Continent'
    ) region_type,
    wca_id,
    u.name,
    country_iso2,
    (
        select
            'Average'
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
    left join users u on e.`rank` < 900 -- Filter by active ranks
    left join ranks_average r on r.event_id = e.id
    and r.person_id = u.wca_id
    left join countries c on c.iso2 = u.country_iso2
    left join continents c2 on c.continent_id = c2.id
    left join default_ranks dr on dr.event_id = e.id
    and dr.region = c2.name
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