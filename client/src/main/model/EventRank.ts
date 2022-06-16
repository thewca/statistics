import Competitor from "./Competitor";
import WcaEvent from "./Event";

export default interface EventRank {
  event: WcaEvent;
  worlds: Competitor[];
  continents: Competitor[];
  countries: Competitor[];
}
