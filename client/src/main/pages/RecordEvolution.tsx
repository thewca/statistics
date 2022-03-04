import {
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

interface RecordEvolutionProps {}

export const RecordEvolution = ({}: RecordEvolutionProps) => {
  const data = [
    { avg1: 10, avg10: 20 },
    { avg1: 9, avg10: 18 },
    { avg1: 5, avg10: 17 },
    { avg1: 5, avg10: 15 },
  ];
  return (
    <div>
      <h1 className="page-title">Record Evolution</h1>
      <LineChart
        width={730}
        height={250}
        data={data}
        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="avg1" stroke="#8884d8" name="Avg 1" />
        <Line type="monotone" dataKey="avg10" stroke="#82ca9d" name="Avg 10" />
      </LineChart>
    </div>
  );
};
