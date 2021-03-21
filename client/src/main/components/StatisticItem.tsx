import { message } from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import statisticsApi from "../api/statistics.api";
import Statistics from "../model/Statistic";
import StatisticsTable from "./StatisticsTable";

interface StatisticsItemProps {
  pathId: string;
}

const StatisticItem = () => {
  const [statistics, setStatistics] = useState<Statistics>();
  const { pathId } = useParams<StatisticsItemProps>();
  useEffect(() => {
    statisticsApi
      .getStatistic(pathId)
      .then((response) => setStatistics(response.data))
      .catch((e) =>
        message.error("Could not get statistics result for " + pathId)
      );
  });
  return (
    <div>
      {!!statistics &&
        statistics.statistics.map((stat, i) => (
          <div key={i}>
            <StatisticsTable
              headers={statistics.headers}
              content={stat.content}
            />
          </div>
        ))}
    </div>
  );
};

export default StatisticItem;
