export interface RecordEvolution {
  eventId: string;
  evolution: Evolution[];
}

export interface Evolution {
  best1: number;
  best10: number;
  best1000: number;
  date: string;
}
