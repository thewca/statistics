import { Collapse, List, Switch } from "antd";
import { useState } from "react";
import { Link } from "react-router-dom";
import { StatisticsList } from "../model/StatisticsList";
import "./StatisticsListPage.css";

const { Panel } = Collapse;

interface StatisticsListPageProps {
  statisticsList?: StatisticsList;
}

const StatisticsListPage = ({ statisticsList }: StatisticsListPageProps) => {
  const [completeList, setCompleteList] = useState(false);
  let dataSource = statisticsList?.list?.flatMap((it) => it.statistics);
  return (
    <>
      <h1 className="page-title">Statistics List</h1>
      <div id="switch">
        <Switch
          checked={completeList}
          onChange={setCompleteList}
          checkedChildren="Complete list"
          unCheckedChildren="Grouped list"
        />
      </div>

      {!completeList && (
        <Collapse
          defaultActiveKey={statisticsList?.list?.map((stat) => stat.group)}
        >
          {(statisticsList?.list.length || 0) > 0 &&
            statisticsList?.list.map((statisticsGroup, i) => (
              <Panel
                key={statisticsGroup.group}
                header={statisticsGroup.group}
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
