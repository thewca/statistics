insert into record_evolution (event_id, evolution)
values (:EVENT_ID, :EVOLUTION) on duplicate key
update evolution = json_array_append(
        evolution,
        '$',
        json_extract(
            values(evolution),
                '$[0]'
        )
    )
