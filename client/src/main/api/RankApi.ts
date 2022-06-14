import Axios from "axios";
import { SumOfRanks } from "../model/rank/SumOfRanks";

export class RanksApi {
  BASE_URL: string;

  constructor() {
    this.BASE_URL = process.env.REACT_APP_BASE_URL! + "/rank";
  }

  listSumOfRanks = (
    region: string,
    regionType: string,
    resultType: string,
    page: number,
    pageSize: number
  ) => {
    let url = `${this.BASE_URL}/sum-of-ranks/${regionType}/${region}/${resultType}`;
    let params = { page, pageSize };
    return Axios.get<SumOfRanks[]>(url, { params });
  };
}

export const ranksApi = new RanksApi();
