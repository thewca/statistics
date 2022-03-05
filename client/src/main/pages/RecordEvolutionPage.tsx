import { message, Select } from "antd";
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
import wcaEventApi from "../api/WcaEventApi";
import { Evolution } from "../model/RecordEvolution";
import { millsToDate } from "../util/DateUtil";
import formatResult from "../util/result.util";

const LINES = [
  { key: 1, color: "#82ca9d" },
  { key: 10, color: "#8884d8" },
  { key: 100, color: "#ff7300" },
];

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
        setData(response.data.evolution);
        setFilteredData(response.data.evolution);
      })
      .catch(() => message.error("Failed to search."));
  }, []);

  const fetchEvents = useCallback(() => {
    wcaEventApi
      .list()
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
    setFilteredData(
      data
        .filter(
          (it) => !leftChartDate || new Date(it.date).getTime() >= leftChartDate
        )
        .filter(
          (it) =>
            !rightChartDate || new Date(it.date).getTime() <= rightChartDate
        )
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
        <ResponsiveContainer width="100%" height={0.7 * window.innerHeight}>
          <LineChart
            width={0.9 * window.innerWidth}
            height={0.7 * window.innerHeight}
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

            <YAxis tickFormatter={(mills) => formatResult(mills, eventId!)} />
            <Tooltip
              formatter={(time: any) => formatResult(time, eventId!)}
              labelFormatter={(mills) => millsToDate(mills)}
            />
            <Legend />
            {LINES.map((it) => (
              <Line
                key={it.key}
                type="monotone"
                dataKey={`best${it.key}`}
                name={`WR ${it.key}`}
                stroke={it.color}
                strokeWidth={3}
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
      )}
    </div>
  );
};
