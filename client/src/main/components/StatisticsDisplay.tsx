import { CompassOutlined } from "@ant-design/icons";
import { message, Popover, Select } from "antd";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import statisticsApi from "../api/statistics.api";
import { DisplayMode, Statistics } from "../model/Statistic";
import { StatisticsDetail } from "../model/StatisticsDetail";
import "./StatisticsDisplay.css";
import StatisticsTable from "./StatisticsTable";

interface StatisticsDisplayProps {
  pathId: string;
}

const StatisticsDisplay = () => {
  const { pathId } = useParams<StatisticsDisplayProps>();

  const [statistics, setStatistics] = useState<Statistics>();
  const [selectedKeys, setSelectedKeys] = useState<string>();
  const [filteredStatistics, setFilteredStatistics] = useState<
    StatisticsDetail[]
  >();

  useEffect(() => {
    statisticsApi
      .getStatistic(pathId)
      .then((response) => {
        setStatistics(response.data);
        setFilteredStatistics([response.data.statistics[0]]);
        setSelectedKeys(joinKeys(response.data.statistics[0].keys));
      })
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

  const showKeys = (
    statisticsDetail: StatisticsDetail,
    displayMode?: DisplayMode
  ) => {
    if (
      !statisticsDetail.keys ||
      !statisticsDetail.keys.length ||
      displayMode === "SELECTOR"
    ) {
      return null;
    }

    return (
      <span className="tags">
        {statisticsDetail.keys.join(" > ")} {getIcon(statisticsDetail)}
      </span>
    );
  };

  const handleChange = (jointKeys: string) => {
    setSelectedKeys(jointKeys);
    setFilteredStatistics(
      statistics?.statistics.filter((it) => it.keys + "" === jointKeys)
    );
  };

  const joinKeys = (keys?: string[]) => keys?.join(" > ");

  const getOptions = (statistics?: Statistics) => {
    return statistics?.statistics.map((stat) => ({
      value: "" + stat.keys,
      label: joinKeys(stat.keys),
    }));
  };

  return (
    <div className="container">
      <h1 className="page-title">{statistics?.title}</h1>
      {!!statistics?.explanation && (
        <h5 className="text-right mr-5">{statistics.explanation}</h5>
      )}
      {statistics?.displayMode === "SELECTOR" && (
        <div id="display-mode-wrapper">
          <Select
            value={selectedKeys}
            onChange={handleChange}
            options={getOptions(statistics)}
            style={{ width: "50%" }}
          />
        </div>
      )}
      {!!filteredStatistics &&
        filteredStatistics.map((stat, i) => (
          <div key={i}>
            {showKeys(stat, statistics?.displayMode)}
            {!!stat.explanation && (
              <h6 className="text-right mr-5 mt-4 text-muted">
                {stat.explanation}
              </h6>
            )}
            <StatisticsTable
              headers={stat.headers}
              content={stat.content}
              allowInnerHTML
            />
          </div>
        ))}
    </div>
  );
};

export default StatisticsDisplay;
