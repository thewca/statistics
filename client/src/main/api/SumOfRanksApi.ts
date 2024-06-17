import Axios from "axios";
import { API_URL } from "../config/EnvVarConfig";
import { PageResponse } from "../model/PageResponse";
import { MetaSorInfo } from "../model/rank/MetaSorInfo";
import { SumOfRanks } from "../model/rank/SumOfRanks";

export class SumOfRanksApi {
  BASE_URL: string;

  constructor() {
    this.BASE_URL = API_URL! + "/sum-of-ranks";
  }

  meta = () => {
    let url = `${this.BASE_URL}/meta`;
    return Axios.get<MetaSorInfo[]>(url);
  };

  listSumOfRanks = (
    resultType: string,
    regionType: string,
    region: string,
    page: number,
    pageSize: number,
    wcaId?: string,
  ) => {
    let url = `${this.BASE_URL}/${resultType}/${regionType}/${region}`;
    let params = { page, pageSize, wcaId };
    return Axios.get<PageResponse<SumOfRanks>>(url, { params });
  };
}

export const sumOfRanksApi = new SumOfRanksApi();
