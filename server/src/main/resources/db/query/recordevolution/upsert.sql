insert into record_evolution (region, evolution)
values (:REGION, :EVOLUTION) on duplicate key
update evolution = json_array_append(
        evolution,
        '$',
        json_extract(
            values(evolution),
                '$[0]'
        )
    )
