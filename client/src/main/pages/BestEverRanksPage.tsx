import { UserOutlined } from "@ant-design/icons";
import "@cubing/icons";
import { Col, Form, Input, message, Row, Tag, Tooltip } from "antd";
import React, { useCallback, useEffect, useState } from "react";
import statisticsApi from "../api/statistics.api";
import BestEverRank from "../model/BestEverRank";
import Competitor from "../model/Competitor";
import Results from "../model/Results";
import { getQueryParameter, setQueryParameter } from "../util/query.param.util";
import formatResult, { ResultType } from "../util/result.util";
import "./BestEverRanksPage.css";

const BestEverRanksPage = () => {
  const [wcaId, setWcaId] = useState(getQueryParameter("wcaId") || "");
  const [bestEverRank, setBestEverRank] = useState<BestEverRank>();
  const [showingFor, setShowingFor] = useState("");

  const handleSubmit = useCallback((wcaId: string) => {
    setQueryParameter("wcaId", wcaId);
    statisticsApi
      .getRanks(wcaId)
      .then((response) => {
        setBestEverRank(response.data);
        setShowingFor(wcaId);
      })
      .catch(() => message.error("Failed to search."));
  }, []);

  const getRank = (rank: number) => {
    return rank != null ? (
      <span className={rank < 10 ? "top-10-rank" : ""}>{rank + 1}</span>
    ) : null;
  };

  const getCompetitionLink = (
    compeitionId: string,
    competitionName?: string
  ) => (
    <a
      href={`https://www.worldcubeassociation.org/competitions/${compeitionId}`}
    >
      {competitionName || compeitionId}
    </a>
  );

  const getResults = (results: Results, event_id: string, type: ResultType) => (
    <>
      <td>{formatResult(results.best_rank.result, event_id, type)}</td>
      <td>{getRank(results.best_rank.rank)}</td>
      <td>{results.best_rank.start}</td>
      <td>{results.best_rank.end}</td>
      <td>{getCompetitionLink(results.best_rank.competition)}</td>
    </>
  );

  const getCompetitor = (
    competitor: Competitor,
    event_id: string,
    rank_type: string,
    tooltip?: string
  ) => (
    <>
      <th>
        <Tooltip title={tooltip}>{rank_type}</Tooltip>
      </th>
      {getResults(competitor.single, event_id, "single")}
      {getResults(competitor.average, event_id, "average")}
    </>
  );

  const getResultsHeaders = () => {
    return (
      <>
        <th scope="row">Result</th>
        <th scope="row">Best Rank</th>
        <th scope="row">Start</th>
        <th scope="row">End</th>
        <th scope="row">Competition</th>
      </>
    );
  };

  // If we find an ID, already fetch results
  useEffect(() => {
    let wcaId = getQueryParameter("wcaId");
    if (!!wcaId) {
      handleSubmit(wcaId);
    }
  }, [handleSubmit]);

  return (
    <div className="ranks-wrapper">
      <h1 className="page-title">Best Ever Ranks</h1>
      <Form onFinish={() => handleSubmit(wcaId)}>
        <Row>
          <Col span={9} />
          <Col span={6}>
            <Form.Item>
              <Input
                prefix={<UserOutlined />}
                placeholder="WCA ID"
                value={wcaId}
                onChange={(e) => setWcaId(e.target.value)}
                minLength={10}
                maxLength={10}
                required
              />
            </Form.Item>
          </Col>
        </Row>
      </Form>
      {!!bestEverRank && (
        <table className="ranks-table">
          <thead>
            <tr>
              <td colSpan={12} className="showing-for">
                <Tag>
                  Showing results for <strong>{showingFor}</strong>
                </Tag>
              </td>
            </tr>
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
              {getResultsHeaders()}
              {getResultsHeaders()}
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
                      <p className="event-name">{eventRank.event.name}</p>
                    </th>
                    {getCompetitor(world, eventRank.event.event_id, "WR")}
                  </tr>
                ))}
                {eventRank.continents.map((continent) => (
                  <tr key={continent.continent}>
                    {getCompetitor(
                      continent,
                      eventRank.event.event_id,
                      "CR",
                      continent.continent
                    )}
                  </tr>
                ))}
                {eventRank.countries.map((country) => (
                  <tr key={country.country}>
                    {getCompetitor(
                      country,
                      eventRank.event.event_id,
                      "NR",
                      country.country
                    )}
                  </tr>
                ))}
                <tr className="empty-row">
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
