def get_mbld_points(result):
    missed = int(result[-2:])
    difference = int(result[:2])
    points = 99-difference
    return points, difference, missed
