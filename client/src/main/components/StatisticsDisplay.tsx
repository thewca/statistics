import { CompassOutlined } from "@ant-design/icons";
import { Col, message, Popover, Row, Select } from "antd";
import { useCallback, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import statisticsApi from "../api/statistics.api";
import { DisplayMode, Statistics } from "../model/Statistic";
import { StatisticsDetail } from "../model/StatisticsDetail";
import { getQueryParameter, setQueryParameter } from "../util/query.param.util";
import "./StatisticsDisplay.css";
import StatisticsTable from "./StatisticsTable";

interface StatisticsDisplayProps {
  pathId: string;
}

const StatisticsDisplay = () => {
  const { pathId } = useParams<StatisticsDisplayProps>();

  const [statistics, setStatistics] = useState<Statistics>();
  const [selectedKeys, setSelectedKeys] = useState<string | undefined>(
    getQueryParameter("selectedKeys") || undefined
  );
  const [filteredStatistics, setFilteredStatistics] = useState<
    StatisticsDetail[]
  >();

  const handleFilteredStatistics = useCallback(
    (statistics: Statistics, selectedKeys: string | null) => {
      switch (statistics.displayMode) {
        case "GROUPED":
          let key =
            selectedKeys ||
            (!!statistics.statistics[0].keys?.length
              ? statistics.statistics[0].keys[0]
              : null);
          if (!!key) {
            setFilteredStatistics(
              statistics.statistics.filter(
                (stat) => !!stat.keys && stat.keys[0] === key
              )
            );
            setSelectedKeys(key);
          } else {
            setFilteredStatistics(statistics.statistics);
          }
          break;
        case "SELECTOR":
          let filtered = statistics.statistics.filter(
            (it) => joinKeys(it.keys) === selectedKeys
          );

          // Display stats based on query parameter
          if (filtered.length > 0) {
            setFilteredStatistics(filtered);
          } else {
            // Otherwise, display the first one
            setFilteredStatistics([statistics.statistics[0]]);
            setSelectedKeys(joinKeys(statistics.statistics[0]?.keys));
          }
          break;
        default:
          setFilteredStatistics(statistics.statistics);
      }
    },
    []
  );

  useEffect(() => {
    statisticsApi
      .getStatistic(pathId)
      .then((response) => {
        setStatistics(response.data);
        handleFilteredStatistics(
          response.data,
          getQueryParameter("selectedKeys")
        );
      })
      .catch(() =>
        message.error("Could not get statistics result for " + pathId)
      );
  }, [pathId, handleFilteredStatistics]);

  useEffect(() => setQueryParameter("selectedKeys", selectedKeys), [
    selectedKeys,
  ]);

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

    let keys = "";
    if (displayMode === "GROUPED" && statisticsDetail.keys.length === 2) {
      keys = statisticsDetail.keys[1];
    } else {
      keys = statisticsDetail.keys.join(" > ");
    }

    return (
      <span className="tags">
        {keys} {getIcon(statisticsDetail)}
      </span>
    );
  };

  const handleChange = (jointKeys: string) => {
    setSelectedKeys(jointKeys);
    setFilteredStatistics(
      statistics?.statistics.filter(
        (it) =>
          // For selector
          it.keys + "" === jointKeys ||
          // For grouped
          (!!it.keys && it.keys[0] === jointKeys)
      )
    );
  };

  const joinKeys = (keys?: string[]) => keys?.join(" > ");

  const getOptions = (statistics?: Statistics) => {
    if (statistics?.displayMode === "SELECTOR") {
      return statistics?.statistics.map((stat) => ({
        value: "" + stat.keys,
        label: joinKeys(stat.keys),
      }));
    }

    // Currently, grouped only works by keys of 2 levels
    if (statistics?.displayMode === "GROUPED") {
      let distinctKeys: string[] = [];
      statistics.statistics
        .filter((stat) => stat.keys?.length === 2)
        .forEach((stat) => {
          if (!distinctKeys.includes(stat.keys![0])) {
            distinctKeys.push(stat.keys![0]);
          }
        });

      return distinctKeys.map((value) => ({ value, label: value }));
    }
  };

  return (
    <div className="container">
      <h1 className="page-title">{statistics?.title}</h1>

      <Row>
        <Col span={8} />
        <Col span={8}>
          {["SELECTOR", "GROUPED"].includes(statistics?.displayMode || "") && (
            <div id="display-mode-wrapper">
              <Select
                value={selectedKeys}
                onChange={handleChange}
                options={getOptions(statistics)}
                style={{ width: "50%" }}
              />
            </div>
          )}
        </Col>
        {!!statistics?.explanation && (
          <Col span={8}>
            <h4 className="explanation">{statistics.explanation}</h4>
          </Col>
        )}
      </Row>
      {!!filteredStatistics &&
        filteredStatistics.map((stat, i) => (
          <div key={i} className="statistics-item">
            <Row>
              <Col span={12}>{showKeys(stat, statistics?.displayMode)}</Col>
              {!!stat.explanation && (
                <Col span={12}>
                  <h3 className="explanation">{stat.explanation}</h3>
                </Col>
              )}
            </Row>
            <StatisticsTable
              headers={stat.headers}
              content={stat.content}
              showPositions={stat.showPositions}
              positionTieBreakerIndex={stat.positionTieBreakerIndex}
              allowInnerHTML
            />
          </div>
        ))}
    </div>
  );
};

export default StatisticsDisplay;
