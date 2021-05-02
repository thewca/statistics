import { Badge, Col, Collapse, Form, Input, List, Row, Switch } from "antd";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import { StatisticsList } from "../model/StatisticsList";
import "./StatisticsListPage.css";

const { Panel } = Collapse;

interface StatisticsListPageProps {
  statisticsList?: StatisticsList;
}

const StatisticsListPage = ({ statisticsList }: StatisticsListPageProps) => {
  const [search, setSearch] = useState("");
  const [completeList, setCompleteList] = useState(false);
  let dataSource = statisticsList?.list
    ?.flatMap((it) => it.statistics)
    .sort((a, b) => (a.title < b.title ? -1 : 1));
  return (
    <>
      <h1 className="page-title">Statistics List</h1>

      <Row>
        <Col span={18}>
          <div id="switch">
            <Switch
              checked={completeList}
              onChange={setCompleteList}
              checkedChildren="Complete list"
              unCheckedChildren="Grouped list"
            />
            {completeList && (
              <Badge
                count={statisticsList?.totalSize}
                className="badge-count"
              />
            )}
          </div>
        </Col>
        <Col span={6}>
          <Form>
            <Form.Item
              label={
                <>
                  <strong>Search in statistics</strong>
                </>
              }
            >
              <Input.Search
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </Form.Item>
          </Form>
        </Col>
      </Row>

      {!completeList && (
        <Collapse
          defaultActiveKey={statisticsList?.list?.map((stat) => stat.group)}
        >
          {(statisticsList?.list.length || 0) > 0 &&
            statisticsList?.list.map((statisticsGroup, i) => (
              <Panel
                key={statisticsGroup.group}
                header={
                  <>
                    {statisticsGroup.group}{" "}
                    <Badge
                      count={statisticsGroup.size}
                      className="badge-count"
                    />
                  </>
                }
                className="list-item"
              >
                <ul>
                  {statisticsGroup.statistics.map((stat) => (
                    <li key={stat.path}>
                      <Link to={`/statistics-list/${stat.path}`}>
                        {stat.title}
                      </Link>
                    </li>
                  ))}
                </ul>
              </Panel>
            ))}
        </Collapse>
      )}
      {completeList && (
        <List
          dataSource={dataSource}
          bordered
          renderItem={(stat) => (
            <List.Item key={stat.path}>
              <Link to={`/statistics-list/${stat.path}`}>{stat.title}</Link>
            </List.Item>
          )}
        />
      )}
    </>
  );
};

export default StatisticsListPage;
