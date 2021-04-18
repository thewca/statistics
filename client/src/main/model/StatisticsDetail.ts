export interface StatisticsDetail {
  content: string[][];
  explanation?: string;
  headers: string[];
  keys?: string[];
  sqlQueryCustom?: string;
  showPositions: boolean;
  positionTieBreakerIndex?: number;
}
