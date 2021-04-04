def largest_range(lista):

    if len(lista) < 2:
        return (1, None, None)  # it should be ignored

    i = 0
    r = 1
    max_r = 0
    min_range = -1  # where the range started
    max_range = -1  # where the range ended
    STEP = 1  # if you want ranges in 2 (eg. 4, 6, 8), change here

    range_start = lista[i]

    while i < len(lista)-1:

        if lista[i+1]-lista[i] == STEP:
            r += 1
        else:
            if r >= max_r:
                max_r = r
                max_range = lista[i]
                min_range = range_start

            range_start = lista[i+1]
            r = 1

        i += 1

    if r > max_r:
        max_r = r
        max_range = lista[i]
        min_range = range_start

    # len of range, start, end
    return (max_r, min_range, max_range)
