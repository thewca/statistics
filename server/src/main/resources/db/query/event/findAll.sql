select id,
    name,
    `rank`
from Events -- hibernate does not like capitalization
order by `rank`
