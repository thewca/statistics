-- get all competition start dates for competitions that held a particular event
select distinct c.start_date
from Competitions c
    inner join competition_events e on e.competition_id = c.id
where event_id = :EVENT_ID
order by start_date