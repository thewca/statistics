import EventRank from "./EventRank";

export default interface BestEverRank {
  personId: string;
  lastModified: Date;
  eventRanks: EventRank[];
}
