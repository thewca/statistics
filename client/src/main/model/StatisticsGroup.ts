import StatisticsItem from "./StatisticItem";

export default interface StatisticsGroup {
  group: string;
  size: number;
  statistics: StatisticsItem[];
}
