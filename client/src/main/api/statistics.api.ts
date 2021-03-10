import Axios from "axios";
import { QueryDatabaseResponse } from "../model/QueryDatabase";

export class StatisticsApi {
  BASE_URL: string;
  queryDatabaseEndpoint: string;

  constructor() {
    this.BASE_URL = "http://localhost:8080";
    this.queryDatabaseEndpoint = "/database/query";
  }

  queryDatabase = (sqlQuery: string) => {
    let url = this.BASE_URL + this.queryDatabaseEndpoint;
    let params = new URLSearchParams();
    params.append("sqlQuery", sqlQuery);
    return Axios.get<QueryDatabaseResponse>(url, { params });
  };
}

const statisticsApi = new StatisticsApi();
export default statisticsApi;
