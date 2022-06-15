interface SumOfRanksEvents {
  completed: boolean;
  eventId: string;
  regionalRank: number;
}

export interface SumOfRanks {
  events: SumOfRanksEvents[];
  overall: number;
  region: string;
  regionRank: number;
  regionType: string;
  wcaId: string;
}
