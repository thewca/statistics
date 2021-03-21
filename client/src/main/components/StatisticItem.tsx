import { useEffect } from "react";
import { useParams } from "react-router-dom";
import statisticsApi from "../api/statistics.api";

interface StatisticsItemProps {
  pathId: string;
}

const StatisticItem = () => {
  const { pathId } = useParams<StatisticsItemProps>();
  useEffect(() => {
    statisticsApi
      .getStatistic(pathId)
      .then((response) => console.log(response));
  });
  return (
    <div>
      <h1>Item</h1>Item {pathId}
    </div>
  );
};

export default StatisticItem;
