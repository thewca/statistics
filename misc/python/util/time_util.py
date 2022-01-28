def time_format(result, event=None, type=None):
    if result == 0:
        return '-'

    if result == -1:
        return 'DNF'
    if result == -2:
        return 'DNF'

    if event == '333fm':
        if type == 'average':
            return '%.2f' % (result/100)
        return result

    if event == '333mbf':
        return mbld(str(result))

    result = int(result)

    h, result = divmod(result, 360000)
    m, result = divmod(result, 6000)
    s, result = divmod(result, 100)

    if h > 0:
        return '%s:%s:%s.%s' % (h, str(m).zfill(2), str(s).zfill(2), str(result).zfill(2))
    if m > 0:
        return '%s:%s.%s' % (m, str(s).zfill(2), str(result).zfill(2))
    return '%s.%s' % (s, str(result).zfill(2))


def mbld(result):
    missed = int(result[-2:])
    dd = int(result[:2])
    difference = 99 - dd
    solved = difference + missed
    attempted = solved + missed
    time_in_seconds = int(result[2:7])

    h, time_in_seconds = divmod(time_in_seconds, 60*60)
    m, s = divmod(time_in_seconds, 60)

    time = f'{h}:' * (h > 0) + ('%s:%s' %
                                (str(m).zfill(2), str(s).zfill(2)))

    return f'{solved}/{attempted} {time}'
