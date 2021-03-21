export default interface Statistics {
  displayMode: "DEFAULT" | "SELECTOR";
  explanation?: string;
  headers: string[];
  statistics: StatisticsDetail[];
  title: string;
}

interface StatisticsDetail {
  content: string[][];
  explanation?: string;
  keys?: string[];
}
