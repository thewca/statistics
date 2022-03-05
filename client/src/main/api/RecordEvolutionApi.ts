import Axios from "axios";
import { RecordEvolution } from "../model/RecordEvolution";

class RecordEvolutionApi {
  BASE_URL: string;

  constructor() {
    this.BASE_URL = process.env.REACT_APP_BASE_URL! + "/record-evolution";
  }

  getEvolution = (eventId: string) => {
    return Axios.get<RecordEvolution>(`${this.BASE_URL}/${eventId}`);
  };
}

const recordEvolutionApi = new RecordEvolutionApi();
export default recordEvolutionApi;
