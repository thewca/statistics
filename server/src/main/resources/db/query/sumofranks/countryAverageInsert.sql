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
            u.country_iso2 region,
            count(1) + 1 default_rank
        from
            RanksAverage ra
            inner join Events e on e.`rank` < 900
            and ra.eventId = e.id
            inner join users u on u.wca_id = ra.personId
        where
            countryRank > 0
        group by
            e.id,
            u.country_iso2
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
    (
        select
            'average'
    ) result_type,
    sum(
        case
            when countryRank is null
            or countryRank = 0 then default_rank
            else ra.countryRank
        end
    ) overall,
    json_arrayagg(
        json_object(
            'event',
            json_object('id', e.id, 'name', e.name, 'rank', e.rank),
            'rank',
            case
                when countryRank is null
                or countryRank = 0 then default_rank
                else ra.countryRank
            end,
            'completed',
            countryRank is not null
            and countryRank > 0
        )
    ) events
from
    Events e
    left join users u on e.`rank` < 900 -- Filter by active ranks
    left join RanksAverage ra on ra.eventId = e.id
    and ra.personId = u.wca_id
    left join Countries c on c.iso2 = u.country_iso2
    left join default_ranks dr on dr.event_id = e.id
    and dr.region = c.iso2
where
    wca_id is not null
group by
    wca_id