import Results from "./Results";

export default interface Competitor {
  continent: string;
  country: string;
  wcaId: string;
  single: Results;
  average: Results;
}
