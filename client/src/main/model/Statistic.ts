import { StatisticsDetail } from "./StatisticsDetail";

export interface Statistics {
  displayMode: "DEFAULT" | "SELECTOR";
  explanation?: string;
  statistics: StatisticsDetail[];
  title: string;
}
