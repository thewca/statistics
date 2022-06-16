update
    sum_of_ranks s
    inner join (
        select
            *,
            rank() over (
                partition by s2.result_type,
                s2.region_type,
                s2.region
                order by
                    overall
            ) r
        from
            sum_of_ranks s2
    ) jt on jt.result_type = s.result_type
    and jt.region_type = s.region_type
    and jt.region = s.region
    and jt.wca_id = s.wca_id
set
    s.region_rank = jt.r