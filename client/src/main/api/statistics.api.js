const baseUrl = "http://localhost:8000";
const queryDatabaseEndpoint = "/database/query";

export const queryDatabase = (query) => {
  let url = new URL(baseUrl + queryDatabaseEndpoint);
  let searchParams = new URLSearchParams();
  searchParams.append("sqlQuery", query);
  url.search = searchParams;
  console.log(url.href);
  return fetch(url.href).then((response) => response.json());
};
