import Axios from "axios";
import { QueryDatabaseResponse } from "../model/QueryDatabase";

export class StatisticsApi {
  BASE_URL: string;
  queryDatabaseEndpoint: string;

  constructor() {
    this.BASE_URL = "http://localhost:8080";
    this.queryDatabaseEndpoint = "/database/query";
  }

  queryDatabase = (sqlQuery: string, page: number, size: number) => {
    let url = this.BASE_URL + this.queryDatabaseEndpoint;
    let params = { sqlQuery, page, size };
    return Axios.post<QueryDatabaseResponse>(url, null, {
      params,
    });
  };
}

const statisticsApi = new StatisticsApi();
export default statisticsApi;
