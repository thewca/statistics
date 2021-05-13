import Results from "./Results";

export default interface Competitor {
  continent: string;
  country: string;
  wca_id: string;
  single: Results;
  average: Results;
}
