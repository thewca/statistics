def time_format(cents, event=None, type=None):
    if event == "333fm":
        if type == "average":
            return "%.2f" % (cents)
        return cents

    cents = int(cents)

    h, cents = divmod(cents, 360000)
    m, cents = divmod(cents, 6000)
    s, cents = divmod(cents, 100)

    if h > 0:
        return "%s:%s:%s.%s" % (h, str(m).zfill(2), str(s).zfill(2), str(cents).zfill(2))
    return "%s:%s.%s" % (str(m).zfill(2), str(s).zfill(2), str(cents).zfill(2))
