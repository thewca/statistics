export interface RecordEvolution {
  eventId: string;
  evolution: Evolution[];
}

export interface Evolution {
  best1: number;
  best10: number;
  best100: number;
  avg1: number;
  avg10: number;
  avg100: number;
  date: string;
}
