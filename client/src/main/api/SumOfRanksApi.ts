import Axios from "axios";
import { MetaSorInfo } from "../model/rank/MetaSorInfo";
import { SumOfRanks } from "../model/rank/SumOfRanks";

export class SumOfRanksApi {
  BASE_URL: string;

  constructor() {
    this.BASE_URL = process.env.REACT_APP_BASE_URL! + "/sum-of-ranks";
  }

  meta = () => {
    let url = `${this.BASE_URL}/meta`;
    return Axios.get<MetaSorInfo>(url);
  };

  listSumOfRanks = (
    resultType: string,
    regionType: string,
    region: string,
    page: number,
    pageSize: number
  ) => {
    let url = `${this.BASE_URL}/${resultType}/${regionType}/${region}`;
    let params = { page, pageSize };
    return Axios.get<SumOfRanks[]>(url, { params });
  };
}

export const sumOfRanksApi = new SumOfRanksApi();
