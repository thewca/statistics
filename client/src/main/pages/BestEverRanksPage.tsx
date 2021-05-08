import { Col, Form, Input, Row } from "antd";
import React, { useCallback, useEffect, useState } from "react";
import statisticsApi from "../api/statistics.api";
import BestEverRank from "../model/BestEverRank";
import "./BestEverRanksPage.css";
import "@cubing/icons";

const BestEverRanksPage = () => {
  const [wcaId, setWcaId] = useState("2015CAMP17");
  const [bestEverRank, setBestEverRank] = useState<BestEverRank[]>();
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
          ></Input>
        </Form.Item>
      </Form>
      {bestEverRank?.map((personCountry, i) => (
        <div key={i}>
          <Row>
            <Col span={3} />
            <Col span={6} className="person-info">
              <strong>WCA ID:</strong> {personCountry.personId}
            </Col>
            <Col span={6} className="person-info">
              <strong>Continent:</strong> {personCountry.continent}
            </Col>
            <Col span={6} className="person-info">
              <strong>Country:</strong> {personCountry.countryId}
            </Col>
          </Row>

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
              {personCountry.ranks.map((rank) => (
                <React.Fragment key={rank.event_id}>
                  <tr>
                    <th rowSpan={3}>
                      <p>{rank.event_id}</p>
                      <span className={`cubing-icon event-${rank.event_id}`} />
                    </th>
                    <th>WR</th>
                    <td>
                      {rank.best_world_single_rank.result != null
                        ? rank.best_world_single_rank.result
                        : "-"}
                    </td>
                    <td>
                      {rank.best_world_single_rank.rank != null
                        ? rank.best_world_single_rank.rank + 1
                        : "-"}
                    </td>
                    <td>
                      {rank.best_world_single_rank.start != null
                        ? rank.best_world_single_rank.start
                        : "-"}
                    </td>
                    <td>
                      {rank.best_world_single_rank.end != null
                        ? rank.best_world_single_rank.end
                        : "-"}
                    </td>
                    <td>
                      {rank.best_world_single_rank.competition != null
                        ? rank.best_world_single_rank.competition
                        : "-"}
                    </td>
                    <td>
                      {rank.best_world_average_rank.result != null
                        ? rank.best_world_average_rank.result
                        : "-"}
                    </td>
                    <td>
                      {rank.best_world_average_rank.rank != null
                        ? rank.best_world_average_rank.rank + 1
                        : "-"}
                    </td>
                    <td>
                      {rank.best_world_average_rank.start != null
                        ? rank.best_world_average_rank.start
                        : "-"}
                    </td>
                    <td>
                      {rank.best_world_average_rank.end != null
                        ? rank.best_world_average_rank.end
                        : "-"}
                    </td>
                    <td>
                      {rank.best_world_average_rank.competition != null
                        ? rank.best_world_average_rank.competition
                        : "-"}
                    </td>
                  </tr>
                  <tr>
                    <th>CR</th>
                    <td>
                      {rank.best_continent_single_rank.result != null
                        ? rank.best_continent_single_rank.result
                        : "-"}
                    </td>
                    <td>
                      {rank.best_continent_single_rank.rank != null
                        ? rank.best_continent_single_rank.rank + 1
                        : "-"}
                    </td>
                    <td>
                      {rank.best_continent_single_rank.start != null
                        ? rank.best_continent_single_rank.start
                        : "-"}
                    </td>
                    <td>
                      {rank.best_continent_single_rank.end != null
                        ? rank.best_continent_single_rank.end
                        : "-"}
                    </td>
                    <td>
                      {rank.best_continent_single_rank.competition != null
                        ? rank.best_continent_single_rank.competition
                        : "-"}
                    </td>
                    <td>
                      {rank.best_continent_average_rank.result != null
                        ? rank.best_continent_average_rank.result
                        : "-"}
                    </td>
                    <td>
                      {rank.best_continent_average_rank.rank != null
                        ? rank.best_continent_average_rank.rank + 1
                        : "-"}
                    </td>
                    <td>
                      {rank.best_continent_average_rank.start != null
                        ? rank.best_continent_average_rank.start
                        : "-"}
                    </td>
                    <td>
                      {rank.best_continent_average_rank.end != null
                        ? rank.best_continent_average_rank.end
                        : "-"}
                    </td>
                    <td>
                      {rank.best_continent_average_rank.competition != null
                        ? rank.best_continent_average_rank.competition
                        : "-"}
                    </td>
                  </tr>
                  <tr>
                    <th>NR</th>
                    <td>
                      {rank.best_country_single_rank.result != null
                        ? rank.best_country_single_rank.result
                        : "-"}
                    </td>
                    <td>
                      {rank.best_country_single_rank.rank != null
                        ? rank.best_country_single_rank.rank + 1
                        : "-"}
                    </td>
                    <td>
                      {rank.best_country_single_rank.start != null
                        ? rank.best_country_single_rank.start
                        : "-"}
                    </td>
                    <td>
                      {rank.best_country_single_rank.end != null
                        ? rank.best_country_single_rank.end
                        : "-"}
                    </td>
                    <td>
                      {rank.best_country_single_rank.competition != null
                        ? rank.best_country_single_rank.competition
                        : "-"}
                    </td>
                    <td>
                      {rank.best_country_average_rank.result != null
                        ? rank.best_country_average_rank.result
                        : "-"}
                    </td>
                    <td>
                      {rank.best_country_average_rank.rank != null
                        ? rank.best_country_average_rank.rank + 1
                        : "-"}
                    </td>
                    <td>
                      {rank.best_country_average_rank.start != null
                        ? rank.best_country_average_rank.start
                        : "-"}
                    </td>
                    <td>
                      {rank.best_country_average_rank.end != null
                        ? rank.best_country_average_rank.end
                        : "-"}
                    </td>
                    <td>
                      {rank.best_country_average_rank.competition != null
                        ? rank.best_country_average_rank.competition
                        : "-"}
                    </td>
                  </tr>
                </React.Fragment>
              ))}
            </tbody>
          </table>
        </div>
      ))}
    </div>
  );
};

export default BestEverRanksPage;
