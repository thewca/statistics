select id,
    name,
    `rank`
from Events -- hibernate does not like capitalization
where id in (:ID)