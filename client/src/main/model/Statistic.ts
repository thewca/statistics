export interface Statistics {
  displayMode: "DEFAULT" | "SELECTOR";
  explanation?: string;
  headers: string[];
  statistics: StatisticsDetail[];
  title: string;
}
export interface StatisticsDetail {
  content: string[][];
  explanation?: string;
  keys?: string[];
  sqlQueryCustom?: string;
}
