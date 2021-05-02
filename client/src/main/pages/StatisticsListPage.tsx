import {
  Alert,
  Badge,
  Col,
  Collapse,
  Form,
  Input,
  List,
  Row,
  Switch,
  Tag,
} from "antd";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import statisticsApi from "../api/statistics.api";
import NoContent from "../components/NoContent";
import { StatisticsList } from "../model/StatisticsList";
import "./StatisticsListPage.css";

const { Panel } = Collapse;

interface StatisticsListPageProps {
  statisticsList?: StatisticsList;
}

const StatisticsListPage = ({ statisticsList }: StatisticsListPageProps) => {
  const [term, setTerm] = useState("");
  const [searchedList, setSearchedList] = useState(statisticsList);
  const [completeList, setCompleteList] = useState(false);
  const [lastSearchedTerm, setLastSearchedTerm] = useState<string>();
  let dataSource = searchedList?.list
    ?.flatMap((it) => it.statistics)
    .sort((a, b) => (a.title < b.title ? -1 : 1));

  const handleSearch = (term: string) => {
    statisticsApi.getStatisticsGroups(term).then((response) => {
      setSearchedList(response.data);
      setLastSearchedTerm(term);
    });
  };

  const handleTermChange = (newTerm: string) => {
    setTerm(newTerm);
    setSearchedList(statisticsList);
    setLastSearchedTerm(undefined);
  };
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
              <Badge count={searchedList?.totalSize} className="badge-count" />
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
                value={term}
                onChange={(e) => handleTermChange(e.target.value)}
                onPressEnter={() => handleSearch(term)}
              />
            </Form.Item>
          </Form>
        </Col>
      </Row>

      {!!lastSearchedTerm && (
        <Tag>
          Showing results for <strong>{lastSearchedTerm}</strong>
        </Tag>
      )}
      {!completeList && !!searchedList?.totalSize && (
        <Collapse
          defaultActiveKey={searchedList?.list?.map((stat) => stat.group)}
        >
          {(searchedList?.list.length || 0) > 0 &&
            searchedList?.list.map((statisticsGroup, i) => (
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
      {completeList && !!searchedList?.totalSize && (
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
      {!searchedList?.totalSize && <NoContent />}
    </>
  );
};

export default StatisticsListPage;
