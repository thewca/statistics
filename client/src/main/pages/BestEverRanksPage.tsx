import "@cubing/icons";
import { Form, Input, Popover, Tooltip } from "antd";
import React, { useCallback, useEffect, useState } from "react";
import statisticsApi from "../api/statistics.api";
import BestEverRank from "../model/BestEverRank";
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
                    <td>{world.single.best_rank.result}</td>
                    <td>{world.single.best_rank.rank}</td>
                    <td>{world.single.best_rank.start}</td>
                    <td>{world.single.best_rank.end}</td>
                    <td>{world.single.best_rank.competition}</td>
                    <td>{world.average.best_rank.result}</td>
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
                    <td>{continent.single.best_rank.result}</td>
                    <td>{continent.single.best_rank.rank}</td>
                    <td>{continent.single.best_rank.start}</td>
                    <td>{continent.single.best_rank.end}</td>
                    <td>{continent.single.best_rank.competition}</td>
                    <td>{continent.average.best_rank.result}</td>
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
                    <td>{country.single.best_rank.result}</td>
                    <td>{country.single.best_rank.rank}</td>
                    <td>{country.single.best_rank.start}</td>
                    <td>{country.single.best_rank.end}</td>
                    <td>{country.single.best_rank.competition}</td>
                    <td>{country.average.best_rank.result}</td>
                    <td>{country.average.best_rank.rank}</td>
                    <td>{country.average.best_rank.start}</td>
                    <td>{country.average.best_rank.end}</td>
                    <td>{country.average.best_rank.competition}</td>
                  </tr>
                ))}
                {/* <tr>
                  <th>WR</th>
                  <td>
                    {eventRank.best_world_single_rank.result != null
                      ? eventRank.best_world_single_rank.result
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_world_single_rank.rank != null
                      ? eventRank.best_world_single_rank.rank + 1
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_world_single_rank.start != null
                      ? eventRank.best_world_single_rank.start
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_world_single_rank.end != null
                      ? eventRank.best_world_single_rank.end
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_world_single_rank.competition != null
                      ? eventRank.best_world_single_rank.competition
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_world_average_rank.result != null
                      ? eventRank.best_world_average_rank.result
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_world_average_rank.rank != null
                      ? eventRank.best_world_average_rank.rank + 1
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_world_average_rank.start != null
                      ? eventRank.best_world_average_rank.start
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_world_average_rank.end != null
                      ? eventRank.best_world_average_rank.end
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_world_average_rank.competition != null
                      ? eventRank.best_world_average_rank.competition
                      : "-"}
                  </td>
                </tr>
                <tr>
                  <th>CR</th>
                  <td>
                    {eventRank.best_continent_single_rank.result != null
                      ? eventRank.best_continent_single_rank.result
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_continent_single_rank.rank != null
                      ? eventRank.best_continent_single_rank.rank + 1
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_continent_single_rank.start != null
                      ? eventRank.best_continent_single_rank.start
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_continent_single_rank.end != null
                      ? eventRank.best_continent_single_rank.end
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_continent_single_rank.competition != null
                      ? eventRank.best_continent_single_rank.competition
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_continent_average_rank.result != null
                      ? eventRank.best_continent_average_rank.result
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_continent_average_rank.rank != null
                      ? eventRank.best_continent_average_rank.rank + 1
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_continent_average_rank.start != null
                      ? eventRank.best_continent_average_rank.start
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_continent_average_rank.end != null
                      ? eventRank.best_continent_average_rank.end
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_continent_average_rank.competition != null
                      ? eventRank.best_continent_average_rank.competition
                      : "-"}
                  </td>
                </tr>
                <tr>
                  <th>NR</th>
                  <td>
                    {eventRank.best_country_single_rank.result != null
                      ? eventRank.best_country_single_rank.result
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_country_single_rank.rank != null
                      ? eventRank.best_country_single_rank.rank + 1
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_country_single_rank.start != null
                      ? eventRank.best_country_single_rank.start
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_country_single_rank.end != null
                      ? eventRank.best_country_single_rank.end
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_country_single_rank.competition != null
                      ? eventRank.best_country_single_rank.competition
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_country_average_rank.result != null
                      ? eventRank.best_country_average_rank.result
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_country_average_rank.rank != null
                      ? eventRank.best_country_average_rank.rank + 1
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_country_average_rank.start != null
                      ? eventRank.best_country_average_rank.start
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_country_average_rank.end != null
                      ? eventRank.best_country_average_rank.end
                      : "-"}
                  </td>
                  <td>
                    {eventRank.best_country_average_rank.competition != null
                      ? eventRank.best_country_average_rank.competition
                      : "-"}
                  </td>
                </tr> */}
              </React.Fragment>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default BestEverRanksPage;
