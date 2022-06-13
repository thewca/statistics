update
    sum_of_ranks s
    inner join (
        select
            *,
            rank() over (
                order by
                    overall
            ) r
        from
            sum_of_ranks s2
        where
            -- Same as below
            s2.region = 'World'
            and s2.region_type = 'World'
            and s2.result_type = 'average'
    ) jt on jt.region = s.region
    and jt.region_type = s.region_type
    and jt.wca_id = s.wca_id
set
    s.region_rank = jt.r
where
    -- World has no region type
    s.region = 'World'
    and s.region_type = 'World'
    and s.result_type = 'average'