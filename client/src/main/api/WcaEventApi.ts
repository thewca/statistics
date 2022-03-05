import Axios from "axios";
import Event from "../model/Event";

class WcaEventApi {
  BASE_URL: string;

  constructor() {
    this.BASE_URL = process.env.REACT_APP_BASE_URL! + "/event";
  }

  list = () => Axios.get<Event[]>(`${this.BASE_URL}`);
}

const wcaEventApi = new WcaEventApi();
export default wcaEventApi;
