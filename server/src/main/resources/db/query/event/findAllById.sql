select id,
    name,
    `rank`
from events -- hibernate does not like capitalization
where id in (:ID)