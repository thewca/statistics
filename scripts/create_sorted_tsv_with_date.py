import bisect
import logging

import pandas as pd

logging.basicConfig(level=logging.INFO)
log = logging.getLogger()


def main():
    """This sorts (date, competition, round) WCA_export_Results and creates a new tsv so it can be reused for other programs."""

    log.info("React tsv")
    data_results = pd.read_csv('WCA_export/WCA_export_Results.tsv', sep='\t')
    data_competition = pd.read_csv(
        'WCA_export/WCA_export_Competitions.tsv', sep='\t')

    # WCA sorts WCA_export_Competitions based on not case sensitive competitionId
    # This messes with bisect, so we create a competitionId list all caps
    competition_id_competition_all_caps = [
        x.upper() for x in data_competition["id"]]

    # first we build columns with year, month and date to place with results
    years = []
    months = []
    days = []

    prev = None
    year = None
    month = None
    day = None

    log.info("Build dates")
    for competitionId in data_results["competitionId"]:
        if competitionId != prev:
            # we match competitionId.upper here
            i = bisect.bisect_left(
                competition_id_competition_all_caps, competitionId.upper())
            year = (data_competition["year"])[i]
            month = (data_competition["month"])[i]
            day = (data_competition["day"])[i]
        years.append(year)
        months.append(month)
        days.append(day)

        prev = competitionId

    data_results["year"] = years
    data_results["month"] = months
    data_results["day"] = days

    log.info("Release unecessary data")
    del data_competition, competition_id_competition_all_caps

    log.info("Sort data")
    round_sorter = ['h', '0', 'd', '1', 'b', '2', 'e', 'g', '3', 'c', 'f']
    data_results.sort_values(by=["year", "month", "day", "competitionId", "eventId", "roundTypeId", "pos"], ascending=[
                             True, True, True, True, True, round_sorter, False], inplace=True)

    log.info("Save to WCA_export_Results_Ordered.tsv")
    data_results.to_csv("WCA_export/WCA_export_Results_Ordered.tsv",
                        sep='\t', encoding='utf-8', index=False)
    log.info("Complete")


if __name__ == "__main__":
    main()
