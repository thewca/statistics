insert into best_ever_rank (person_id, best_ever_rank, last_modified)
values (:PERSON_ID, :EVENT_RANKS, current_timestamp()) on duplicate key
update best_ever_rank = json_array_append(
        best_ever_rank,
        '$',
        json_extract(
            values(best_ever_rank),
                '$[0]'
        )
    ),
    last_modified = current_timestamp()
