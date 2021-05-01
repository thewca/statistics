import StatisticsGroup from "./StatisticsGroup";

export interface StatisticsList {
  groups: number;
  list: StatisticsGroup[];
  totalSize: number;
}
