import Axios from "axios";
import WcaEvent from "../model/Event";
import { RecordEvolution } from "../model/RecordEvolution";
import { API_URL } from "../config/EnvVarConfig";

class RecordEvolutionApi {
  BASE_URL: string;

  constructor() {
    this.BASE_URL = API_URL! + "/record-evolution";
  }

  getEvolution = (eventId: string) => {
    return Axios.get<RecordEvolution>(`${this.BASE_URL}/${eventId}`);
  };

  getAvailableEvents = () => Axios.get<WcaEvent[]>(`${this.BASE_URL}/event`);
}

const recordEvolutionApi = new RecordEvolutionApi();
export default recordEvolutionApi;
