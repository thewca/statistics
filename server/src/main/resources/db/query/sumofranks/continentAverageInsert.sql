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
                    count(1)
                from
                    RanksAverage r
                    inner join users u on u.wca_id = r.personId
                    inner join Countries c on c.iso2 = u.country_iso2
                where
                    c.continentId = c2.id
                    and continentRank > 0
                    and r.eventId = e.id
            ) + 1 default_rank
        from
            Events e,
            Continents c2
        where
            e.`rank` < 900
    )
select
    (
        select
            c2.name
        from
            Countries c
            left join Continents c2 on c.continentId = c2.id
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
            when continentRank is null
            or continentRank = 0 then default_rank
            else r.continentRank
        end
    ) overall,
    json_arrayagg(
        json_object(
            'event',
            json_object('id', e.id, 'name', e.name, 'rank', e.rank),
            'regionalRank',
            case
                when continentRank is null
                or continentRank = 0 then default_rank
                else r.continentRank
            end,
            'completed',
            continentRank is not null
            and continentRank > 0
        )
    ) events
from
    Events e
    left join users u on e.`rank` < 900 -- Filter by active ranks
    left join RanksAverage r on r.eventId = e.id
    and r.personId = u.wca_id
    left join Countries c on c.iso2 = u.country_iso2
    left join Continents c2 on c.continentId = c2.id
    left join default_ranks dr on dr.event_id = e.id
    and dr.region = c2.name
where
    wca_id is not null
    and exists (
        -- Some events has no averages and this excludes them to avoid adding 1 into the sum
        select
            1
        from
            RanksAverage ra2
        where
            ra2.eventId = e.id
    )
group by
    wca_id