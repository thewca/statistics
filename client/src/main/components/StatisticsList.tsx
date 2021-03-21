import { message } from "antd";
import { useEffect, useState } from "react";
import statisticsApi from "../api/statistics.api";
import { StatisticItem } from "../model/StatisticItem";

const StatisticsList = () => {
  const [statisticsList, setStatisticsList] = useState<StatisticItem[]>([]);
  const getStatisticsList = () => {
    statisticsApi
      .getStatisticsList()
      .then((response) => setStatisticsList(response.data))
      .catch((e) => message.error("Error fetching statistics list"));
  };
  useEffect(getStatisticsList, []);
  return (
    <div>
      <h1>Statistics List</h1>
      {statisticsList.length > 0 && (
        <div>
          <ul>
            {statisticsList.map((item) => (
              <li key={item.path}>
                <a href={"statistics-list/" + item.path}>{item.title}</a>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default StatisticsList;
