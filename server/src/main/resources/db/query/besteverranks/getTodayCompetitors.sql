select
    person_id wca_id,
    ct.name continent,
    c.name country,
    single,
    average,
    (
        select
            competition_id
        from
            results results
            inner join competitions competitions on results.competition_id = competitions.id
        where
            results.event_id = :EVENT_ID
            and results.person_id = today.person_id
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
            person_id,
            r.country_id,
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
            results r
            inner join competitions c on r.competition_id = c.id
        where
            event_id = :EVENT_ID
            and c.start_date = :DATE
        group by
            r.person_id,
            country_id
        order by
            person_id
    ) today
    inner join countries c on today.country_id = c.id
    inner join continents ct on c.continent_id = ct.id
where
    single is not null
