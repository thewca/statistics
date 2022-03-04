select
    best,
    average
from
    Results r
    inner join Competitions c on r.competitionId = c.id
where
    best > 0
    and c.start_date = :CURRENT_DATE