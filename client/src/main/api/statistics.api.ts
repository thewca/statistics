import Axios from "axios";
import { QueryDatabaseResponse } from "../model/QueryDatabase";
import { Statistics } from "../model/Statistic";
import { StatisticsItem } from "../model/StatisticItem";
import { UserInfo } from "../model/UserInfo";

export class StatisticsApi {
  BASE_URL: string;
  queryDatabaseEndpoint: string;

  constructor() {
    this.BASE_URL = process.env.REACT_APP_BASE_URL!;
    this.queryDatabaseEndpoint = "/database/query";
  }

  queryDatabase = (sqlQuery: string, page: number, size: number) => {
    let url = this.BASE_URL + this.queryDatabaseEndpoint;
    let body = { sqlQuery, page, size };
    return Axios.post<QueryDatabaseResponse>(url, body);
  };

  getWcaAuthenticationUrl = (frontendHost: string) =>
    Axios.get<string>(this.BASE_URL + "/wca/authentication", {
      params: { frontendHost },
    });

  getUserInfo = (accessToken: string, tokenType: string) =>
    Axios.get<UserInfo>(this.BASE_URL + "/wca/user", {
      params: { accessToken, tokenType },
    });

  getStatisticsList = () =>
    Axios.get<StatisticsItem[]>(this.BASE_URL + "/statistics/list");

  getStatistic = (pathId: string) =>
    Axios.get<Statistics>(this.BASE_URL + "/statistics/list/" + pathId);
}

const statisticsApi = new StatisticsApi();
export default statisticsApi;
