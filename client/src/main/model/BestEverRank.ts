import Rank from "./Rank";

export default interface BestEverRank {
  personId: string;
  countryId: string;
  continent: string;
  lastModified: Date;
  ranks: Rank[];
}
