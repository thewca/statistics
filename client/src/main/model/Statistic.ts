import { StatisticsDetail } from "./StatisticsDetail";

export type DisplayMode = "DEFAULT" | "SELECTOR";

export interface Statistics {
  displayMode: DisplayMode;
  explanation?: string;
  statistics: StatisticsDetail[];
  title: string;
}
