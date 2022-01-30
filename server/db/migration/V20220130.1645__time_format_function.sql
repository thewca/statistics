drop function if exists wca_statistics_time_format;

create function wca_statistics_time_format(
    time_result bigint,
    event_id varchar(20),
    result_type varchar(20)
) returns varchar(20) deterministic reads sql data return case
    when time_result = 0 then '-'
    when time_result = -1 then 'DNF'
    when time_result = -2 then 'DNS'
    when event_id = '333fm'
    and result_type = 'average' then format(time_result / 100, 2)
    when event_id = '333fm' then time_result
    when event_id = '333mbf' then replace(
        replace(
            replace(
                concat(
                    99 - substring(time_result, 1, 2) + substring(time_result, 8, 2),
                    '/',
                    99 - substring(time_result, 1, 2) + 2 * substring(time_result, 8, 2),
                    ' (',
                    sec_to_time(substring(time_result, 3, 5)),
                    ')'
                ),
                '.000000',
                ''
            ),
            '0000',
            ''
        ),
        '00:',
        ''
    )
    else substring(
        replace(
            replace(
                replace(
                    replace(
                        replace(
                            sec_to_time(time_result / 100),
                            '00:00:00.',
                            '0.'
                        ),
                        '00:00:0',
                        ''
                    ),
                    '00:00:',
                    ''
                ),
                '00:0',
                ''
            ),
            '00:',
            ''
        ),
        1,
        length(
            replace(
                replace(
                    replace(
                        replace(
                            replace(
                                sec_to_time(time_result / 100),
                                '00:00:00.',
                                '0.'
                            ),
                            '00:00:0',
                            ''
                        ),
                        '00:00:',
                        ''
                    ),
                    '00:0',
                    ''
                ),
                '00:',
                ''
            )
        ) -2
    )
end;
