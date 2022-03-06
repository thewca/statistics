import { Button, message, Select } from "antd";
import { isEqual, omit } from "lodash";
import { useCallback, useEffect, useState } from "react";
import {
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  ReferenceArea,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import { CategoricalChartState } from "recharts/types/chart/generateCategoricalChart";
import recordEvolutionApi from "../api/RecordEvolutionApi";
import { Evolution } from "../model/RecordEvolution";
import { millsToDate } from "../util/DateUtil";
import formatResult, { getMbldPoints } from "../util/result.util";

const LINES = [
  { key: 1, type: "best", label: "Single", color: "#82ca9d" },
  { key: 1, type: "avg", label: "Average", color: "#82ca9d" },
  { key: 10, type: "best", label: "Single", color: "#8884d8" },
  { key: 10, type: "avg", label: "Average", color: "#8884d8" },
  { key: 100, type: "best", label: "Single", color: "#ff7300" },
  { key: 100, type: "avg", label: "Average", color: "#ff7300" },
];

const omitDate = (evolution: Evolution) => omit(evolution, ["date"]);

const getFormattedResult = (
  value: any,
  eventId: string,
  type: string,
  key: number
) => {
  let result = value[type + key];
  if (!result) {
    return;
  }
  if (eventId === "333mbf") {
    return getMbldPoints(result);
  }
  if (eventId === "333fm" && type === "best") {
    return 100 * result;
  }
  return result;
};

export const RecordEvolutionPage = () => {
  const [data, setData] = useState<Evolution[]>([]);
  const [filteredData, setFilteredData] = useState<Evolution[]>([]);
  const [eventId, setEventId] = useState<string>();
  const [options, setOptions] = useState<{ label: string; value: string }[]>(
    []
  );
  const [leftChartDate, setLeftChartDate] = useState<number>();
  const [rightChartDate, setRightChartDate] = useState<number>();

  const fetchData = useCallback((eventId: string) => {
    recordEvolutionApi
      .getEvolution(eventId)
      .then((response) => {
        // Removes repeated dots
        let nonRedundant = response.data.evolution.filter(
          (it, i) =>
            i === 0 ||
            !isEqual(omitDate(it), omitDate(response.data.evolution[i - 1]))
        );
        setData(nonRedundant);
        setFilteredData(nonRedundant);
      })
      .catch(() => message.error("Failed to search."));
  }, []);

  const fetchEvents = useCallback(() => {
    recordEvolutionApi
      .getAvailableEvents()
      .then((response) => {
        setEventId(response.data[0].id);
        setOptions(
          response.data.map((it) => ({ label: it.name, value: it.id }))
        );
      })
      .catch(() => message.error("Failed to search."));
  }, []);

  const handleChange = useCallback((eventId: string) => {
    setData([]);
    setEventId(eventId);
  }, []);

  const handleMouseDown = useCallback((e: CategoricalChartState) => {
    setLeftChartDate(Number(e.activeLabel));
  }, []);

  const handleMouseMove = (e: CategoricalChartState) => {
    if (!leftChartDate) {
      return;
    }
    setRightChartDate(Number(e.activeLabel));
  };

  const handleMouseUp = (leftChartDate?: number, rightChartDate?: number) => {
    if (!leftChartDate || !rightChartDate) {
      return;
    }
    let left = Math.min(leftChartDate, rightChartDate);
    let right = Math.max(leftChartDate, rightChartDate);
    setFilteredData(
      data
        .filter((it) => new Date(it.date).getTime() >= left)
        .filter((it) => new Date(it.date).getTime() <= right)
    );
    setLeftChartDate(undefined);
    setRightChartDate(undefined);
  };

  useEffect(fetchEvents, [fetchEvents]);

  useEffect(() => {
    !!eventId && fetchData(eventId);
  }, [fetchData, eventId]);

  return (
    <div>
      <h1 className="page-title">Record Evolution</h1>
      <div className="align-center">
        <Select
          value={eventId}
          onChange={handleChange}
          options={options}
          style={{ width: "50%" }}
        />
      </div>
      {filteredData.length > 0 && (
        <>
          <Button onClick={() => setFilteredData(data)}>Zoom Out</Button>
          <ResponsiveContainer width="100%" height={0.6 * window.innerHeight}>
            <LineChart
              width={0.9 * window.innerWidth}
              height={0.6 * window.innerHeight}
              data={filteredData}
              style={{ margin: "0 auto" }}
              onMouseDown={handleMouseDown}
              onMouseMove={handleMouseMove}
              onMouseUp={() => handleMouseUp(leftChartDate, rightChartDate)}
            >
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis
                dataKey={(it) => new Date(it.date).getTime()}
                tickFormatter={(mills) => millsToDate(mills)}
                allowDataOverflow
                domain={["dataMin", "dataMax"]}
                type="number"
              />

              <YAxis
                tickFormatter={(mills) =>
                  formatResult(mills, eventId!, "single")
                }
              />
              <Tooltip
                formatter={(time: number, name: string, ...rest: any[]) => {
                  let isAverage = name.startsWith("Average");
                  let isSingleFmc = eventId === "333fm" && !isAverage;
                  let isMbld = eventId === "333mbf";
                  if (isMbld) {
                    let result = rest[0];
                    let resultType = result.name.replace("Single ", "best");
                    return formatResult(
                      result.payload[resultType],
                      eventId!,
                      "single"
                    );
                  }
                  return formatResult(
                    isSingleFmc ? time / 100 : time,
                    eventId!,
                    isAverage ? "average" : "single"
                  );
                }}
                labelFormatter={(mills) => millsToDate(mills)}
              />
              <Legend />
              {LINES.map((it) => (
                <Line
                  key={it.type + it.key}
                  type="monotone"
                  dataKey={(res) =>
                    getFormattedResult(res, eventId!, it.type, it.key)
                  }
                  name={`${it.label} ${it.key}`}
                  stroke={it.color}
                  strokeWidth={3}
                  dot={false}
                />
              ))}
              {leftChartDate && rightChartDate ? (
                <ReferenceArea
                  x1={leftChartDate}
                  x2={rightChartDate}
                  strokeOpacity={0.3}
                />
              ) : null}
            </LineChart>
          </ResponsiveContainer>
        </>
      )}
    </div>
  );
};
