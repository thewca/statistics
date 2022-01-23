import Axios from "axios";
import { AuthenticationResponse } from "../model/AuthenticationResponse";
import BestEverRank from "../model/BestEverRank";
import { QueryDatabaseResponse } from "../model/QueryDatabase";
import { Statistics } from "../model/Statistic";
import { StatisticsList } from "../model/StatisticsList";
import UserInfo from "../model/UserInfo";

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
    Axios.get<AuthenticationResponse>(this.BASE_URL + "/wca/authentication", {
      params: { frontendHost },
    });

  getUserInfo = () =>
    Axios.get<UserInfo>(this.BASE_URL + "/wca/user");

  getStatisticsGroups = (term?: string) =>
    Axios.get<StatisticsList>(this.BASE_URL + "/statistics/list", {
      params: { term },
    });

  getStatistic = (pathId: string) =>
    Axios.get<Statistics>(this.BASE_URL + "/statistics/list/" + pathId);

  getRanks = (wcaId: string) =>
    Axios.get<BestEverRank>(this.BASE_URL + "/best-ever-rank/" + wcaId);
}

const statisticsApi = new StatisticsApi();
export default statisticsApi;
