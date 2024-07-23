# This is a quick fix to ignore the line
# /*!999999\- enable the sandbox mode */
# This line is causing an error when importing the database dump
c = 0
new_lines = []
with open("wca-developer-database-dump.sql", "r") as f:
    for line in f.readlines():
        if c > 0:
            new_lines.append(line)
        c += 1

with open("wca-developer-database-dump.sql", "w") as f:
    f.writelines(new_lines)
