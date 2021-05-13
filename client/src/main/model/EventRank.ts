import Competitor from "./Competitor";
import Event from "./Event";

export default interface EventRank {
  event: Event;
  worlds: Competitor[];
  continents: Competitor[];
  countries: Competitor[];
}
