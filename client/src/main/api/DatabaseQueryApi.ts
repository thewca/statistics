import Axios from "axios";
import {
  QueryDatabaseMetaResponse,
  QueryDatabaseResponse,
} from "../model/QueryDatabase";

export class DatabaseQueryApi {
  BASE_URL: string;

  constructor() {
    this.BASE_URL = process.env.REACT_APP_BASE_URL! + "/database/";
  }

  queryDatabase = (sqlQuery: string, page: number, size: number) => {
    let url = this.BASE_URL + "query";
    let body = { sqlQuery, page, size };
    return Axios.post<QueryDatabaseResponse>(url, body);
  };

  queryDatabaseMeta = () => {
    let url = this.BASE_URL + "meta";
    return Axios.get<QueryDatabaseMetaResponse>(url);
  };
}

const databaseQueryApi = new DatabaseQueryApi();
export default databaseQueryApi;
