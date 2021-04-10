import { Link } from "react-router-dom";
import { StatisticsItem } from "../model/StatisticItem";
import "./StatisticsList.css";

interface StatisticsListProps {
  statisticsList: StatisticsItem[];
}

const StatisticsList = ({ statisticsList }: StatisticsListProps) => {
  return (
    <div className="container">
      <h1 className="page-title">Statistics List</h1>
      {statisticsList.length > 0 && (
        <ul>
          {statisticsList.map((statisticsItem) => (
            <li key={statisticsItem.path} className="list-item">
              <Link to={`/statistics-list/${statisticsItem.path}`}>
                {statisticsItem.title}
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default StatisticsList;
