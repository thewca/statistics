import { CompassOutlined } from "@ant-design/icons";
import { message, Popover } from "antd";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import statisticsApi from "../api/statistics.api";
import { Statistics } from "../model/Statistic";
import { StatisticsDetail } from "../model/StatisticsDetail";
import "./StatisticsDisplay.css";
import StatisticsTable from "./StatisticsTable";

interface StatisticsDisplayProps {
  pathId: string;
}

const StatisticsDisplay = () => {
  const [statistics, setStatistics] = useState<Statistics>();
  const { pathId } = useParams<StatisticsDisplayProps>();
  useEffect(() => {
    statisticsApi
      .getStatistic(pathId)
      .then((response) => setStatistics(response.data))
      .catch(() =>
        message.error("Could not get statistics result for " + pathId)
      );
  }, [pathId]);

  const getIcon = (statisticsDetail: StatisticsDetail) => {
    if (!statisticsDetail.sqlQueryCustom) {
      return null;
    }

    return (
      <Popover content="Find me">
        <Link
          to={`/database-query?sqlQuery=${statisticsDetail.sqlQueryCustom}`}
        >
          <CompassOutlined />
        </Link>
      </Popover>
    );
  };

  const showKeys = (statisticsDetail: StatisticsDetail) => {
    if (!statisticsDetail.keys || !statisticsDetail.keys.length) {
      return null;
    }

    return (
      <span className="tags">
        {statisticsDetail.keys.join(" > ")} {getIcon(statisticsDetail)}
      </span>
    );
  };

  return (
    <div className="container">
      <h1 className="page-title">{statistics?.title}</h1>
      {!!statistics?.explanation && (
        <h5 className="text-right mr-5">{statistics.explanation}</h5>
      )}
      {!!statistics &&
        statistics.statistics.map((stat, i) => (
          <div key={i}>
            {showKeys(stat)}
            {!!stat.explanation && <h5>{stat.explanation}</h5>}
            <StatisticsTable
              headers={stat.headers}
              content={stat.content}
              allowInnerHTML={true}
            />
          </div>
        ))}
    </div>
  );
};

export default StatisticsDisplay;
