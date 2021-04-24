import { Collapse } from "antd";
import { Link } from "react-router-dom";
import { StatisticsGroup } from "../model/StatisticItem";
import "./StatisticsList.css";

const { Panel } = Collapse;

interface StatisticsListProps {
  statisticsGroups?: StatisticsGroup[];
}

const StatisticsList = ({ statisticsGroups }: StatisticsListProps) => {
  return (
    <>
      <h1 className="page-title">Statistics List</h1>
      <Collapse defaultActiveKey={statisticsGroups?.map((stat) => stat.group)}>
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
    </>
  );
};

export default StatisticsList;
