export interface StatisticsItem {
  title: string;
  path: string;
}

export interface StatisticsGroup {
  group: string;
  statistics: StatisticsItem[];
}
