select id,
    name,
    `rank`
from events -- hibernate does not like capitalization
order by `rank`
