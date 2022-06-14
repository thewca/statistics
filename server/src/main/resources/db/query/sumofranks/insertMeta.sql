insert into
    sum_of_ranks_meta (result_type, region_type, region, total_size)
select
    'Single' resultType,
    'World' regionType,
    'World' regionName,
    count(1) totalSize
from
    RanksSingle r
where
    r.worldRank > 0
union
all (
    select
        'Average' resultType,
        'World' regionType,
        'World' regionName,
        count(1) totalSize
    from
        RanksAverage r
    where
        r.worldRank > 0
)
union
all (
    select
        'Single' resultType,
        'Continent' regionType,
        c2.name regionName,
        count(1) totalSize
    from
        RanksSingle r
        inner join users u on r.personId = u.wca_id
        inner join Countries c on u.country_iso2 = c.iso2
        inner join Continents c2 on c2.id = c.continentId
    where
        r.continentRank > 0
    group by
        c2.name
    order by
        c2.name
)
union
all (
    select
        'Average' resultType,
        'Continent' regionType,
        c2.name regionName,
        count(1) totalSize
    from
        RanksAverage r
        inner join users u on r.personId = u.wca_id
        inner join Countries c on u.country_iso2 = c.iso2
        inner join Continents c2 on c2.id = c.continentId
    where
        r.continentRank > 0
    group by
        c2.name
    order by
        c2.name
)
union
all (
    select
        'Single' resultType,
        'Country' regionType,
        c.name regionName,
        count(1) totalSize
    from
        RanksSingle r
        inner join users u on r.personId = u.wca_id
        inner join Countries c on u.country_iso2 = c.iso2
    where
        r.continentRank > 0
    group by
        c.name
    order by
        c.name
)
union
all (
    select
        'Average' resultType,
        'Country' regionType,
        c.name regionName,
        count(1) totalSize
    from
        RanksAverage r
        inner join users u on r.personId = u.wca_id
        inner join Countries c on u.country_iso2 = c.iso2
    where
        r.continentRank > 0
    group by
        c.name
    order by
        c.name
)