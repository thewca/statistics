import {
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
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import statisticsApi from "../api/statistics.api";
import NoContent from "../components/NoContent";
import { StatisticsList } from "../model/StatisticsList";
import { getQueryParameter, setQueryParameter } from "../util/query.param.util";
import "./StatisticsListPage.css";

const { Panel } = Collapse;

interface StatisticsListPageProps {
  statisticsList?: StatisticsList;
}

const StatisticsListPage = ({ statisticsList }: StatisticsListPageProps) => {
  // Searched list is a copy that gets refreshed on term change (to the fetched list)
  // or holds the searched list filtered by term
  const [term, setTerm] = useState(getQueryParameter("term") || "");
  const [searchedList, setSearchedList] = useState<StatisticsList>();
  const [completeList, setCompleteList] = useState(false);
  const [lastSearchedTerm, setLastSearchedTerm] = useState<string>();

  let dataSource = searchedList?.list
    ?.flatMap((it) => it.statistics)
    .sort((a, b) => (a.title < b.title ? -1 : 1));

  const handleSearch = (term: string) => {
    setQueryParameter("term", term);
    statisticsApi.getStatisticsGroups(term).then((response) => {
      setSearchedList(response.data);
      setLastSearchedTerm(term);
    });
  };

  useEffect(() => {
    // If there is a term, we let it to handle search
    let initialTerm = getQueryParameter("term");
    if (!initialTerm) {
      setSearchedList(statisticsList);
    } else {
      handleSearch(initialTerm);
    }
  }, [statisticsList]);

  return (
    <>
      <h1 className="page-title">Statistics List</h1>

      <Row justify="center">
        <Col xs={6} md={12}>
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
        <Col xs={24} md={12}>
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
                onChange={(e) => setTerm(e.target.value)}
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
            searchedList?.list.map((statisticsGroup) => (
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
