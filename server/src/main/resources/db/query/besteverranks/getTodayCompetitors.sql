select
    personId wcaId,
    ct.name continent,
    c.name country,
    single,
    average,
    (
        select
            competitionId
        from
            Results results
            inner join Competitions competitions on results.competitionId = competitions.id
        where
            results.eventId = :EVENT_ID
            and results.personId = today.personId
            and competitions.start_date = :DATE
            and (
                results.best = today.single
                or results.average = today.average
            )
        limit
            1
    ) competition
from
    (
        select
            personId,
            r.countryId,
            min(
                case
                    when best > 0 then best
                    else null
                end
            ) single,
            min(
                case
                    when average > 0 then average
                    else null
                end
            ) average
        from
            Results r
            inner join Competitions c on r.competitionId = c.id
        where
            eventId = :EVENT_ID
            and c.start_date = :DATE
        group by
            r.personId,
            countryId
        order by
            personId
    ) today
    inner join Countries c on today.countryId = c.id
    inner join Continents ct on c.continentId = ct.id
where
    single is not null
