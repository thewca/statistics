import Result from "./Result";

export default interface Rank {
  single: Result;
  average: Result;
  event_id: string;
  wcaId: string;
  country: string;
  continent: string;
  best_world_single_rank: Result;
  best_world_average_rank: Result;
  best_continent_single_rank: Result;
  best_continent_average_rank: Result;
  best_country_single_rank: Result;
  best_country_average_rank: Result;
}
