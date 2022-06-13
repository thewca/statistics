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
            RanksSingle rs
            inner join Events e on e.`rank` < 900
            and rs.eventId = e.id
            inner join users u on u.wca_id = rs.personId
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
            'single'
    ) result_type,
    sum(
        case
            when countryRank is null
            or countryRank = 0 then default_rank
            else rs.countryRank
        end
    ) overall,
    json_arrayagg(
        json_object(
            'event_id',
            e.id,
            'rank',
            case
                when countryRank is null
                or countryRank = 0 then default_rank
                else rs.countryRank
            end,
            'completed',
            countryRank is not null
            and countryRank > 0
        )
    ) events
from
    Events e
    left join users u on e.`rank` < 900 -- Filter by active ranks
    left join RanksSingle rs on rs.eventId = e.id
    and rs.personId = u.wca_id
    left join Countries c on c.iso2 = u.country_iso2
    left join default_ranks dr on dr.event_id = e.id
    and dr.region = c.iso2
where
    wca_id is not null
group by
    wca_id