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
                    count(1)
                from
                    RanksAverage r
                    inner join users u on u.wca_id = r.personId
                where
                    countryRank > 0
                    and r.eventId = e.id
                    and u.country_iso2 = c2.iso2
            ) + 1 default_rank
        from
            Events e,
            Countries c2
        where
            e.`rank` < 900
    )
select
    (
        select
            c.name
        from
            Countries c
        where
            u.country_iso2 = c.iso2
    ) region,
    (
        select
            'Country'
    ) region_type,
    wca_id,
    u.name,
    country_iso2,
    (
        select
            'average'
    ) result_type,
    sum(
        case
            when countryRank is null
            or countryRank = 0 then default_rank
            else r.countryRank
        end
    ) overall,
    json_arrayagg(
        json_object(
            'event',
            json_object('id', e.id, 'name', e.name, 'rank', e.rank),
            'regionalRank',
            case
                when countryRank is null
                or countryRank = 0 then default_rank
                else r.countryRank
            end,
            'completed',
            countryRank is not null
            and countryRank > 0
        )
    ) events
from
    Events e
    left join users u on e.`rank` < 900 -- Filter by active ranks
    left join RanksAverage r on r.eventId = e.id
    and r.personId = u.wca_id
    left join Countries c on c.iso2 = u.country_iso2
    left join default_ranks dr on dr.event_id = e.id
    and dr.region = c.iso2
where
    wca_id is not null
group by
    wca_id