select
    id,
    name,
    e.`rank`
from
    record_evolution re
    inner join events e on re.event_id collate utf8mb4_unicode_ci = e.id
where
    e.`rank` < 900 -- Active events
order by
    e.`rank`