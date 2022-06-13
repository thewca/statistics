insert into
    sum_of_ranks (
        region,
        region_type,
        wca_id,
        result_type,
        overall,
        events
    )
with default_ranks as (
    select
        e.id event_id,
        c2.name region,
        count(1) + 1 default_rank
    from
        RanksSingle rs
        inner join Events e on e.`rank` < 900
        and rs.eventId = e.id
        inner join users u on u.wca_id = rs.personId
        inner join Countries c on c.iso2 = u.country_iso2
        inner join Continents c2 on c.continentId = c2.id
    where
        continentRank > 0
    group by
        e.id,
        c2.name
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
    (
        select
            'single'
    ) result_type,
    sum(
        case
            when continentRank is null
            or continentRank = 0 then default_rank
            else rs.continentRank
        end
    ) overall,
    json_arrayagg(
        json_object(
            'event_id',
            e.id,
            'rank',
            case
                when continentRank is null
                or continentRank = 0 then default_rank
                else rs.continentRank
            end,
            'completed',
            continentRank is not null
            and continentRank > 0
        )
    ) events
from
    Events e
    left join users u on e.`rank` < 900 -- Filter by active ranks
    left join RanksSingle rs on rs.eventId = e.id
    and rs.personId = u.wca_id
    left join Countries c on c.iso2 = u.country_iso2
    left join Continents c2 on c.continentId = c2.id
    left join default_ranks dr on dr.event_id = e.id
    and dr.region = c2.name
where
    wca_id is not null
group by
    wca_id