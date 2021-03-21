import { useParams } from "react-router-dom";

interface StatisticsItemProps {
  pathId: string;
}

const StatisticItem = () => {
  const { pathId } = useParams<StatisticsItemProps>();
  return (
    <div>
      <h1>Item</h1>Item {pathId}
    </div>
  );
};

export default StatisticItem;
