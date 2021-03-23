import { message } from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import statisticsApi from "../api/statistics.api";
import Statistics from "../model/Statistic";
import "./StatisticsDetail.css";
import StatisticsTable from "./StatisticsTable";

interface StatisticsDetailProps {
  pathId: string;
}

const StatisticsDetail = () => {
  const [statistics, setStatistics] = useState<Statistics>();
  const { pathId } = useParams<StatisticsDetailProps>();
  useEffect(() => {
    statisticsApi
      .getStatistic(pathId)
      .then((response) => setStatistics(response.data))
      .catch(() =>
        message.error("Could not get statistics result for " + pathId)
      );
  });

  const showKeys = (keys?: string[]) => {
    if (!keys || !keys.length) {
      return null;
    }

    return <h3 className="keys">{keys.join(" > ")}</h3>;
  };

  return (
    <div className="container">
      <h1 className="page-title">{statistics?.title}</h1>
      {!!statistics &&
        statistics.statistics.map((stat, i) => (
          <div key={i}>
            {showKeys(stat.keys)}
            <StatisticsTable
              headers={statistics.headers}
              content={stat.content}
              allowInnerHTML={true}
            />
          </div>
        ))}
    </div>
  );
};

export default StatisticsDetail;
