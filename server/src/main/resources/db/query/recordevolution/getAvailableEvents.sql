select
    id,
    name,
    e.`rank`
from
    record_evolution re
    inner join Events e on re.event_id collate utf8mb4_unicode_ci = e.id
