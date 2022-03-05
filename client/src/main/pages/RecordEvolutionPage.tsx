import { message } from "antd";
import { useCallback, useEffect, useState } from "react";
import {
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import recordEvolutionApi from "../api/RecordEvolutionApi";
import { Evolution } from "../model/RecordEvolution";
import { millsToDate } from "../util/DateUtil";

const LINES = [
  { key: 1, color: "#82ca9d" },
  { key: 10, color: "#8884d8" },
  { key: 100, color: "#ff7300" },
];

export const RecordEvolutionPage = () => {
  const [data, setData] = useState<Evolution[]>([]);
  const [eventId, setEventId] = useState("555bf");
  const [loading, setLoading] = useState(false);

  const fetchData = useCallback((eventId: string) => {
    setLoading(true);
    recordEvolutionApi
      .getEvolution(eventId)
      .then((response) => {
        setData(response.data.evolution);
      })
      .catch(() => message.error("Failed to search."))
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => fetchData(eventId), [fetchData, eventId]);

  return (
    <div>
      <h1 className="page-title">Record Evolution</h1>
      {data.length > 0 && (
        <LineChart
          width={0.9 * window.innerWidth}
          height={0.7 * window.innerHeight}
          data={data}
          style={{ margin: "0 auto" }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey={(it) => new Date(it.date).getTime()}
            domain={["auto", "auto"]}
            name="Time"
            tickFormatter={(mills) => millsToDate(mills)}
            type="number"
          />
          <YAxis />
          <Tooltip />
          <Legend />
          {LINES.map((it) => (
            <Line
              key={it.key}
              type="monotone"
              dataKey={`best${it.key}`}
              name={`WR ${it.key}`}
              stroke={it.color}
              strokeWidth={3}
            ></Line>
          ))}
        </LineChart>
      )}
    </div>
  );
};
