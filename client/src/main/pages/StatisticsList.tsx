import { Collapse, List, Switch } from "antd";
import { useState } from "react";
import { Link } from "react-router-dom";
import { StatisticsGroup } from "../model/StatisticItem";
import "./StatisticsList.css";

const { Panel } = Collapse;

interface StatisticsListProps {
  statisticsGroups?: StatisticsGroup[];
}

const StatisticsList = ({ statisticsGroups }: StatisticsListProps) => {
  const [completeList, setCompleteList] = useState(true);
  let dataSource = statisticsGroups
    ?.flatMap((it) => it.statistics)
    .sort((a, b) => (a.title < b.title ? -1 : 1));
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
          defaultActiveKey={statisticsGroups?.map((stat) => stat.group)}
        >
          {(statisticsGroups?.length || 0) > 0 &&
            statisticsGroups?.map((statisticsGroup, i) => (
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

export default StatisticsList;
