import "@cubing/icons";
import { Form, Input, Popover, Tooltip } from "antd";
import React, { useCallback, useEffect, useState } from "react";
import statisticsApi from "../api/statistics.api";
import BestEverRank from "../model/BestEverRank";
import formatResult from "../util/result.util";
import "./BestEverRanksPage.css";

const BestEverRanksPage = () => {
  const [wcaId, setWcaId] = useState("2015CAMP17");
  const [bestEverRank, setBestEverRank] = useState<BestEverRank>();
  const handleSubmit = useCallback((wcaId: string) => {
    statisticsApi.getRanks(wcaId).then((response) => {
      setBestEverRank(response.data);
    });
  }, []);

  useEffect(() => {
    handleSubmit("2015CAMP17");
  }, [handleSubmit]);
  return (
    <div className="ranks-wrapper">
      <Form onFinish={() => handleSubmit(wcaId)}>
        <Form.Item>
          <Input
            placeholder="WCA ID"
            value={wcaId}
            onChange={(e) => setWcaId(e.target.value)}
          />
        </Form.Item>
      </Form>
      {!!bestEverRank && (
        <table className="ranks-table">
          <thead>
            <tr>
              <th colSpan={2}></th>
              <th scope="col" colSpan={5}>
                Single
              </th>
              <th scope="col" colSpan={5}>
                Average
              </th>
            </tr>
            <tr>
              <th scope="row">Event</th>
              <th scope="row">Rank</th>
              <th scope="row">Result</th>
              <th scope="row">Best Rank</th>
              <th scope="row">Start</th>
              <th scope="row">End</th>
              <th scope="row">Competition</th>
              <th scope="row">Result</th>
              <th scope="row">Best Rank</th>
              <th scope="row">Start</th>
              <th scope="row">End</th>
              <th scope="row">Competition</th>
            </tr>
          </thead>
          <tbody>
            {bestEverRank.eventRanks.map((eventRank) => (
              <React.Fragment key={eventRank.event.event_id}>
                {eventRank.worlds.map((world, i) => (
                  <tr key={i}>
                    <th
                      rowSpan={
                        eventRank.worlds.length +
                        eventRank.continents.length +
                        eventRank.countries.length
                      }
                    >
                      <span
                        className={`cubing-icon event-${eventRank.event.event_id}`}
                      />
                    </th>
                    <th>WR</th>
                    <td>
                      {formatResult(
                        world.single.best_rank.result,
                        eventRank.event.event_id,
                        "single"
                      )}
                    </td>
                    <td>
                      {world.single.best_rank.rank != null
                        ? world.single.best_rank.rank + 1
                        : null}
                    </td>
                    <td>{world.single.best_rank.start}</td>
                    <td>{world.single.best_rank.end}</td>
                    <td>{world.single.best_rank.competition}</td>
                    <td>
                      {formatResult(
                        world.average.best_rank.result,
                        eventRank.event.event_id,
                        "average"
                      )}
                    </td>
                    <td>{world.average.best_rank.rank}</td>
                    <td>{world.average.best_rank.start}</td>
                    <td>{world.average.best_rank.end}</td>
                    <td>{world.average.best_rank.competition}</td>
                  </tr>
                ))}
                {eventRank.continents.map((continent) => (
                  <tr key={continent.continent}>
                    <th>
                      <Tooltip title={continent.continent}>CR</Tooltip>
                    </th>
                    <td>
                      {formatResult(
                        continent.single.best_rank.result,
                        eventRank.event.event_id,
                        "single"
                      )}
                    </td>
                    <td>
                      {continent.single.best_rank.rank != null
                        ? continent.single.best_rank.rank + 1
                        : null}
                    </td>
                    <td>{continent.single.best_rank.start}</td>
                    <td>{continent.single.best_rank.end}</td>
                    <td>{continent.single.best_rank.competition}</td>
                    <td>
                      {formatResult(
                        continent.average.best_rank.result,
                        eventRank.event.event_id,
                        "average"
                      )}
                    </td>
                    <td>{continent.average.best_rank.rank}</td>
                    <td>{continent.average.best_rank.start}</td>
                    <td>{continent.average.best_rank.end}</td>
                    <td>{continent.average.best_rank.competition}</td>
                  </tr>
                ))}
                {eventRank.countries.map((country) => (
                  <tr key={country.country}>
                    <th>
                      <Tooltip title={country.country}>NR</Tooltip>
                    </th>
                    <td>
                      {formatResult(
                        country.single.best_rank.result,
                        eventRank.event.event_id,
                        "single"
                      )}
                    </td>
                    <td>
                      {country.single.best_rank.rank != null
                        ? country.single.best_rank.rank + 1
                        : null}
                    </td>
                    <td>{country.single.best_rank.start}</td>
                    <td>{country.single.best_rank.end}</td>
                    <td>{country.single.best_rank.competition}</td>
                    <td>
                      {formatResult(
                        country.average.best_rank.result,
                        eventRank.event.event_id,
                        "average"
                      )}
                    </td>
                    <td>{country.average.best_rank.rank}</td>
                    <td>{country.average.best_rank.start}</td>
                    <td>{country.average.best_rank.end}</td>
                    <td>{country.average.best_rank.competition}</td>
                  </tr>
                ))}
                <tr>
                  <th colSpan={12}></th>
                </tr>
              </React.Fragment>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default BestEverRanksPage;
