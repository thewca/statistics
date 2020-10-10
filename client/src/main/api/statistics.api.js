const baseUrl = "http://localhost:8000";
const queryDatabaseEndpoint = "/database/query";

export const queryDatabase = (query) => {
  return fetch(
    baseUrl + queryDatabaseEndpoint + "?sqlQuery=" + query
  ).then((response) => response.json());
};
